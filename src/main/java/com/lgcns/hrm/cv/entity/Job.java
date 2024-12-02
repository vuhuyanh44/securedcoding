package com.lgcns.hrm.cv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgcns.hrm.cv.common.utils.DateUtil;
import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "job")
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class Job extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job", fetch = FetchType.LAZY)
    private List<InterviewSchedule> interviewScheduleList;

    private String name;

    @Column(name = "posted_date")
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    private Date postedDate;

    @Column(name = "expired_date")
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    private Date expiredDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job", fetch = FetchType.LAZY)
    private List<JobCriteria> jobCriteriaList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job", fetch = FetchType.LAZY)
    private List<Subscription> subscriptionList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job", fetch = FetchType.LAZY)
    private List<CandidateOnboardSchedule> candidateOnboardScheduleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job", fetch = FetchType.LAZY)
    private List<JobCriteriaLog> jobCriteriaLogList;
}
