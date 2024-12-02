package com.lgcns.hrm.cv.common.utils;
import org.springframework.lang.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
public class Once {
    private final AtomicBoolean value;

    public Once() {
        this.value = new AtomicBoolean(false);
    }

    public boolean canRun() {
        return value.compareAndSet(false, true);
    }

    public <T> void run(Consumer<T> consumer, T argument) {
        if (canRun()) {
            consumer.accept(argument);
        }
    }

    public <T, U> void run(BiConsumer<T, U> consumer, T arg1, U arg2) {
        if (canRun()) {
            consumer.accept(arg1, arg2);
        }
    }

    @Nullable
    public <T, R> R run(Function<T, R> function, T argument) {
        if (canRun()) {
            return function.apply(argument);
        }
        return null;
    }

}

