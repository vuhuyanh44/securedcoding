package com.lgcns.hrm.cv.annotation;

import com.lgcns.hrm.cv.validation.JwtEnabledValidation;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(JwtEnabledValidation.class)
public @interface ConditionOnJwtEnabled {
}
