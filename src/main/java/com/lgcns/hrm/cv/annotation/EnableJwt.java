package com.lgcns.hrm.cv.annotation;

import com.lgcns.hrm.cv.config.SecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Import(SecurityConfiguration.class)
public @interface EnableJwt {
}
