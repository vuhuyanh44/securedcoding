package com.lgcns.hrm.cv.controller.base;

import com.lgcns.hrm.cv.common.definition.domain.AbstractEntity;
import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.service.base.BaseService;
import com.lgcns.hrm.cv.transform.BaseTransform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ReadableController<E extends AbstractEntity, M extends AbstractVo, ID extends Serializable> extends Controller {


    BaseService<E, ID> getReadableService();

    BaseTransform<E, M> getBaseTransform();

    default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize) {
        Page<E> pages = getReadableService().findByPage(pageNumber, pageSize);
        Page<M> pagesVo = getBaseTransform().mapEntityPageIntoDtoPage(pages);
        return result(pagesVo);
    }


    default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize, Sort.Direction direction, String... properties) {
        Page<E> pages = getReadableService().findByPage(pageNumber, pageSize, direction, properties);
        Page<M> pageVo = getBaseTransform().mapEntityPageIntoDtoPage(pages);
        return result(pageVo);
    }

    default Result<List<M>> findAll() {
        List<E> domains = getReadableService().findAll();
        List<M> domainsVo = getBaseTransform().mapEntityListIntoDtoList(domains);
        return result(domainsVo);
    }

    default Result<M> findById(ID id) {
        E domain = getReadableService().findById(id);
        M domainVo = getBaseTransform().toModel(domain);
        return result(domainVo);
    }
}

