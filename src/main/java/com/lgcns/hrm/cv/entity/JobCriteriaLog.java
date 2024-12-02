package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "job_criteria_log")
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class JobCriteriaLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", referencedColumnName = "ID")
    private SysParam status; // NEW/UPDATED

    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "ID")
    private Job job;
}
