package com.lgcns.hrm.cv.common.function;

import com.lgcns.hrm.cv.common.exception.ErrorCodeMapperBuilder;

@FunctionalInterface
public interface ErrorCodeMapperBuilderCustomizer {
    void customize(ErrorCodeMapperBuilder builder);
}

