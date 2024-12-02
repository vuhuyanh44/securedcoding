package com.lgcns.hrm.cv.model.vo;

import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import com.lgcns.hrm.cv.common.utils.DateUtil;
import com.lgcns.hrm.cv.entity.CandidatesCv;
import com.lgcns.hrm.cv.entity.CandidatesStatus;
import com.lgcns.hrm.cv.entity.SysParam;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CandidateVo extends AbstractVo {
    private Integer id;
    private CandidatesCv candidatesCvId;
    private String code;
    private String name;
    private String universityName;
    private int yearOfBirth;
    private String currentCompany;
    private SysParam applyPosition;
    private String foreignLanguage;
    private String phoneNumber;
    private String email;
    private String references;
    private LocalDateTime receiveCvDate;
    private LocalDateTime receiveDateEmail;
    private LocalDateTime checkCvDate;
    private SysParam type;
    private SysParam status;
    private Date createTime;
    private String createDateStr;
    private String checkCvDateStr;
    private String skill;
    private LocalDate offerDate;
    private String comment;
    final static DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    private String subjectMail;
    public String getCreateDateStr() {
        return this.createTime == null ? "" : DateUtil.format(this.createTime, DateUtil.PATTERN_DATETIME);
    }
    public String getCheckCvDateStr() {
        return this.checkCvDate == null ? "" : DateUtil.format(this.checkCvDate, DateUtil.PATTERN_DATETIME);
    }
}
