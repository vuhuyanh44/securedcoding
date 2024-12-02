package com.lgcns.hrm.cv.config;

import com.lgcns.hrm.cv.common.utils.CollectionUtil;
import com.lgcns.hrm.cv.entity.ScheduleJob;
import com.lgcns.hrm.cv.model.enums.ScheduleStatus;
import com.lgcns.hrm.cv.service.QuartzSchedulerService;
import com.lgcns.hrm.cv.service.ScheduleJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.quartz", name = "job-store-type", havingValue = "MEMORY", matchIfMissing = true)
public class SchedulerJobInitializer implements ApplicationRunner {

    private final ScheduleJobService scheduleJobService;
    private final QuartzSchedulerService quartzSchedulerService;

    @Override
    public void run(ApplicationArguments args) {

        List<ScheduleJob> jobList = scheduleJobService.getEntityListByStatus(ScheduleStatus.A.name());
        if (CollectionUtil.isEmpty(jobList)) {
            return;
        }
        jobList.forEach(scheduleJob -> {
            try {
                quartzSchedulerService.addJob(scheduleJob);
            } catch (Exception e) {
                log.error("Timing task: jobKey={}, initialization loading failed!", scheduleJob.getJobKey(), e);
            }
        });
    }
}
