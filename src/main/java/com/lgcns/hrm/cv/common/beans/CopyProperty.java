package com.lgcns.hrm.cv.common.beans;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CopyProperty {

    String value() default "";

    boolean ignore() default false;
}

