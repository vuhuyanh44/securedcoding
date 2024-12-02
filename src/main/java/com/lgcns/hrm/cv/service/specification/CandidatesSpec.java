package com.lgcns.hrm.cv.service.specification;

import com.lgcns.hrm.cv.common.jpa.Specifications;
import com.lgcns.hrm.cv.common.utils.DateUtil;
import com.lgcns.hrm.cv.entity.Candidates;
import com.lgcns.hrm.cv.entity.SysParam;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CandidatesSpec {


    public static Specification<Candidates> findByCondition(String startDate, String endDate, String status, String type,
                                                            String name, String applyPosition, String startDateReview, String endDateReview) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            List<Predicate> predicates = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(startDate)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), DateUtil.parse(startDate, DateUtil.PATTERN_DATE)));
            }
            if (ObjectUtils.isNotEmpty(endDate)) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime"), DateUtil.parse(endDate, DateUtil.PATTERN_DATE)));
            }

            if (ObjectUtils.isNotEmpty(startDateReview)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("checkCvDate"), DateUtil.parse(startDateReview, DateUtil.PATTERN_DATE)));
            }
            if (ObjectUtils.isNotEmpty(endDateReview)) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("checkCvDate"), DateUtil.parse(endDateReview, DateUtil.PATTERN_DATE)));
            }

            if (ObjectUtils.isNotEmpty(status)) {
                Join<SysParam, String> paramObj = root.join("status");
                predicates.add(criteriaBuilder.equal(paramObj.get("code"), status));
            }
            if (ObjectUtils.isNotEmpty(type)) {
                Join<SysParam, String> paramObj = root.join("type");
                predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(paramObj.get("value")), type.toUpperCase()));
            }
            if (ObjectUtils.isNotEmpty(applyPosition)) {
                Join<SysParam, String> paramObj = root.join("applyPosition");
                predicates.add(criteriaBuilder.equal(paramObj.get("id"), applyPosition));
            }
            if (ObjectUtils.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + name + "%"), criteriaBuilder.like(root.get("subjectMail"), "%" + name + "%"),
                        criteriaBuilder.like(root.get("email"), "%" + name + "%")));
            }
//            if (authentication != null && authentication.getAuthorities().stream().anyMatch(group -> "admin".equals(group.getAuthority()))) {
//                Join<Candidates, CandidatesStatus> candidatesStatuses = root.join("candidatesStatuses", JoinType.INNER);
//                Join<CandidatesStatus, CandidateInterviewer> interviewerJoin = candidatesStatuses.join("interviewers", JoinType.LEFT);
//                predicates.add(criteriaBuilder.equal(interviewerJoin.get("interviewer"), authentication.getName()));
//            }
            Predicate[] predicateArray = new Predicate[predicates.size()];
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
            return criteriaQuery.getRestriction();
        };
    }

    public static Specification<Candidates> findByEmailAndLastApply(String email, LocalDate lastDateApply) {
        return Specifications.<Candidates>and()
                .eq( "email", email)
                .gt( "createTime", lastDateApply)
                .desc("createTime")
                .build();
    }

    public static Specification<Candidates> findByEmailsAndHasOfferDate(String email) {
        return Specifications.<Candidates>and()
                .eq( "email", email)
                .notNull("offerDate")
                .desc("createTime")
                .build();
    }
}
