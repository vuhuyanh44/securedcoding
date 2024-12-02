package com.lgcns.hrm.cv.aop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private int limitMs = 50;

    @Around("execution(* com.lgcns.hrm.cv.repository.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        var start = System.currentTimeMillis();
        var proceed = joinPoint.proceed();
        var executionTime = System.currentTimeMillis() - start;
        var message = joinPoint.getSignature() + " exec in " + executionTime + " ms";
        if (executionTime >= limitMs) {
            log.warn(message + " : SLOW QUERY");
        }
        return proceed;
    }
}