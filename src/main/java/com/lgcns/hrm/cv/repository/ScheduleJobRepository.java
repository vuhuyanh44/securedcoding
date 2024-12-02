package com.lgcns.hrm.cv.repository;

import com.lgcns.hrm.cv.entity.ScheduleJob;
import com.lgcns.hrm.cv.repository.base.BaseRepository;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ScheduleJobRepository extends BaseRepository<ScheduleJob, String> {

    List<ScheduleJob> findScheduleJobByJobStatus(String jobStatus);

    ScheduleJob findScheduleJobByJobKey(@NotNull(message = "Task cannot be empty") String jobKey);
}
