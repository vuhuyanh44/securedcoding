package com.lgcns.hrm.cv.transform;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.entity.CandidatesStatus;
import com.lgcns.hrm.cv.model.vo.CandidatesStatusVo;
import org.springframework.stereotype.Component;

@Component
public class CandidateProcess implements BaseTransform<CandidatesStatus, CandidatesStatusVo> {

    @Override
    public CandidatesStatus toEntity(CandidatesStatusVo model) {
        return BeanUtil.copy(model, CandidatesStatus.class);
    }
    @Override
    public CandidatesStatusVo toModel(CandidatesStatus entity) {
        return BeanUtil.copy(entity, CandidatesStatusVo.class);
    }
}
