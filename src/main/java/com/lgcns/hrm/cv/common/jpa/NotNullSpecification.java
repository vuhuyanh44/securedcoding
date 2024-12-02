package com.lgcns.hrm.cv.common.jpa;

import com.lgcns.hrm.cv.common.jpa.specification.AbstractSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jetbrains.annotations.NotNull;

/**
 * @author tuannt
 * @created 16/05/2024 - 09:11 AM
 * @project hr-api
 */

public class NotNullSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    public NotNullSpecification(String property) {
        this.property = property;
    }

    @Override
    public Predicate toPredicate(Root<T> root, @NotNull CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isNotNull(root.get(property));
    }
}
