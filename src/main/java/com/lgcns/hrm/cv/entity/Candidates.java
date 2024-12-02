package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.common.utils.DateUtil;
import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "candidates")
@Getter
@Setter
@Accessors(chain = true)
public class Candidates extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate", fetch = FetchType.LAZY)
    private List<CandidatesCv> candidatesCv;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate", fetch = FetchType.LAZY)
    private List<CandidateInterviewSchedule> candidateInterviewScheduleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate", fetch = FetchType.LAZY)
    private List<InterviewerResult> interviewerResultList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate", fetch = FetchType.LAZY)
    private List<InterviewFinalResult> interviewFinalResultList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate", fetch = FetchType.LAZY)
    private List<CandidatesStatus> candidatesStatusList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate", fetch = FetchType.LAZY)
    private List<CandidateOnboardSchedule> candidateOnboardScheduleList;

    private String code;

    private String name;

    @Column(name = "university_name")
    private String universityName;

    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @Column(name = "dob")
    private Date dOB;

    @Column(name = "current_company")
    private String currentCompany;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @Column(name = "reference")
    private String references;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate", fetch = FetchType.LAZY)
    private List<CandidatesStatus> candidatesStatuses;

    @Column(name = "offer_date")
    private LocalDate offerDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", referencedColumnName = "ID")
    private SysParam status; // BLACK_LIST?

    private String address;

    @Transient
    private String receiveCvDateStr;

//    @Transient String checkCvDateStr;

    @Transient
    private String linkCv;

    @Transient
    private String offerDateStr;

    @Transient
    private String processDateFromReceiveToCheck;

    @Transient
    private String processDateFromCheckToOffer;

    @Transient
    private String interviewDateStr;

//    public String getOfferDateStr() {
//        return this.offerDate != null ? DateUtil.format(this.offerDate, DateUtil.PATTERN_DATE) : "";
//    }
//
//    public String getInterviewDateStr() {
//        return this.candidatesStatuses != null && !this.candidatesStatuses.isEmpty() ? DateUtil.format(this.getInterviewerResultList().get(this.getInterviewerResultList().size() - 1), DateUtil.PATTERN_DATE) : "";
//    }

}
