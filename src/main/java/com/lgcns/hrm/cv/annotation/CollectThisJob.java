package com.lgcns.hrm.cv.annotation;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;
/**
 * @author pigx
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Lazy
public @interface CollectThisJob {

    /**
     * bean Name
     *
     * @return String
     */
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";

    /**
     * Whether to lazy load
     *
     * @return boolean
     */
    @AliasFor(
            annotation = Lazy.class,
            value = "value"
    )
    boolean lazy() default true;

    /**
     * The name of the scheduled task
     *
     * @return String
     */
    String name();

    /**
     * cron expression
     *
     * @return Class
     */
    String cron() default "";

    /**
     * Parameter string in json format
     *
     * @return String
     */
    String paramJson() default "";

    /**
     * json format class parameter
     * <p>
     * If paramJson has a value, use the value defined by paramJson first
     * <p>
     * If there are too many parameters, paramJson is inconvenient, you can define the entity class to write, and give the default value
     *
     * @return Class<?>
     */
    Class<?> paramClass() default Object.class;

}
