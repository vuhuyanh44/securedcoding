package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "interviewer_result")
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class InterviewerResult extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_schedule_id", referencedColumnName = "ID")
    private InterviewSchedule interviewSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_cv_id", referencedColumnName = "ID")
    private CandidatesCv candidateCv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", referencedColumnName = "ID")
    private Candidates candidate;

    @Column(name = "experience_capability")
    private String experienceCapability;

    @Column(name = "organization_compability")
    private String organizationCompability;

    @Column(name = "others_significant")
    private String othersSignificant;

    @Column(name = "general_evaluation")
    private String generalEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level", referencedColumnName = "ID")
    private SysParam level; // Junior/Middle/Senior

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round", referencedColumnName = "ID")
    private SysParam round;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result", referencedColumnName = "ID")
    private SysParam result; // pass/fail

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade", referencedColumnName = "ID")
    private SysParam grade; // S/A/B/C

    @Column(name = "gpa")
    private String gpa;

    @Column(name = "gpa_score")
    private Float gpaScore;

    private String achievement;

    @Column(name = "achievement_score")
    private Float achievementScore;

    @Column(name = "project_experience")
    private String projectExperience;

    @Column(name = "project_experience_score")
    private Float projectExperienceScore;

    @Column(name = "programming_languages")
    private String programmingLanguages;

    @Column(name = "programming_languages_score")
    private Float programmingLanguagesScore;

    @Column(name = "other_comment")
    private String otherComment;

    @Column(name = "other_comment_score")
    private Float otherCommentScore;

    private String personality;

    @Column(name = "personality_score")
    private Float personalityScore;

    @Column(name = "team_work")
    private String teamWork;

    @Column(name = "team_work_score")
    private Float teamWorkScore;

    @Column(name = "general_comment_compatibility")
    private String generalCommentCompatibility;

    @Column(name = "general_comment_score")
    private Float generalCommentScore;

    @Column(name = "potential")
    private String potential;

    @Column(name = "potential_score")
    private Float potentialScore;

    @Column(name = "result_score")
    private Float resultScore;

}
