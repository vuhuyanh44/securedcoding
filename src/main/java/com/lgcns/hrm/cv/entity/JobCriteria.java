package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "job_criteria")
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class JobCriteria extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "ID")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "criteria_id", referencedColumnName = "ID")
    private Criteria criteria;

    private String fromValue;

    private String toValue;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobCriteria", fetch = FetchType.LAZY)
    private List<CvJobCriteriaResult> cvJobCriteriaResultList;
}
