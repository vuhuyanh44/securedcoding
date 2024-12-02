package com.lgcns.hrm.cv.common.function;

import java.io.Serializable;

@FunctionalInterface
public interface CheckedRunnable extends Serializable {

    /**
     * Run this runnable.
     *
     * @throws Throwable CheckedException
     */
    void run() throws Throwable;

}

