package com.lgcns.hrm.cv.service.base;

import com.lgcns.hrm.cv.common.constants.SymbolConstants;
import com.lgcns.hrm.cv.common.definition.domain.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
public abstract class AbstractBaseService<E extends Entity, ID extends Serializable> implements BaseService<E, ID> {

    protected String like(String property) {
        return SymbolConstants.PERCENT + property + SymbolConstants.PERCENT;
    }
}
