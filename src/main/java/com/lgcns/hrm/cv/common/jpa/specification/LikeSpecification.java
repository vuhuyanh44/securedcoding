package com.lgcns.hrm.cv.common.jpa.specification;

import jakarta.persistence.criteria.*;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author pigx
 * @created 17/01/2024 - 10:18 AM
 * @project hr-api
 */
public class LikeSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    private final String[] patterns;

    public LikeSpecification(String property, String... patterns){
        this.patterns = patterns;
        this.property = property;
    }
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var from = getRoot(property, root);
        var field = getProperty(property);
        return Stream.ofNullable(patterns)
                .flatMap(Arrays::stream)
                .map(pattern -> criteriaBuilder.like(from.get(field), pattern))
                .reduce(criteriaBuilder::or)
                .orElseThrow(() -> new IllegalArgumentException("Patterns array must not be empty"));
    }
}
