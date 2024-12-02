package com.lgcns.hrm.cv.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author pigx
 */
@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableJpaAuditing
public class DataSourceConfiguration {
    private final DataSourceProperties dataSourceProperties;

    @Primary
    @Bean
    public DataSource dataSource(){
        var hikariConfig=new HikariConfig();
        hikariConfig.setJdbcUrl(dataSourceProperties.getUrl());
        hikariConfig.setUsername(dataSourceProperties.getUsername());
        hikariConfig.setPassword(dataSourceProperties.getPassword());
        hikariConfig.setDriverClassName(dataSourceProperties.getDriverClassName());
        return new HikariDataSource(hikariConfig);
    }
}
