package com.lgcns.hrm.cv.common.utils;

import com.lgcns.hrm.cv.common.constants.SymbolConstants;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

public class ObjectUtil extends org.springframework.util.ObjectUtils {

    public static boolean isNull(@Nullable Object object) {
        return Objects.isNull(object);
    }

    public static boolean isNotNull(@Nullable Object object) {
        return Objects.nonNull(object);
    }

    public static boolean isTrue(@Nullable Boolean object) {
        return Boolean.TRUE.equals(object);
    }

    public static boolean isFalse(@Nullable Boolean object) {
        return object == null || Boolean.FALSE.equals(object);
    }

    public static boolean isNotEmpty(@Nullable Object[] array) {
        return !ObjectUtils.isEmpty(array);
    }

    public static boolean isNotEmpty(@Nullable Object obj) {
        return !ObjectUtils.isEmpty(obj);
    }


    public static boolean equals(@Nullable Object o1, @Nullable Object o2) {
        return Objects.equals(o1, o2);
    }

    public static boolean isNotEqual(Object o1, Object o2) {
        return !Objects.equals(o1, o2);
    }


    public static int hashCode(@Nullable Object obj) {
        return Objects.hashCode(obj);
    }


    public static Object defaultIfNull(@Nullable Object object, Object defaultValue) {
        return object != null ? object : defaultValue;
    }

    @Nullable
    public static String toStr(@Nullable Object object) {
        return toStr(object, null);
    }

    @Nullable
    public static String toStr(@Nullable Object object, @Nullable String defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof CharSequence cs) {
            return cs.toString();
        }
        return String.valueOf(object);
    }

    public static int toInt(@Nullable Object object) {
        return toInt(object, 0);
    }

    public static int toInt(@Nullable Object object, int defaultValue) {
        if (object instanceof Number number) {
            return number.intValue();
        }
        if (object instanceof CharSequence cs) {
            String value = cs.toString();
            try {
                return Integer.parseInt(value);
            } catch (final NumberFormatException nfe) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static long toLong(@Nullable Object object) {
        return toLong(object, 0L);
    }

    public static long toLong(@Nullable Object object, long defaultValue) {
        if (object instanceof Number number) {
            return number.longValue();
        }
        if (object instanceof CharSequence cs) {
            String value = cs.toString();
            try {
                return Long.parseLong(value);
            } catch (final NumberFormatException nfe) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static float toFloat(@Nullable Object object) {
        return toFloat(object, 0.0f);
    }

    public static float toFloat(@Nullable Object object, float defaultValue) {
        if (object instanceof Number number) {
            return number.floatValue();
        }
        if (object instanceof CharSequence cs) {
            String value = cs.toString();
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException nfe) {
                return defaultValue;
            }
        }
        return defaultValue;
    }


    public static double toDouble(@Nullable Object object) {
        return toDouble(object, 0.0d);
    }


    public static double toDouble(@Nullable Object object, double defaultValue) {
        if (object instanceof Number number) {
            return number.doubleValue();
        }
        if (object instanceof CharSequence cs) {
            String value = cs.toString();
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException nfe) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static byte toByte(@Nullable Object object) {
        return toByte(object, (byte) 0);
    }

    public static byte toByte(@Nullable Object object, byte defaultValue) {
        if (object instanceof Number number) {
            return number.byteValue();
        }
        if (object instanceof CharSequence cs) {
            String value = cs.toString();
            try {
                return Byte.parseByte(value);
            } catch (NumberFormatException nfe) {
                return defaultValue;
            }
        }
        return defaultValue;
    }


    public static short toShort(@Nullable Object object) {
        return toShort(object, (short) 0);
    }

    public static short toShort(@Nullable Object object, short defaultValue) {
        if (object instanceof Number number) {
            return number.byteValue();
        }
        if (object instanceof CharSequence cs) {
            String value = cs.toString();
            try {
                return Short.parseShort(value);
            } catch (NumberFormatException nfe) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    @Nullable
    public static Boolean toBoolean(@Nullable Object object) {
        return toBoolean(object, null);
    }

    @Nullable
    public static Boolean toBoolean(@Nullable Object object, @Nullable Boolean defaultValue) {
        if (object instanceof Boolean bool) {
            return bool;
        } else if (object instanceof CharSequence cs) {
            String value = cs.toString();
            if (SymbolConstants.TRUE.equalsIgnoreCase(value) ||
                    SymbolConstants.Y.equalsIgnoreCase(value) ||
                    SymbolConstants.YES.equalsIgnoreCase(value) ||
                    SymbolConstants.ON.equalsIgnoreCase(value) ||
                    SymbolConstants.ONE.equalsIgnoreCase(value)) {
                return true;
            } else if (SymbolConstants.FALSE.equalsIgnoreCase(value) ||
                    SymbolConstants.N.equalsIgnoreCase(value) ||
                    SymbolConstants.NO.equalsIgnoreCase(value) ||
                    SymbolConstants.OFF.equalsIgnoreCase(value) ||
                    SymbolConstants.ZERO.equalsIgnoreCase(value)) {
                return false;
            }
        }
        return defaultValue;
    }
}
