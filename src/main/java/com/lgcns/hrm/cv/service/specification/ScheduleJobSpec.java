package com.lgcns.hrm.cv.service.specification;

import com.lgcns.hrm.cv.common.jpa.Specifications;
import com.lgcns.hrm.cv.common.utils.ObjectUtil;
import com.lgcns.hrm.cv.entity.ScheduleJob;
import org.springframework.data.jpa.domain.Specification;

public class ScheduleJobSpec {
    public static Specification<ScheduleJob> findByCondition(Long jobId, String jobName, String jobKey, String jobStatus) {
        return Specifications.<ScheduleJob>and()
                .eq(ObjectUtil.isNotEmpty(jobId),"id",jobId)
                .eq(ObjectUtil.isNotEmpty(jobKey),"jobKey",jobKey)
                .eq(ObjectUtil.isNotEmpty(jobName),"jobName",jobName)
                .eq(ObjectUtil.isNotEmpty(jobStatus),"jobStatus",jobStatus)
                .desc("createTime")
                .build();
    }
}
