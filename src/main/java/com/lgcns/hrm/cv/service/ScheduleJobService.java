package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.common.utils.ObjectUtil;
import com.lgcns.hrm.cv.common.utils.StringUtil;
import com.lgcns.hrm.cv.entity.ScheduleJob;
import com.lgcns.hrm.cv.exception.ResourceException;
import com.lgcns.hrm.cv.exception.ScheduleException;
import com.lgcns.hrm.cv.model.enums.ScheduleStatus;
import com.lgcns.hrm.cv.model.vo.ScheduleJobVo;
import com.lgcns.hrm.cv.repository.ScheduleJobRepository;
import com.lgcns.hrm.cv.service.base.AbstractBaseService;
import com.lgcns.hrm.cv.service.specification.ScheduleJobSpec;
import com.lgcns.hrm.cv.transform.ScheduleJobTransform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleJobService extends AbstractBaseService<ScheduleJob, String> {
    private final QuartzSchedulerService quartzSchedulerService;
    private final ScheduleJobRepository scheduleJobRepository;
    private final ScheduleJobTransform scheduleJobTransform;

    @Override
    public ScheduleJobRepository getRepository() {
        return scheduleJobRepository;
    }

    public ScheduleJobVo getViewObject(String id) {
        Optional<ScheduleJob> scheduleJob = scheduleJobRepository.findById(id);
        return scheduleJob.map(scheduleJobTransform::toModel).orElse(null);
    }

    public Page<ScheduleJobVo> getViewObjectList(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ScheduleJob> scheduleJobs = scheduleJobRepository.findAll(pageable);
        return scheduleJobTransform.mapEntityPageIntoDtoPage(scheduleJobs);
    }

    public List<ScheduleJob> getEntityListByStatus(String status) {
        return scheduleJobRepository.findScheduleJobByJobStatus(status);
    }

    public ScheduleJob findScheduleJobByJobKey(String jobKey) {
        return scheduleJobRepository.findScheduleJobByJobKey(jobKey);
    }

    @Transactional
    public void saveJob(ScheduleJobVo scheduleJobVo) throws Exception{
        ScheduleJob entity = scheduleJobTransform.toEntity(scheduleJobVo);
        if (ObjectUtil.isEmpty(entity.getJobKey())) {
            entity.setJobKey(StringUtil.getUUID());
        }
        if (ObjectUtil.isNotEmpty(entity.getId())) {
            try {
                updateJob(scheduleJobVo);
            } catch (Exception e) {
                throw new ResourceException(e.getMessage());
            }
            return;
        }
        ScheduleJob success = this.save(entity);
        if (Objects.isNull(success)) {
            throw new ScheduleException("Failed to create scheduled task!");
        }
        if (ObjectUtil.equals(success.getJobStatus(), ScheduleStatus.A.name())) {
            quartzSchedulerService.addJob(success);
        }
    }

    @Transactional
    public void updateJob(ScheduleJobVo scheduleJobVo) throws Exception{
        ScheduleJob entity = scheduleJobTransform.toEntity(scheduleJobVo);
        ScheduleJob oldJob = this.findById(entity.getId());

        if (Objects.isNull(entity)) {
            throw new ScheduleException("Update scheduled task failed!");
        }
        if (!ObjectUtil.equals(oldJob.getParamJson(), entity.getParamJson())
                || !ObjectUtil.equals(oldJob.getJobStatus(), ScheduleStatus.I.name())
                || !ObjectUtil.equals(oldJob.getInitStrategy(), entity.getInitStrategy())
                || !ObjectUtil.equals(oldJob.getCron(), entity.getCron())
        ) {
            quartzSchedulerService.deleteJob(oldJob.getId());
        }
        this.saveAndFlush(entity);
        if (ObjectUtil.equals(entity.getJobStatus(), ScheduleStatus.A.name())) {
            quartzSchedulerService.addJob(entity);
        }
    }

    public boolean executeOnceJob(String jobId) {

        if (!quartzSchedulerService.existJob(jobId)) {
            ScheduleJob entity = this.findById(jobId);
            if (ObjectUtil.isEmpty(entity)) {
                throw new ScheduleException("The current task is invalid!");
            }
            quartzSchedulerService.addJobExecuteOnce(entity);
        } else {
            quartzSchedulerService.runJob(jobId);
        }

        return true;
    }

    @Transactional
    public boolean changeScheduleJobStatus(String jobId, String jobStatus) throws Exception{
        ScheduleJob scheduleJob = findById(jobId);
        if (Objects.isNull(scheduleJob)) {
            throw new ScheduleException("Update status failed!");
        }
        scheduleJob.setJobStatus(jobStatus);
        this.saveAndFlush(scheduleJob);

        if (ScheduleStatus.A.name().equals(jobStatus)) {
            if (quartzSchedulerService.existJob(jobId)) {
                quartzSchedulerService.resumeJob(jobId);
            } else {
                ScheduleJob entity = this.findById(jobId);
                if (ObjectUtil.isEmpty(entity)) {
                    throw new ScheduleException("The current task is invalid！");
                }
                quartzSchedulerService.addJob(entity);
            }
        } else if (ScheduleStatus.I.name().equals(jobStatus)) {
            quartzSchedulerService.pauseJob(jobId);
        } else {
            log.info("Invalid action parameter: {}", jobStatus.replaceAll("[\r\n]", ""));
        }

        return true;
    }


    public List<Map<String, Object>> getAllJobs() {
        return quartzSchedulerService.loadAllJobs();
    }


    public Page<ScheduleJobVo> getByCondition(Long logId, String jobName, String jobKey, String jobStatus, int pageNum, int pageSize){
        Page<ScheduleJob> scheduleJobLogs= this.findByPage(ScheduleJobSpec.findByCondition(logId,jobName,jobKey,jobStatus),PageRequest.of(pageNum, pageSize));
        return scheduleJobTransform.mapEntityPageIntoDtoPage(scheduleJobLogs);
    }
}
