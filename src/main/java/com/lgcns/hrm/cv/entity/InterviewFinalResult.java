package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "interview_final_result")
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class InterviewFinalResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_schedule_id", referencedColumnName = "ID")
    private InterviewSchedule interviewSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_cv_id", referencedColumnName = "ID")
    private CandidatesCv candidateCv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", referencedColumnName = "ID")
    private Candidates candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "final_result", referencedColumnName = "ID")
    private SysParam finalResult; // pass/fail

    private String note;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interviewFinalResult", fetch = FetchType.LAZY)
    private List<CandidatesStatus> candidatesStatuses;
}
