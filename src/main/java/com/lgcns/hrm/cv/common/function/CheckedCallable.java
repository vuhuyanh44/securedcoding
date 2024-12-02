package com.lgcns.hrm.cv.common.function;

import org.springframework.lang.Nullable;

import java.io.Serializable;

@FunctionalInterface
public interface CheckedCallable<T> extends Serializable {

    /**
     * Run this callable.
     *
     * @return result
     * @throws Throwable CheckedException
     */
    @Nullable
    T call() throws Throwable;
}

