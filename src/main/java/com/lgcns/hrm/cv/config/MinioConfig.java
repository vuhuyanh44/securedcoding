package com.lgcns.hrm.cv.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Value("${spring.minio.access-key}")
    private String accessKey;

    @Value("${spring.minio.secret-key}")
    private String secretKey;

    @Value("${spring.minio.end-point}")
    private String minioUrl;

    @Bean
    public MinioClient minioClient() {
        return new MinioClient.Builder().credentials(accessKey, secretKey).endpoint(minioUrl).build();
    }
}
