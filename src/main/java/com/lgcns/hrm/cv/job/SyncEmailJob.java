package com.lgcns.hrm.cv.job;

import com.lgcns.hrm.cv.annotation.CollectThisJob;
import com.lgcns.hrm.cv.email.MailReceiveTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@DisallowConcurrentExecution
@RequiredArgsConstructor
@CollectThisJob(name = "Sync Email Job", paramJson = "{\"flagFolder\":\"DOWNLOADED\"}", cron = "0 * * ? * *")
public class SyncEmailJob extends QuartzJobBean {
    private final MailReceiveTemplate mailReceiveTemplate;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        var jobDataMap = context.getJobDetail().getJobDataMap();
        String flagFolder;
        if (jobDataMap.containsKey("flagFolder")) {
            flagFolder = jobDataMap.getString("flagFolder");
        } else {
            flagFolder = null;
        }
        mailReceiveTemplate.receiveUnreadMails(null, flagFolder);
    }
}
