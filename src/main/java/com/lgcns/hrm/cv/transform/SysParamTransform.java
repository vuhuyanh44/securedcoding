package com.lgcns.hrm.cv.transform;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.entity.SysParam;
import com.lgcns.hrm.cv.model.vo.SysParamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SysParamTransform implements BaseTransform<SysParam, SysParamVo> {
    @Override
    public SysParam toEntity(SysParamVo model) {
        return BeanUtil.copy(model, SysParam.class);
    }

    @Override
    public SysParamVo toModel(SysParam entity) {
        return BeanUtil.copy(entity, SysParamVo.class);
    }
}
