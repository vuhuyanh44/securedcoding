package com.lgcns.hrm.cv.common.jpa.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author pigx
 * @created 17/01/2024 - 10:55 AM
 * @project hr-api
 */
public class NotLikeSpecification<T> extends AbstractSpecification<T>{
    private final String property;
    private final transient String[] patterns;

    public NotLikeSpecification(String property,String... patterns){
        this.property= property;
        this.patterns = patterns;
    }
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var from = getRoot(property, root);
        var field = getProperty(property);
        return Stream.ofNullable(patterns)
                .flatMap(Arrays::stream)
                .map(pattern -> criteriaBuilder.like(from.get(field), pattern).not())
                .reduce(criteriaBuilder::or)
                .orElseThrow(() -> new IllegalStateException("No patterns provided"));
    }
}
