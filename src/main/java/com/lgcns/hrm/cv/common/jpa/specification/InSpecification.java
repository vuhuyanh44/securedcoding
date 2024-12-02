package com.lgcns.hrm.cv.common.jpa.specification;

import jakarta.persistence.criteria.*;

import java.util.Collection;

/**
 * @author pigx
 * @created 17/01/2024 - 10:12 AM
 * @project hr-api
 */
public class InSpecification <T> extends AbstractSpecification<T>{
    private final String property;
    private final transient Collection<?> values;

    public InSpecification(String property, Collection<?> values){
        this.property = property;
        this.values = values;
    }
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var from = getRoot(property, root);
        var field = getProperty(property);
        return from.get(field).in(values);
    }
}
