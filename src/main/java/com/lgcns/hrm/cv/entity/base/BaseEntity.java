package com.lgcns.hrm.cv.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import com.lgcns.hrm.cv.common.definition.domain.AbstractEntity;
import com.lgcns.hrm.cv.common.utils.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends AbstractEntity {

    @Schema(name = "Data Creation Time")
    @Column(name = "create_time", updatable = false)
    @CreatedDate
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    private Date createTime = new Date();

    @Schema(name = "Data update time")
    @Column(name = "update_time")
    @LastModifiedDate
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    private Date updateTime = new Date();

    @Schema(name = "Creator")
    @Column(name = "create_by")
    @CreatedBy
    private String createBy;

    @Schema(name = "Last modified")
    @Column(name = "update_by")
    @LastModifiedBy
    private String updateBy;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("createTime", createTime)
                .add("updateTime", updateTime)
                .add("createBy", createBy)
                .add("updateBy", updateBy)
                .toString();
    }
}