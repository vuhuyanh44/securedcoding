package com.lgcns.hrm.cv.job;

import com.lgcns.hrm.cv.annotation.CollectThisJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pigx
 */
@Slf4j
@DisallowConcurrentExecution
@CollectThisJob(name = "Demo Job Log", paramJson = "{\"daysBefore\":30}", cron = "0 * * ? * *")
public class DemoLogJob extends QuartzJobBean {
    private static final String PARAM_KEY_DAYS_BEFORE = "daysBefore";

    /**
     * Executes the job.
     * Gets the parameter from the jobDataMap and sets the default value to 30 days.
     * Creates a list of parameters and adds the date from 30 days ago.
     * Logs the info with the parameters.
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // get parameters
        var jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        var days = 30;
        if (jobDataMap.containsKey(PARAM_KEY_DAYS_BEFORE)) {
            days = jobDataMap.getInt(PARAM_KEY_DAYS_BEFORE);
        }
        var params = new ArrayList<>(1);
        params.add("1");

        log.info("[PricingTool] - Demo log with params {}", params);
    }

}