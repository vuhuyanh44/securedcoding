package com.lgcns.hrm.cv.controller.base;

import com.lgcns.hrm.cv.common.definition.domain.AbstractEntity;
import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.service.base.BaseService;

import java.io.Serializable;

public abstract class BaseController<E extends AbstractEntity, M extends AbstractVo, ID extends Serializable> implements WriteableController<E, M, ID> {


    @Override
    public BaseService<E, ID> getReadableService() {
        return this.getWriteableService();
    }

    @Override
    public Result<M> saveOrUpdate(M domainVo) throws Exception {
        E savedDomain = getWriteableService().saveAndFlush(getBaseTransform().toEntity(domainVo));
        return result(getBaseTransform().toModel(savedDomain));
    }

    @Override
    public Result<String> delete(ID id) {
        Result<String> result = result(String.valueOf(id));
        getWriteableService().deleteById(id);
        return result;
    }
}
