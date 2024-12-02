package com.lgcns.hrm.cv.common.utils;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.function.Supplier;
public class Lazy<T> implements Supplier<T>, Serializable {

    @Nullable
    private transient volatile Supplier<? extends T> supplier;
    @Nullable
    private T value;

    public static <T> Lazy<T> of(final Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    private Lazy(final Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Nullable
    @Override
    public T get() {
        return (supplier == null) ? value : computeValue();
    }

    @Nullable
    private synchronized T computeValue() {
        final Supplier<? extends T> s = supplier;
        if (s != null) {
            value = s.get();
            supplier = null;
        }
        return value;
    }

}
