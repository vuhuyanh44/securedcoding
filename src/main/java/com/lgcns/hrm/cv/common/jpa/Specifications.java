package com.lgcns.hrm.cv.common.jpa;

import jakarta.persistence.criteria.Predicate;

/**
 * @author pigx
 * @created 17/01/2024 - 10:59 AM
 * @project hr-api
 */
public final class Specifications {
    private Specifications() {
    }

    public static <T> PredicateBuilder<T> and() {
        return new PredicateBuilder<>(Predicate.BooleanOperator.AND);
    }

    public static <T> PredicateBuilder<T> or() {
        return new PredicateBuilder<>(Predicate.BooleanOperator.OR);
    }
}
