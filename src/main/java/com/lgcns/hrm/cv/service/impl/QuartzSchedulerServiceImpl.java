package com.lgcns.hrm.cv.service.impl;

import com.lgcns.hrm.cv.annotation.CollectThisJob;
import com.lgcns.hrm.cv.common.utils.*;
import com.lgcns.hrm.cv.entity.ScheduleJob;
import com.lgcns.hrm.cv.exception.ScheduleException;
import com.lgcns.hrm.cv.service.QuartzSchedulerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuartzSchedulerServiceImpl implements QuartzSchedulerService {

    private final Scheduler scheduler;

    enum INIT_STRATEGY {
        // cycle execution
        DO_NOTHING,
        // Execute once immediately, and execute periodically
        FIRE_AND_PROCEED,
        // Execute immediately after the expiration, and execute periodically
        IGNORE_MISFIRES
    }

    // task cache
    public static final List<Map<String, Object>> CACHE_JOB = new ArrayList<>();

    @PostConstruct
    public void startScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("Timed task scheduler initialization exception, please check!", e);
        }
    }

    @Override
    public List<Map<String, Object>> loadAllJobs() {
        if (CollectionUtil.isNotEmpty(CACHE_JOB)) {
            return CACHE_JOB;
        }
        // Get all jobs annotated by com.diboot.scheduler.job.anno.CollectThisJob
        List<Object> annoJobList = ContextUtil.getBeansByAnnotation(CollectThisJob.class);
        if (CollectionUtil.isNotEmpty(annoJobList)) {
            List<Map<String, Object>> result = loadJobs(annoJobList);
            CACHE_JOB.addAll(result);
        }
        return CACHE_JOB;
    }

    private List<Map<String, Object>> loadJobs(List<Object> annoJobList) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object job : annoJobList) {
            if (!(job instanceof Job)) {
                log.warn("Invalid job task: {}", job.getClass());
                continue;
            }
            Map<String, Object> temp = new HashMap<>(8);
            Class<?> targetClass = BeanUtil.getTargetClass(job);
            CollectThisJob annotation = targetClass.getAnnotation(CollectThisJob.class);
            temp.put("jobKey", targetClass.getSimpleName());
            temp.put("jobCron", annotation.cron());
            temp.put("jobName", annotation.name());
            temp.put("jobClass", targetClass);
            Class<?> paramClass = annotation.paramClass();
            String paramJsonExample = annotation.paramJson();
            if (ObjectUtil.isNotEmpty(paramJsonExample) && !Object.class.getTypeName().equals(paramClass.getTypeName())) {
                try {
                    paramJsonExample = JsonUtil.toPrettyJson(paramClass.getConstructor().newInstance());
                } catch (Exception e) {
                    log.error("job task: {}, Scheduled#paramClass parameter task is invalid, it is recommended to use Scheduled#paramJson parameter instead!", job.getClass());
                }
            }
            temp.put("paramJsonExample", paramJsonExample);
            result.add(temp);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> loadJobsInScheduler() {
        List<Map<String, Object>> jobList = null;
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
            jobList = new ArrayList<>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jobName", jobKey.getName());
                    map.put("jobGroupName", jobKey.getGroup());
                    map.put("description", trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    map.put("jobStatus", triggerState.name());
                    if (trigger instanceof CronTrigger cronTrigger) {
                        String cronExpression = cronTrigger.getCronExpression();
                        map.put("jobTime", cronExpression);
                    }
                    jobList.add(map);
                }
            }
        } catch (SchedulerException e) {
            log.error("Loading all Job exception", e);
        }
        return jobList;
    }

    @Override
    public void addJob(ScheduleJob job) {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getId());
        // build parameters
        JobDetail jobDetail = JobBuilder.newJob(getJobClass(job.getJobKey())).withIdentity(job.getId()).build();
        if (ObjectUtil.isNotEmpty(job.getParamJson())) {
            Map<String, Object> jsonData = JsonUtil.readMap(job.getParamJson());
            jobDetail.getJobDataMap().putAll(jsonData);
        }
        try {
            // expression dispatch builder
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
            // Set the timing task initialization strategy
            if (INIT_STRATEGY.FIRE_AND_PROCEED.name().equals(job.getInitStrategy())) {
                cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
            } else if (INIT_STRATEGY.IGNORE_MISFIRES.name().equals(job.getInitStrategy())) {
                cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
            }
            // timed task
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.error("Add a scheduled task exception", e);
            throw new ScheduleException("Add a scheduled task exception");
        }
    }

    @Override
    public void addJobExecuteOnce(ScheduleJob job) {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getId());
        // build parameters
        JobDetail jobDetail = JobBuilder.newJob(getJobClass(job.getJobKey())).withIdentity(job.getId()).build();
        if (ObjectUtil.isNotEmpty(job.getParamJson())) {
            Map<String, Object> jsonData = JsonUtil.readMap(job.getParamJson());
            jobDetail.getJobDataMap().putAll(jsonData);
        }
        try {
            // Execute immediately, and only once
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0)).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.error("Add a scheduled task exception", e);
            throw new ScheduleException("Add a scheduled task exception");
        }
    }

    @Override
    public void updateJobCron(String jobId, String cron) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobId.toString());
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            CronTrigger cronTrigger = (CronTrigger) trigger;
            if (!cronTrigger.getCronExpression().equals(cron)) {
                cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(cron)).startNow().build();
                scheduler.rescheduleJob(triggerKey, cronTrigger);
            }
        } catch (Exception e) {
            log.error("The cron timing expression of updating job is abnormal", e);
        }
    }

    @Override
    public void deleteJob(String jobId) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobId);
        try {
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(JobKey.jobKey(jobId));
        } catch (Exception e) {
            log.error("delete job exception", e);
        }
    }

    @Override
    public boolean existJob(String jobId) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobId);
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            return ObjectUtil.isNotEmpty(trigger);
        } catch (SchedulerException e) {
            log.info("Check existed job fail ", e);
        }
        return false;
    }

    @Override
    public void runJob(String jobId) {
        try {
            scheduler.triggerJob(JobKey.jobKey(jobId));
        } catch (SchedulerException e) {
            log.error("Execution job exception", e);
        }
    }

    @Override
    public void pauseJob(String jobId) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobId));
        } catch (Exception e) {
            log.error("Suspended job exception", e);
        }
    }

    @Override
    public void resumeJob(String jobId) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobId));
        } catch (SchedulerException e) {
            log.error("resume job exception", e);
        }
    }


    public Class<? extends Job> getJobClass(String jobKey) {
        try {
            Class<? extends Job> jobClass = loadAllJobs().stream()
                    .filter(e -> String.valueOf(e.get("jobKey")).equals(jobKey))
                    .map(e -> (Class<? extends Job>) e.get("jobClass"))
                    .findAny()
                    .orElse(null);
            if (jobClass == null) {
                throw new ScheduleException("Illegal scheduled task!" + jobKey);
            }
            return jobClass;
        } catch (Exception e) {
            throw new ScheduleException("Failed to load the scheduled task!");
        }
    }


}