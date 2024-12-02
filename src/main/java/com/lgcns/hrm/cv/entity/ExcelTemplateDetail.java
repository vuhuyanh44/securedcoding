package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "excel_template_detail")
@Getter
@Setter
public class ExcelTemplateDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excel_template_id", referencedColumnName = "ID")
    private ExcelTemplate excelTemplate;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_type")
    private String fieldType;

    private Integer position;

    private Integer width;

    private Integer alignment;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "column_full_name")
    private String columnFullName;

    private String pattern;

    @Column(name = "mandatory_ind")
    private Integer mandatoryInd;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "column_size")
    private Integer columnSize;

    @Column(name = "active_ind")
    private Integer activeInd;

    @Column(name = "cell_type")
    private String cellType;
}
