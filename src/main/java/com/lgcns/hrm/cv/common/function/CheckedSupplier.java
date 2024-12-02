package com.lgcns.hrm.cv.common.function;

import org.springframework.lang.Nullable;

import java.io.Serializable;

@FunctionalInterface
public interface CheckedSupplier<T> extends Serializable {

    /**
     * Run the Supplier
     *
     * @return T
     * @throws Throwable CheckedException
     */
    @Nullable
    T get() throws Throwable;

}