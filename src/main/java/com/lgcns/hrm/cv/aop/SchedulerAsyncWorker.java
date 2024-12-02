package com.lgcns.hrm.cv.aop;

import com.lgcns.hrm.cv.common.utils.JsonUtil;
import com.lgcns.hrm.cv.entity.ScheduleJob;
import com.lgcns.hrm.cv.entity.ScheduleJobLog;
import com.lgcns.hrm.cv.service.ScheduleJobLogService;
import com.lgcns.hrm.cv.service.ScheduleJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class SchedulerAsyncWorker {
    private final ScheduleJobService scheduleJobService;
    private final ScheduleJobLogService scheduleJobLogService;

    public void saveScheduleJobLog(ScheduleJobLog scheduleJobLog) {
        var scheduleJob = scheduleJobService.findById(scheduleJobLog.getJobId());
        if (scheduleJob == null) {
            log.error("Get exception of `{}` scheduled task; log content: {}", scheduleJobLog.getJobId(), JsonUtil.toPrettyJson(scheduleJobLog));
        } else {
            scheduleJobLog.setJobKey(scheduleJob.getJobKey());
            scheduleJobLog.setCron(scheduleJob.getCron());
            scheduleJobLog.setJobName(scheduleJob.getJobName());
            scheduleJobLog.setParamJson(scheduleJob.getParamJson());
            if (scheduleJob.getSaveLog() != null && scheduleJob.getSaveLog()) {
                scheduleJobLogService.save(scheduleJobLog);
            } else {
                log.debug("The scheduled task `{}` has turned off logging, log content: {}", scheduleJobLog.getJobId(), JsonUtil.toPrettyJson(scheduleJobLog));
            }
        }
    }

}