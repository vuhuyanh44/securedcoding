package com.lgcns.hrm.cv.common.jpa.specification;

import jakarta.persistence.criteria.*;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author pigx
 * @created 17/01/2024 - 10:42 AM
 * @project hr-api
 */
public class NotEqualSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    private final transient Object[] values;

    public NotEqualSpecification(String property, Object... values) {
        this.property = property;
        this.values = values;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var from = getRoot(property, root);
        var field = getProperty(property);
        return Stream.ofNullable(values)
                .flatMap(Arrays::stream)
                .map(value -> getPredicate(from, criteriaBuilder, value, field))
                .reduce(criteriaBuilder::or)
                .orElse(criteriaBuilder.isNotNull(from.get(field)));
    }

    private Predicate getPredicate(From root, CriteriaBuilder criteriaBuilder, Object value, String field) {
        return value == null ? criteriaBuilder.isNotNull(root.get(field)) : criteriaBuilder.notEqual(root.get(field), value);
    }
}
