package com.lgcns.hrm.cv.service.specification;

import com.lgcns.hrm.cv.entity.SysParam;
import com.lgcns.hrm.cv.entity.SysType;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SysParamSpec {
    public static Specification<SysParam> findByCondition(String searchType, String searchCode) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(searchType)) {
                Join<SysType,String> type = root.join("sysType");
                predicates.add(criteriaBuilder.equal(type.get("id"), searchType));
            }
            if (ObjectUtils.isNotEmpty(searchCode)) {
                predicates.add(criteriaBuilder.like(root.get("code"), "%" + searchCode + "%"));
            }
            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")),criteriaBuilder.desc(root.get("createTime")));
            return criteriaQuery.getRestriction();
        };
    }

    public static Specification<SysParam> findByType(Integer type) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(type)) {
                Join<SysType,String> sysType = root.join("sysType");
                predicates.add(criteriaBuilder.equal(sysType.get("id"), type));
            }
            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")),criteriaBuilder.desc(root.get("createTime")));
            return criteriaQuery.getRestriction();
        };
    }

    public static Specification<SysParam> findByTypeAndCode(String typeCode, String code) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(typeCode)) {
                Join<SysType,String> sysType = root.join("sysType");
                predicates.add(criteriaBuilder.equal(sysType.get("code"), typeCode));
            }
            predicates.add(criteriaBuilder.equal(root.get("code"), code));
            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            return criteriaQuery.getRestriction();
        };
    }
}
