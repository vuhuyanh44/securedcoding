package com.lgcns.hrm.cv.common.utils;

import org.springframework.lang.Nullable;


public class NumberUtil extends org.springframework.util.NumberUtils {

    public static int toInt(final String str) {
        return ObjectUtil.toInt(str, 0);
    }

    public static int toInt(@Nullable final String str, final int defaultValue) {
        return ObjectUtil.toInt(str, defaultValue);
    }

    public static long toLong(final String str) {
        return ObjectUtil.toLong(str, 0L);
    }

    public static long toLong(@Nullable final String str, final long defaultValue) {
        return ObjectUtil.toLong(str, defaultValue);
    }

    static final byte[] DIGITS = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '_', '-'
    };


    public static String to62Str(long i) {
        int radix = 62;
        byte[] buf = new byte[65];
        int charPos = 64;
        i = -i;
        while (i <= -radix) {
            buf[charPos--] = DIGITS[(int) (-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = DIGITS[(int) (-i)];
        return new String(buf, charPos, (65 - charPos), Charsets.UTF_8);
    }


    public static long form62Str(String s) {
        char[] chars = s.toCharArray();
        char c;
        int idx;
        long res = 0;
        int len = chars.length;
        int lenIdx = len - 1;
        for (int i = 0; i < len; i++) {
            c = chars[i];
            // 将字符转换为对应的数字
            if (c >= 'A' && c <= 'Z') {
                idx = c - 29;
            } else if (c >= 'a' && c <= 'z') {
                idx = c - 87;
            } else {
                idx = c - 48;
            }
            res += (long) (idx * StrictMath.pow(62, lenIdx - i));
        }
        return res;
    }

}