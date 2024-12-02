package com.lgcns.hrm.cv.validation;

import com.lgcns.hrm.cv.common.beans.PropertyResolver;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author pigx
 */
public class JwtEnabledValidation implements Condition {

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        return PropertyResolver.getBoolean(conditionContext, "hrm-env.security.jwt.enable");
    }
}

