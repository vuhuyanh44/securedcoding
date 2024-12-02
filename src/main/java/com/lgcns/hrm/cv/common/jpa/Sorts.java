package com.lgcns.hrm.cv.common.jpa;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pigx
 * @created 17/01/2024 - 9:28 AM
 * @project hr-api
 */
public final class Sorts {
    private Sorts() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<Sort.Order> orders;

        public Builder() {
            this.orders = new ArrayList<>();
        }

        public Builder asc(String property) {
            return asc(true, property);
        }

        public Builder desc(String property) {
            return desc(true, property);
        }

        public Builder asc(boolean condition, String property) {
            if (condition) {
                orders.add(new Sort.Order(Sort.Direction.ASC, property));
            }
            return this;
        }

        public Builder desc(boolean condition, String property) {
            if (condition) {
                orders.add(new Sort.Order(Sort.Direction.DESC, property));
            }
            return this;
        }

        public Sort build() {
            return Sort.by(orders);
        }
    }
}
