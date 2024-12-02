package com.lgcns.hrm.cv.entity;

import com.lgcns.hrm.cv.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "excel_template")
@Getter
@Setter
public class ExcelTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", referencedColumnName = "ID")
    private SysParam type;

    private String title;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "sheet_seq_no")
    private String sheetSeqNo;

    @Column(name = "start_row")
    private Integer startRow;

    @Column(name = "active_ind")
    private String activeInd;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "excelTemplate", fetch = FetchType.LAZY)
    private List<ExcelTemplateDetail> excelTemplateDetailList;
}
