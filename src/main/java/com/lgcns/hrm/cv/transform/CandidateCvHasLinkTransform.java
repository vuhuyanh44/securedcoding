package com.lgcns.hrm.cv.transform;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.entity.CandidatesCv;
import com.lgcns.hrm.cv.model.vo.CandidateCvVoHasLink;
import org.springframework.stereotype.Component;

@Component
public class CandidateCvHasLinkTransform implements BaseTransform<CandidatesCv, CandidateCvVoHasLink> {
    @Override
    public CandidatesCv toEntity(CandidateCvVoHasLink model) {
        return BeanUtil.copy(model, CandidatesCv.class);
    }

    @Override
    public CandidateCvVoHasLink toModel(CandidatesCv entity) {
        return BeanUtil.copy(entity, CandidateCvVoHasLink.class);
    }
}
