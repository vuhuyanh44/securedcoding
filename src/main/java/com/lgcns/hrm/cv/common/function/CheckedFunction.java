package com.lgcns.hrm.cv.common.function;

import org.springframework.lang.Nullable;

import java.io.Serializable;

@FunctionalInterface
public interface CheckedFunction<T, R> extends Serializable {

    /**
     * Run the Function
     *
     * @param t T
     * @return R R
     * @throws Throwable CheckedException
     */
    @Nullable
    R apply(@Nullable T t) throws Throwable;

}