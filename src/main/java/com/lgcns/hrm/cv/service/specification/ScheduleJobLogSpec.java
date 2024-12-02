package com.lgcns.hrm.cv.service.specification;

import com.lgcns.hrm.cv.common.jpa.Specifications;
import com.lgcns.hrm.cv.common.utils.ObjectUtil;
import com.lgcns.hrm.cv.entity.ScheduleJobLog;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ScheduleJobLogSpec {

    public static Specification<ScheduleJobLog> findByCondition(String logId, String jobName, String jobKey, String runStatus) {
        return Specifications.<ScheduleJobLog>and()
                .eq(ObjectUtil.isNotEmpty(logId), "id", logId)
                .eq(ObjectUtil.isNotEmpty(jobName), "jobName", jobName)
                .eq(ObjectUtil.isNotEmpty(jobKey), "jobKey", jobKey)
                .eq(ObjectUtil.isNotEmpty(runStatus), "runStatus", runStatus)
                .desc("createTime")
                .build();

    }

    public static Specification<ScheduleJobLog> greaterThanOrEqualDay(LocalDateTime daysBefore) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("createdAt"), daysBefore);
    }
}
