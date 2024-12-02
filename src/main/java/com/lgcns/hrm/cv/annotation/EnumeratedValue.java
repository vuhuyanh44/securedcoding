package com.lgcns.hrm.cv.annotation;

import com.lgcns.hrm.cv.validation.EnumeratedValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(EnumeratedValue.List.class)
@Documented
@Constraint(validatedBy = {EnumeratedValueValidator.class})
public @interface EnumeratedValue {

    //Default error message
    String message() default "must be the specified value";

    String[] names() default {};

    int[] ordinals() default {};

    // group
    Class<?>[] groups() default {};

    // load
    Class<? extends Payload>[] payload() default {};

    //Use when specifying multiple
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        EnumeratedValue[] value();
    }
}