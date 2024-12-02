package com.lgcns.hrm.cv.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import com.lgcns.hrm.cv.entity.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sys_param")
@Getter
@Setter
public class SysParam extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	private String code;
	private String value;

	@Column(name = "dis_order", nullable = false)
	private int order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_type_id", referencedColumnName = "ID")
	private SysType sysType;

}
