package com.lgcns.hrm.cv.transform;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.entity.SysType;
import com.lgcns.hrm.cv.model.vo.SysTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SysTypeTransform implements BaseTransform<SysType, SysTypeVo> {
    @Override
    public SysType toEntity(SysTypeVo model) {
        return BeanUtil.copy(model, SysType.class);
    }

    @Override
    public SysTypeVo toModel(SysType entity) {
        return BeanUtil.copy(entity, SysTypeVo.class);
    }
}
