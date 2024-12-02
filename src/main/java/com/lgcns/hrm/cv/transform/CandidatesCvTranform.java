package com.lgcns.hrm.cv.transform;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.entity.CandidatesCv;
import com.lgcns.hrm.cv.model.vo.CandidatesCvVo;
import org.springframework.stereotype.Component;

@Component
public class CandidatesCvTranform implements BaseTransform<CandidatesCv, CandidatesCvVo> {
    @Override
    public CandidatesCv toEntity(CandidatesCvVo model) {
        return BeanUtil.copy(model, CandidatesCv.class);
    }

    @Override
    public CandidatesCvVo toModel(CandidatesCv entity) {
        return BeanUtil.copy(entity, CandidatesCvVo.class);
    }
}
