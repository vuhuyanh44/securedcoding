package com.lgcns.hrm.cv.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import com.lgcns.hrm.cv.entity.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "sys_type")
@Getter
@Setter
public class SysType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String code;

    private String name;

}
