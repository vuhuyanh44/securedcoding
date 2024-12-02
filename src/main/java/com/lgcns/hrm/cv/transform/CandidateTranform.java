package com.lgcns.hrm.cv.transform;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.entity.Candidates;
import com.lgcns.hrm.cv.model.vo.CandidateVo;
import org.springframework.stereotype.Component;

@Component
public class CandidateTranform implements BaseTransform<Candidates, CandidateVo> {

    @Override
    public Candidates toEntity(CandidateVo model) {
        return BeanUtil.copy(model, Candidates.class);
    }
    @Override
    public CandidateVo toModel(Candidates entity) {
        return BeanUtil.copy(entity, CandidateVo.class);
    }
}
