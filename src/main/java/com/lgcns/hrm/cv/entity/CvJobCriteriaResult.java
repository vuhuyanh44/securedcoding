package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "criteria")
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class CvJobCriteriaResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_cv_id", referencedColumnName = "ID")
    private CandidatesCv candidateCv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_criteria_id", referencedColumnName = "ID")
    private JobCriteria jobCriteria;

    private Float score;
}
