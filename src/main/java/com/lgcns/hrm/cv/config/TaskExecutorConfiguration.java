package com.lgcns.hrm.cv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class TaskExecutorConfiguration implements AsyncConfigurer {
    public static final int cpuNum = Runtime.getRuntime().availableProcessors();

    @Value("${hrm-env.thread.pool.corePoolSize:}")
    private Optional<Integer> corePoolSize;

    @Value("${hrm-env.thread.pool.maxPoolSize:}")
    private Optional<Integer> maxPoolSize;

    @Value("${hrm-env.thread.pool.queueCapacity:}")
    private Optional<Integer> queueCapacity;

    @Value("${hrm-env.thread.pool.awaitTerminationSeconds:1}")
    private Optional<Integer> awaitTerminationSeconds;

    @Override
    @Bean(name = "threadPoolTaskExecutor")
    public Executor getAsyncExecutor() {
        var taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize.orElse(cpuNum));
        taskExecutor.setMaxPoolSize(maxPoolSize.orElse(cpuNum * 2));
        taskExecutor.setQueueCapacity(queueCapacity.orElse(500));
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds.orElse(60));
        taskExecutor.setThreadNamePrefix("PIG-Thread-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
