package com.lgcns.hrm.cv.common.jpa.specification;

import jakarta.persistence.criteria.*;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author pigx
 * @created 17/01/2024 - 9:55 AM
 * @project hr-api
 */
public class EqualSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    private final transient Object[] values;

    private final JoinType joinType;

    public EqualSpecification(String property, JoinType joinType, Object... values) {
        this.property = property;
        this.values = values;
        this.joinType = joinType;
    }

    public EqualSpecification(String property, Object... values) {
        this.property = property;
        this.values = values;
        this.joinType = null;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var from = joinType == null ? getRoot(property, root) : getRoot(property, root, joinType);
        var field = getProperty(property);
        return Stream.ofNullable(values)
                .flatMap(Arrays::stream)
                .map(value -> getPredicate(from, criteriaBuilder, value, field))
                .reduce(criteriaBuilder::or)
                .orElseGet(() -> criteriaBuilder.isNull(from.get(field)));

    }

    private Predicate getPredicate(From root, CriteriaBuilder cb, Object value, String field) {
        return value == null ? cb.isNull(root.get(field)) : cb.equal(root.get(field), value);
    }
}
