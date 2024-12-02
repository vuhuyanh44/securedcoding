package com.lgcns.hrm.cv.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

public class Exceptions {
    public static RuntimeException unchecked(Throwable e) {
        if (e instanceof Error error) {
            throw error;
        } else if (e instanceof IllegalAccessException ||
                e instanceof IllegalArgumentException ||
                e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(e);
        } else if (e instanceof InvocationTargetException exception) {
            return Exceptions.runtime(exception.getTargetException());
        } else if (e instanceof RuntimeException exception) {
            return exception;
        } else if (e instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
        return Exceptions.runtime(e);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> T runtime(Throwable throwable) throws T {
        throw (T) throwable;
    }

    public static Throwable unwrap(Throwable wrapped) {
        Throwable unwrapped = wrapped;
        while (true) {
            if (unwrapped instanceof InvocationTargetException exception) {
                unwrapped = exception.getTargetException();
            } else if (unwrapped instanceof UndeclaredThrowableException exception) {
                unwrapped = exception.getUndeclaredThrowable();
            } else {
                return unwrapped;
            }
        }
    }
}
