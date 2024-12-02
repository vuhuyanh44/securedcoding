package com.lgcns.hrm.cv.aop;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.utils.CollectionUtil;
import com.lgcns.hrm.cv.common.utils.DateUtil;
import com.lgcns.hrm.cv.common.utils.StringUtil;
import com.lgcns.hrm.cv.entity.ScheduleJobLog;
import com.lgcns.hrm.cv.model.enums.ScheduleStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.quartz.JobExecutionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JobAspect {

    private final SchedulerAsyncWorker schedulerAsyncWorker;

    private static final int MAX_LENGTH = 500;

    @Pointcut("execution(void *.execute*(org.quartz.JobExecutionContext))")
    public void pointCut() {
    }


    @Around(value = "pointCut()")
    public void afterHandler(ProceedingJoinPoint joinPoint) {
        var jobLog = new ScheduleJobLog();
        jobLog.setJobId(((JobExecutionContext) joinPoint.getArgs()[0]).getJobDetail().getKey().getName());
        try {
            jobLog.setStartTime(LocalDateTime.now());
            joinPoint.proceed(joinPoint.getArgs());
            jobLog.setEndTime(LocalDateTime.now());
            var seconds = (DateUtil.toDate(jobLog.getEndTime()).getTime() - DateUtil.toDate(jobLog.getStartTime()).getTime()) / 1000;
            jobLog.setElapsedSeconds(seconds);
            jobLog.setRunStatus(ErrorCodes.OK.getMessage());
            jobLog.setExecuteMsg("Execution succeeded");
        } catch (Throwable throwable) {
            log.error("Timed task execution exception", throwable);
            // Handle the exception and return the result
            var errorMsg = throwable.toString();
            var stackTraceElements = throwable.getStackTrace();
            if (CollectionUtil.isNotEmpty(List.of(stackTraceElements))) {
                errorMsg += " : " + stackTraceElements[0].toString();
            }
            errorMsg = StringUtil.left(ErrorCodes.SERVER_ERROR.getMessage() + ":" + errorMsg, MAX_LENGTH);
            jobLog.setRunStatus(ScheduleStatus.F.name());
            jobLog.setExecuteMsg(errorMsg);
        }
        // save log asynchronously
        schedulerAsyncWorker.saveScheduleJobLog(jobLog);
    }
}