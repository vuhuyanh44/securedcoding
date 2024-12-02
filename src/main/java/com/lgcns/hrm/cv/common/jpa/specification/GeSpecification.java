package com.lgcns.hrm.cv.common.jpa.specification;

import jakarta.persistence.criteria.*;

/**
 * @author pigx
 * @created 17/01/2024 - 10:04 AM
 * @project hr-api
 */
public class GeSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    private final transient Comparable<Object> compare;

    public GeSpecification(String property, Comparable<? extends Object> compare) {
        this.property = property;
        this.compare = (Comparable<Object>) compare;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var from = getRoot(property, root);
        var field = getProperty(property);
        return criteriaBuilder.greaterThanOrEqualTo(from.get(field), compare);
    }
}
