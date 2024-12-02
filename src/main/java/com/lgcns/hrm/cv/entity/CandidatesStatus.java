package com.lgcns.hrm.cv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "candidates_status")
@Getter
@Setter
public class CandidatesStatus extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidates_id", referencedColumnName = "ID")
    @JsonIgnore
    private Candidates candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_cv_id", referencedColumnName = "ID")
    @JsonIgnore
    private CandidatesCv candidateCv;

    @Column(name = "status")
    private String status;

    @Column(name = "current_status")
    private Boolean currentStatus;

    @Column(name = "round_number")
    private Integer roundNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_final_result_id", referencedColumnName = "ID")
    @JsonIgnore
    private InterviewFinalResult interviewFinalResult;

}
