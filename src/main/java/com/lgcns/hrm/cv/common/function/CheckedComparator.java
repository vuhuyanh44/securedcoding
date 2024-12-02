package com.lgcns.hrm.cv.common.function;

import java.io.Serializable;

@FunctionalInterface
public interface CheckedComparator<T> extends Serializable {

    /**
     * Compares its two arguments for order.
     *
     * @param o1 o1
     * @param o2 o2
     * @return int
     * @throws Throwable CheckedException
     */
    int compare(T o1, T o2) throws Throwable;

}