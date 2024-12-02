package com.lgcns.hrm.cv.common.jpa.specification;

import jakarta.persistence.criteria.*;

/**
 * @author pigx
 * @created 17/01/2024 - 9:44 AM
 * @project hr-api
 */
public class BetweenSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    private final transient Comparable<Object> lower;
    private final transient Comparable<Object> upper;
    public BetweenSpecification(String property, Object lower, Object upper){
        this.property = property;
        this.lower = (Comparable<Object>) lower;
        this.upper = (Comparable<Object>) upper;
    }
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var from = getRoot(property,root);
        var field = getProperty(property);
        return criteriaBuilder.between(from.get(field), lower, upper);
    }
}
