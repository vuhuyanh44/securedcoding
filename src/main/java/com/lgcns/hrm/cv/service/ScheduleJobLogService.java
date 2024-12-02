package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.entity.ScheduleJobLog;
import com.lgcns.hrm.cv.model.vo.ScheduleJobLogVo;
import com.lgcns.hrm.cv.repository.ScheduleJobLogRepository;
import com.lgcns.hrm.cv.repository.base.BaseRepository;
import com.lgcns.hrm.cv.service.base.AbstractBaseService;
import com.lgcns.hrm.cv.service.specification.ScheduleJobLogSpec;
import com.lgcns.hrm.cv.transform.ScheduleJobLogTransform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleJobLogService extends AbstractBaseService<ScheduleJobLog, String> {

    private final ScheduleJobLogRepository scheduleJobLogRepository;
    private final ScheduleJobLogTransform scheduleJobLogTransform;

    @Override
    public ScheduleJobLogRepository getRepository() {
        return scheduleJobLogRepository;
    }

    public Page<ScheduleJobLogVo> getViewObjectList(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ScheduleJobLog> scheduleJobLogs = scheduleJobLogRepository.findAll(pageable);
        return scheduleJobLogTransform.mapEntityPageIntoDtoPage(scheduleJobLogs);
    }

    public ScheduleJobLogVo getViewObject(String id) {
        Optional<ScheduleJobLog> scheduleJobLog = scheduleJobLogRepository.findById(id);
        return scheduleJobLog.map(scheduleJobLogTransform::toModel).orElse(null);
    }

    public Page<ScheduleJobLogVo> getByCondition(String logId, String jobName, String jobKey, String runStatus, int pageNum, int pageSize) {
        Page<ScheduleJobLog> scheduleJobLogs = this.findByPage(ScheduleJobLogSpec.findByCondition(logId, jobName, jobKey, runStatus), PageRequest.of(pageNum, pageSize));
        return scheduleJobLogTransform.mapEntityPageIntoDtoPage(scheduleJobLogs);
    }
}