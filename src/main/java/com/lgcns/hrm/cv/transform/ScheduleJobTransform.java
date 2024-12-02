package com.lgcns.hrm.cv.transform;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.entity.ScheduleJob;
import com.lgcns.hrm.cv.model.vo.ScheduleJobVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduleJobTransform implements BaseTransform<ScheduleJob, ScheduleJobVo> {
    @Override
    public ScheduleJob toEntity(ScheduleJobVo model) {
        return BeanUtil.copy(model, ScheduleJob.class);
    }

    @Override
    public ScheduleJobVo toModel(ScheduleJob entity) {
        return BeanUtil.copy(entity, ScheduleJobVo.class);
    }
}
