package com.lgcns.hrm.cv.common.jpa.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Collection;

/**
 * @author pigx
 * @created 17/01/2024 - 10:53 AM
 * @project hr-api
 */
public class NotInSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    private final transient Collection<?> values;

    public NotInSpecification(String property, Collection<?> values) {
        this.property = property;
        this.values = values;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var from = getRoot(property, root);
        var field = getProperty(property);
        return from.get(field).in(values).not();
    }
}
