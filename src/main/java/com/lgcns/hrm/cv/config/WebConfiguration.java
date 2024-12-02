package com.lgcns.hrm.cv.config;

import com.lgcns.hrm.cv.common.utils.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    public SpringUtil springContextUtil() {
        return new SpringUtil();
    }
}
