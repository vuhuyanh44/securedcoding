package com.lgcns.hrm.cv.common.jpa.specification;

import com.lgcns.hrm.cv.common.utils.StringUtil;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;

/**
 * @author pigx
 * @created 16/01/2024 - 4:22 PM
 * @project hr-api
 */
public abstract class AbstractSpecification<T> implements Specification<T>, Serializable {
    public String getProperty(String property) {
        if (property.contains(".")) {
            return StringUtil.split(property, ".")[1];
        }
        return property;
    }

    public From getRoot(String property, Root<T> root, JoinType joinType) {
        if (property.contains(".")) {
            var joinProperty = StringUtil.split(property, ".")[1];
            switch (joinType) {
                case LEFT, RIGHT, INNER -> {
                    return root.join(joinProperty, joinType);
                }
                default -> {
                    return root.join(joinProperty);
                }
            }
        }
        return root;
    }

    public From getRoot(String property, Root<T> root) {
        if (property.contains(".")) {
            var joinProperty = StringUtil.split(property, ".")[1];
            return root.join(joinProperty);
        }
        return root;
    }
}
