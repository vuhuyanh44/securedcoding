package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.common.utils.DateUtil;
import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "candidates_cv")
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class CandidatesCv extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", referencedColumnName = "ID")
    private Candidates candidate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateCv", fetch = FetchType.LAZY)
    private List<CandidateInterviewSchedule> candidateInterviewScheduleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateCv", fetch = FetchType.LAZY)
    private List<InterviewerResult> interviewerResultList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateCv", fetch = FetchType.LAZY)
    private List<InterviewFinalResult> interviewFinalResultList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateCv", fetch = FetchType.LAZY)
    private List<CvJobCriteriaResult> cvJobCriteriaResultList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateCv", fetch = FetchType.LAZY)
    private List<CandidatesStatus> candidatesStatusList;

    @Column(name = "email_number")
    private Long emailNumber;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_path", length = 1000)
    private String filePath;

    private Boolean status;

    @Column(name = "bucket_name")
    private String BucketName;

    @Column(name = "download_link", length = 1000)
    private String downloadLink;

    @Column(name = "comment", length = 1000)
    private String comment;

    @Column(name = "subject_mail", length = 1000)
    private String subjectMail;

    @Column(name = "receive_date_email")
    private LocalDateTime receiveDateEmail;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "receive_cv_date")
    private LocalDateTime receiveCvDate;

    @Column(name = "check_cv_date")
    private LocalDateTime checkCvDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", referencedColumnName = "ID")
    private SysParam type;

    @Column(name = "skill", length = 1000)
    private String skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_position", referencedColumnName = "ID")
    private SysParam applyPosition;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "notice")
    private String notice;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "auto_evaluated")
    private Boolean autoEvaluated;

    public String getReceiveCvDateStr() {
        return this.receiveCvDate != null ? DateUtil.format(this.receiveCvDate, DateUtil.PATTERN_DATE) : "";
    }

    public String getCheckCvDateStr() {
        return this.checkCvDate != null ? DateUtil.format(this.checkCvDate, DateUtil.PATTERN_DATE) : "";
    }
}
