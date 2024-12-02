package com.lgcns.hrm.cv.common.jpa.specification;

import jakarta.persistence.criteria.*;

/**
 * @author pigx
 * @created 17/01/2024 - 10:07 AM
 * @project hr-api
 */
public class GtSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    private final transient Comparable<Object> comparable;

    public GtSpecification(String property, Comparable<? extends Object> comparable) {
        this.property = property;
        this.comparable = (Comparable<Object>) comparable;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var from = getRoot(property, root);
        var field = getProperty(property);
        return criteriaBuilder.greaterThan(from.get(field), comparable);
    }
}
