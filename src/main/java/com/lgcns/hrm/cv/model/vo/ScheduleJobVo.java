package com.lgcns.hrm.cv.model.vo;

import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import com.lgcns.hrm.cv.model.enums.ScheduleStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ScheduleJobVo extends AbstractVo {
    private String id;
    private String jobKey;
    private String jobName;
    private String cron;
    private String paramJson;
    private String initStrategy;
    private String jobStatus;
    private Boolean saveLog;
    private String jobComment;
    public String getJobStatusLabel() {
        return ScheduleStatus.getLabel(this.getJobStatus());
    }
}
