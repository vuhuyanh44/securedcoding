package com.lgcns.hrm.cv.config;

import com.lgcns.hrm.cv.service.QuartzSchedulerService;
import com.lgcns.hrm.cv.service.impl.QuartzSchedulerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.lgcns.hrm.cv"})
public class SchedulerAutoConfig {
//    @Bean
//    @ConditionalOnMissingBean
//    public QuartzSchedulerService quartzSchedulerService() {
//        return new QuartzSchedulerServiceImpl();
//    }

    @Bean
    @QuartzDataSource
    @ConditionalOnMissingBean
    public DataSource quartzDataSource() {
        return DataSourceBuilder.create().build();
    }
}