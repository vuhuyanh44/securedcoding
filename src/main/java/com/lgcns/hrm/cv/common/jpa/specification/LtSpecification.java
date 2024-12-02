package com.lgcns.hrm.cv.common.jpa.specification;

import jakarta.persistence.criteria.*;

/**
 * @author pigx
 * @created 17/01/2024 - 10:40 AM
 * @project hr-api
 */
public class LtSpecification<T> extends AbstractSpecification<T>{
    private final String property;
    private final transient Comparable<Object> comparable;

    public LtSpecification(String property, Comparable<?> comparable){
        this.comparable = (Comparable<Object>) comparable;
        this.property = property;
    }
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var from = getRoot(property, root);
        var field = getProperty(property);
        return criteriaBuilder.lessThan(from.get(field), comparable);
    }
}
