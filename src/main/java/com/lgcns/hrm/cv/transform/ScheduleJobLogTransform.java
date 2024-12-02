package com.lgcns.hrm.cv.transform;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.entity.ScheduleJobLog;
import com.lgcns.hrm.cv.model.vo.ScheduleJobLogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduleJobLogTransform implements BaseTransform<ScheduleJobLog, ScheduleJobLogVo> {
    @Override
    public ScheduleJobLog toEntity(ScheduleJobLogVo model) {
        return BeanUtil.copy(model, ScheduleJobLog.class);
    }

    @Override
    public ScheduleJobLogVo toModel(ScheduleJobLog entity) {
        return BeanUtil.copy(entity, ScheduleJobLogVo.class);
    }
}