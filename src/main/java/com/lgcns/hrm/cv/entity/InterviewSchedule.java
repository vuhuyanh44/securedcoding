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
@Table(name = "interview_schedule")
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class InterviewSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", referencedColumnName = "ID")
    private Job job;

    @Column(name = "from_date")
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    private Date fromDate;

    @Column(name = "to_date")
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    private Date toDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location", referencedColumnName = "ID")
    private SysParam location;

    private String note;

    private String round;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interviewSchedule", fetch = FetchType.LAZY)
    private List<CandidateInterviewSchedule> candidateInterviewScheduleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interviewSchedule", fetch = FetchType.LAZY)
    private List<AssignedInterviewerSchedule> assignedInterviewerScheduleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interviewSchedule", fetch = FetchType.LAZY)
    private List<InterviewerResult> interviewerResultList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interviewSchedule", fetch = FetchType.LAZY)
    private List<InterviewFinalResult> interviewFinalResults;
}
