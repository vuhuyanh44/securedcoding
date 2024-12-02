package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "criteria")
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class Criteria extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Boolean status;

    private String code;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "criteria", fetch = FetchType.LAZY)
    private List<JobCriteria> jobCriteriaList;
}
