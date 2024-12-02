package com.lgcns.hrm.cv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgcns.hrm.cv.common.utils.DateUtil;
import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "onboard_schedule")
@Getter
@Setter
@Accessors(chain = true)
public class OnboardSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "onboard_date")
    @JsonFormat(pattern = DateUtil.PATTERN_DATE_ORIGINAL)
    private Date onboardDate;

    private String note;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "onboardSchedule", fetch = FetchType.LAZY)
    private List<CandidateOnboardSchedule> candidateOnboardScheduleList;
}
