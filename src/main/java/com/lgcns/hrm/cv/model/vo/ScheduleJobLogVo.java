package com.lgcns.hrm.cv.model.vo;

import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import com.lgcns.hrm.cv.model.enums.ScheduleStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class ScheduleJobLogVo extends AbstractVo {
    private Long id;
    private String jobKey;
    private String jobName;
    private String cron;
    private String paramJson;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long elapsedSeconds;
    private String runStatus;
    private Integer dataCount;
    private String executeMsg;
    public String getRunStatusLabel() {
        return ScheduleStatus.getLabel(this.getRunStatus());
    }

}