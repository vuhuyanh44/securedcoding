package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.entity.ScheduleJob;

import java.util.List;
import java.util.Map;

public interface QuartzSchedulerService {
    /**
     * Get a list of all tasks in the scheduler
     *
     * @return
     */
    List<Map<String, Object>> loadJobsInScheduler();

    /**
     * Add cron expression job
     *
     * @param job
     */
    void addJob(ScheduleJob job);

    /**
     * Add a job that executes once immediately
     *
     * @param job
     */
    void addJobExecuteOnce(ScheduleJob job);

    /**
     * Update the cron expression of a job
     *
     * @param jobId
     * @param cron
     */
    void updateJobCron(String jobId, String cron);

    /**
     * delete job
     *
     * @param jobId
     */
    void deleteJob(String jobId);


    /**
     * Determine whether there is a job
     *
     * @param jobId
     * @return
     */
    boolean existJob(String jobId);

    /**
     * Execute the job immediately
     *
     * @param jobId
     */
    void runJob(String jobId);

    /**
     * Pause job
     *
     * @param jobId
     */
    void pauseJob(String jobId);

    /**
     * resume job
     *
     * @param jobId
     */
    void resumeJob(String jobId);

    /**
     * Get all scheduled tasks in the current system
     *
     * @return
     */
    List<Map<String, Object>> loadAllJobs();
}
