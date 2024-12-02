package com.lgcns.hrm.cv.common.jpa;

import com.lgcns.hrm.cv.common.jpa.specification.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static jakarta.persistence.criteria.Predicate.BooleanOperator.OR;

/**
 * @author pigx
 * @created 16/01/2024 - 4:26 PM
 * @project hr-api
 */
public class PredicateBuilder<T> {
    private final Predicate.BooleanOperator operator;
    private List<Specification<T>> specifications;
    private List<String> asc = new ArrayList<>();
    private List<String> desc = new ArrayList<>();

    public PredicateBuilder(Predicate.BooleanOperator operator) {
        this.operator = operator;
        this.specifications = new ArrayList<>();
    }

    public PredicateBuilder<T> eq(String property, Object... values) {
        return eq(true, property, values);
    }

    public PredicateBuilder<T> eq(boolean condition, String property, Object... values) {
        return this.predicate(condition, new EqualSpecification<>(property, values));
    }

    public PredicateBuilder<T> eq(String property, JoinType joinType, Object... values) {
        return eq(true, property, joinType, values);
    }

    public PredicateBuilder<T> eq(boolean condition, String property, JoinType joinType, Object... values) {
        return this.predicate(condition, new EqualSpecification<>(property, joinType, values));
    }

    public PredicateBuilder<T> ne(String property, Object... values) {
        return ne(true, property, values);
    }

    public PredicateBuilder<T> ne(boolean condition, String property, Object... values) {
        return this.predicate(condition, new NotEqualSpecification<>(property, values));
    }

    public PredicateBuilder<T> gt(String property, Comparable<?> comparable) {
        return gt(true, property, comparable);
    }

    public PredicateBuilder<T> gt(boolean condition, String property, Comparable<?> comparable) {
        return this.predicate(condition, new GeSpecification<>(property, comparable));
    }

    public PredicateBuilder<T> ge(String property, Comparable<?> comparable) {
        return ge(true, property, comparable);
    }

    public PredicateBuilder<T> ge(boolean condition, String property, Comparable<?> comparable) {
        return this.predicate(condition, new GeSpecification<>(property, comparable));
    }

    public PredicateBuilder<T> lt(String property, Comparable<?> comparable) {
        return lt(true, property, comparable);
    }

    public PredicateBuilder<T> lt(boolean condition, String property, Comparable<?> comparable) {
        return this.predicate(condition, new LtSpecification<>(property, comparable));
    }

    public PredicateBuilder<T> le(String property, Comparable<?> comparable) {
        return le(true, property, comparable);
    }

    public PredicateBuilder<T> le(boolean condition, String property, Comparable<?> comparable) {
        return this.predicate(condition, new LeSpecification<>(property, comparable));
    }

    public PredicateBuilder<T> between(String property, Object lower, Object upper) {
        return between(true, property, lower, upper);
    }

    public PredicateBuilder<T> between(boolean condition, String property, Object lower, Object upper) {
        return this.predicate(condition, new BetweenSpecification<>(property, lower, upper));
    }

    public PredicateBuilder<T> like(String property, String... patterns) {
        return like(true, property, patterns);
    }

    public PredicateBuilder<T> like(boolean condition, String property, String... patterns) {
        return this.predicate(condition, new LikeSpecification<>(property, patterns));
    }

    public PredicateBuilder<T> notLike(String property, String... patterns) {
        return notLike(true, property, patterns);
    }

    public PredicateBuilder<T> notLike(boolean condition, String property, String... patterns) {
        return this.predicate(condition, new NotLikeSpecification<>(property, patterns));
    }

    public PredicateBuilder<T> in(String property, Collection<?> values) {
        return in(true, property, values);
    }

    public PredicateBuilder<T> in(boolean condition, String property, Collection<?> values) {
        return this.predicate(condition, new InSpecification<>(property, values));
    }


    public PredicateBuilder<T> notIn(String property, Collection<?> values) {
        return notIn(true, property, values);
    }

    public PredicateBuilder<T> notNull(String property) {
        return notNull(true, property);
    }

    public PredicateBuilder<T> notNull(boolean condition, String property) {
        return this.predicate(condition, new NotNullSpecification<>(property));
    }

    public PredicateBuilder<T> notIn(boolean condition, String property, Collection<?> values) {
        return this.predicate(condition, new NotInSpecification<>(property, values));
    }

    public PredicateBuilder<T> predicate(boolean condition, Specification<T> specification) {
        if (condition) {
            this.specifications.add(specification);
        }
        return this;
    }

    public PredicateBuilder<T> asc(String... property) {
        this.asc.addAll(Arrays.stream(property).toList());
        return this;
    }

    public PredicateBuilder<T> desc(String... property) {
        this.desc.addAll(Arrays.stream(property).toList());
        return this;
    }

    public Specification<T> build() {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            var predicates = new Predicate[specifications.size()];
            for (int i = 0; i < specifications.size(); i++) {
                predicates[i] = specifications.get(i).toPredicate(root, query, cb);
            }
            if (Objects.equals(predicates.length, 0)) {
                return null;
            }
            query.orderBy(this.asc.stream().map(x -> cb.asc(root.get(x))).toList());
            query.orderBy(this.desc.stream().map(x -> cb.desc(root.get(x))).toList());
            query.where(OR.equals(operator) ? cb.or(predicates) : cb.and(predicates));
            return query.getRestriction();
        };
    }
}
