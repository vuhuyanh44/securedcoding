package com.lgcns.hrm.cv.common.utils;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
@SuppressWarnings("unchecked")
public class ThreadLocalUtil {
    private static final ThreadLocal<Map<String, Object>> LOCAL = ThreadLocal.withInitial(HashMap::new);

    public static Map<String, Object> getAll() {
        return new HashMap<>(LOCAL.get());
    }

    public static <T> T put(String key, T value) {
        LOCAL.get().put(key, value);
        return value;
    }


    public static void put(Map<String, Object> map) {
        LOCAL.get().putAll(map);
    }

    public static void remove(String key) {
        LOCAL.get().remove(key);
    }

    public static void clear() {
        LOCAL.remove();
    }

    @Nullable
    public static <T> T get(String key) {
        return ((T) LOCAL.get().get(key));
    }

    @Nullable
    public static <T> T getIfAbsent(String key, Supplier<T> supplierOnNull) {
        return ((T) LOCAL.get().computeIfAbsent(key, k -> supplierOnNull.get()));
    }


    @Nullable
    public static <T> T getAndRemove(String key) {
        try {
            return get(key);
        } finally {
            remove(key);
        }
    }

}
