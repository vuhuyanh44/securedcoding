package com.lgcns.hrm.cv.common.utils;

import com.lgcns.hrm.cv.common.constants.CharPool;
import com.lgcns.hrm.cv.common.constants.SymbolConstants;
import org.springframework.lang.Nullable;
import org.springframework.util.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StringUtil extends org.springframework.util.StringUtils {
    public static final int INDEX_NOT_FOUND = -1;

    private static final Pattern SPECIAL_CHARS_REGEX = Pattern.compile("[`'\"|/,;()-+*%#·•�　\\s]");
    /**
     * <p>The maximum size to which the padding constant(s) can expand.</p>
     */
    private static final int PAD_LIMIT = 8192;


    public static String firstCharToLower(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= CharPool.UPPER_A && firstChar <= CharPool.UPPER_Z) {
            char[] arr = str.toCharArray();
            arr[0] += (CharPool.LOWER_A - CharPool.UPPER_A);
            return new String(arr);
        }
        return str;
    }


    public static String firstCharToUpper(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= CharPool.LOWER_A && firstChar <= CharPool.LOWER_Z) {
            char[] arr = str.toCharArray();
            arr[0] -= (CharPool.LOWER_A - CharPool.UPPER_A);
            return new String(arr);
        }
        return str;
    }

    /**
     * Check whether the given {@code CharSequence} contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code CharSequence} is not {@code null}, its length is greater than
     * 0, and it contains at least one non-whitespace character.
     * <pre class="code">
     * StringUtil.isBlank(null) = true
     * StringUtil.isBlank("") = true
     * StringUtil.isBlank(" ") = true
     * StringUtil.isBlank("12345") = false
     * StringUtil.isBlank(" 12345 ") = false
     * </pre>
     *
     * @param cs the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null},
     * its length is greater than 0, and it does not contain whitespace only
     * @see Character#isWhitespace
     */
    public static boolean isBlank(@Nullable final CharSequence cs) {
        return !StringUtils.hasText(cs);
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     * <pre>
     * StringUtil.isNotBlank(null)	  = false
     * StringUtil.isNotBlank("")		= false
     * StringUtil.isNotBlank(" ")	   = false
     * StringUtil.isNotBlank("bob")	 = true
     * StringUtil.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     * not empty and not null and not whitespace
     * @see Character#isWhitespace
     */
    public static boolean isNotBlank(@Nullable final CharSequence cs) {
        return StringUtils.hasText(cs);
    }

    public static boolean isAnyBlank(final CharSequence... css) {
        if (ObjectUtils.isEmpty(css)) {
            return true;
        }
        return Stream.of(css).anyMatch(StringUtil::isBlank);
    }


    public static boolean isAnyBlank(Collection<CharSequence> css) {
        if (CollectionUtils.isEmpty(css)) {
            return true;
        }
        return css.stream().anyMatch(StringUtil::isBlank);
    }


    public static boolean isNoneBlank(final CharSequence... css) {
        if (ObjectUtils.isEmpty(css)) {
            return false;
        }
        return Stream.of(css).allMatch(StringUtil::isNotBlank);
    }

    public static boolean isNoneBlank(Collection<CharSequence> css) {
        if (CollectionUtils.isEmpty(css)) {
            return false;
        }
        return css.stream().allMatch(StringUtil::isNotBlank);
    }


    public static boolean isAnyNotBlank(CharSequence... css) {
        if (ObjectUtils.isEmpty(css)) {
            return false;
        }
        return Stream.of(css).anyMatch(StringUtil::isNotBlank);
    }


    public static boolean isAnyNotBlank(Collection<CharSequence> css) {
        if (CollectionUtils.isEmpty(css)) {
            return false;
        }
        return css.stream().anyMatch(StringUtil::isNotBlank);
    }


    public static boolean isNumeric(final CharSequence cs) {
        if (StringUtil.isBlank(cs)) {
            return false;
        }
        for (int i = cs.length(); --i >= 0; ) {
            int chr = cs.charAt(i);
            if (chr < 48 || chr > 57) {
                return false;
            }
        }
        return true;
    }

    /**
     * startWith char
     *
     * @param cs CharSequence
     * @param c  char
     * @return {boolean}
     */
    public static boolean startWith(CharSequence cs, char c) {
        return cs.charAt(0) == c;
    }

    /**
     * endWith char
     *
     * @param cs CharSequence
     * @param c  char
     * @return {boolean}
     */
    public static boolean endWith(CharSequence cs, char c) {
        return cs.charAt(cs.length() - 1) == c;
    }


    public static String format(@Nullable String message, @Nullable Map<String, ?> params) {

        if (message == null) {
            return SymbolConstants.EMPTY;
        }

        if (params == null || params.isEmpty()) {
            return message;
        }

        StringBuilder sb = new StringBuilder((int) (message.length() * 1.5));
        int cursor = 0;
        for (int start, end; (start = message.indexOf(SymbolConstants.DOLLAR_LEFT_BRACE, cursor)) != -1 && (end = message.indexOf(CharPool.RIGHT_BRACE, start)) != -1; ) {
            sb.append(message, cursor, start);
            String key = message.substring(start + 2, end);
            Object value = params.get(key.trim());
            sb.append(value == null ? SymbolConstants.EMPTY : value);
            cursor = end + 1;
        }
        sb.append(message.substring(cursor));
        return sb.toString();
    }

    public static String format(@Nullable String message, @Nullable Object... arguments) {

        if (message == null) {
            return SymbolConstants.EMPTY;
        }

        if (arguments == null || arguments.length == 0) {
            return message;
        }
        StringBuilder sb = new StringBuilder((int) (message.length() * 1.5));
        int cursor = 0;
        int index = 0;
        int argsLength = arguments.length;
        for (int start, end; (start = message.indexOf(CharPool.LEFT_BRACE, cursor)) != -1 && (end = message.indexOf(CharPool.RIGHT_BRACE, start)) != -1 && index < argsLength; ) {
            sb.append(message, cursor, start);
            sb.append(arguments[index]);
            cursor = end + 1;
            index++;
        }
        sb.append(message.substring(cursor));
        return sb.toString();
    }


    public static String format(long nanos) {
        if (nanos < 1) {
            return "0ms";
        }
        double millis = (double) nanos / (1000 * 1000);
        // 不够 1 ms，最小单位为 ms
        if (millis > 1000) {
            return String.format("%.3fs", millis / 1000);
        } else {
            return String.format("%.3fms", millis);
        }
    }

    /**
     * Convert a {@code Collection} into a delimited {@code String} (e.g., CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param coll the {@code Collection} to convert
     * @return the delimited {@code String}
     */
    public static String join(Collection<?> coll) {
        return StringUtils.collectionToCommaDelimitedString(coll);
    }

    /**
     * Convert a {@code Collection} into a delimited {@code String} (e.g. CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param coll  the {@code Collection} to convert
     * @param delim the delimiter to use (typically a ",")
     * @return the delimited {@code String}
     */
    public static String join(Collection<?> coll, String delim) {
        return StringUtils.collectionToDelimitedString(coll, delim);
    }

    /**
     * Convert a {@code String} array into a comma delimited {@code String}
     * (i.e., CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param arr the array to display
     * @return the delimited {@code String}
     */
    public static String join(Object[] arr) {
        return StringUtils.arrayToCommaDelimitedString(arr);
    }

    /**
     * Convert a {@code String} array into a delimited {@code String} (e.g. CSV).
     * <p>Useful for {@code toString()} implementations.
     *
     * @param arr   the array to display
     * @param delim the delimiter to use (typically a ",")
     * @return the delimited {@code String}
     */
    public static String join(Object[] arr, String delim) {
        return StringUtils.arrayToDelimitedString(arr, delim);
    }

    public static String[] split(@Nullable String str, @Nullable String delimiter) {
        return StringUtils.delimitedListToStringArray(str, delimiter);
    }

    public static String[] splitTrim(@Nullable String str, @Nullable String delimiter) {
        return StringUtils.delimitedListToStringArray(str, delimiter, " \t\n\n\f");
    }

    public static boolean simpleMatch(@Nullable String pattern, @Nullable String str) {
        return PatternMatchUtils.simpleMatch(pattern, str);
    }

    public static boolean simpleMatch(@Nullable String[] patterns, String str) {
        return PatternMatchUtils.simpleMatch(patterns, str);
    }


    public static String getUUID() {
        return getUUID(ThreadLocalRandom.current());
    }

    public static String getSafeUUID() {
        return getUUID(Holder.SECURE_RANDOM);
    }

    private static String getUUID(Random random) {
        long lsb = random.nextLong();
        long msb = random.nextLong();
        byte[] buf = new byte[32];
        int radix = 1 << 4;
        formatUnsignedLong(lsb, radix, buf, 20, 12);
        formatUnsignedLong(lsb >>> 48, radix, buf, 16, 4);
        formatUnsignedLong(msb, radix, buf, 12, 4);
        formatUnsignedLong(msb >>> 16, radix, buf, 8, 4);
        formatUnsignedLong(msb >>> 32, radix, buf, 0, 8);
        return new String(buf, Charsets.UTF_8);
    }

    public static String getNanoId() {
        return getNanoId(ThreadLocalRandom.current(), true);
    }

    public static String getNanoId62() {
        return getNanoId(ThreadLocalRandom.current(), false);
    }

    public static String getSafeNanoId() {
        return getNanoId(Holder.SECURE_RANDOM, true);
    }

    public static String getSafeNanoId62() {
        return getNanoId(Holder.SECURE_RANDOM, false);
    }

    private static String getNanoId(Random random, boolean radix64) {
        long lsb = random.nextLong();
        long msb = random.nextLong();
        byte[] buf = new byte[21];
        int radix = radix64 ? 64 : 62;
        formatUnsignedLong(lsb, radix, buf, 14, 7);
        formatUnsignedLong(msb, radix, buf, 10, 4);
        formatUnsignedLong(msb >>> 16, radix, buf, 6, 4);
        formatUnsignedLong(msb >>> 32, radix, buf, 0, 6);
        return new String(buf, Charsets.UTF_8);
    }

    private static void formatUnsignedLong(long val, int radix, byte[] buf, int offset, int len) {
        int charPos = offset + len;
        int mask = radix - 1;
        do {
            buf[--charPos] = NumberUtil.DIGITS[((int) val) & mask];
            val >>>= 4;
        } while (charPos > offset);
    }


    public static String escapeHtml(String html) {
        return HtmlUtils.htmlEscape(html);
    }


    @Nullable
    public static String cleanText(@Nullable String txt) {
        if (txt == null) {
            return null;
        }
        return SPECIAL_CHARS_REGEX.matcher(txt).replaceAll(SymbolConstants.EMPTY);
    }


    @Nullable
    public static String cleanIdentifier(@Nullable String param) {
        if (param == null) {
            return null;
        }
        StringBuilder paramBuilder = new StringBuilder();
        for (int i = 0; i < param.length(); i++) {
            char c = param.charAt(i);
            if (Character.isJavaIdentifierPart(c)) {
                paramBuilder.append(c);
            }
        }
        return paramBuilder.toString();
    }

    public static String random(int count) {
        return StringUtil.random(count, RandomType.ALL);
    }

    public static String random(int count, RandomType randomType) {
        if (count == 0) {
            return SymbolConstants.EMPTY;
        }
        Assert.isTrue(count > 0, "Requested random string length " + count + " is less than 0.");
        final Random random = Holder.SECURE_RANDOM;
        char[] buffer = new char[count];
        for (int i = 0; i < count; i++) {
            String factor = randomType.getFactor();
            buffer[i] = factor.charAt(random.nextInt(factor.length()));
        }
        return new String(buffer);
    }

    public static String repeat(final char ch, final int repeat) {
        if (repeat <= 0) {
            return SymbolConstants.EMPTY;
        }
        final char[] buf = new char[repeat];
        Arrays.fill(buf, ch);
        return new String(buf);
    }


    @Nullable
    public static String left(@Nullable final String str, final int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return SymbolConstants.EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    @Nullable
    public static String right(@Nullable final String str, final int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return SymbolConstants.EMPTY;
        }
        int length = str.length();
        if (length <= len) {
            return str;
        }
        return str.substring(length - len);
    }


    @Nullable
    public static String rightPad(@Nullable final String str, final int size) {
        return rightPad(str, size, CharPool.SPACE);
    }


    @Nullable
    public static String rightPad(@Nullable final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            // returns original String when possible
            return str;
        }
        if (pads > PAD_LIMIT) {
            return rightPad(str, size, String.valueOf(padChar));
        }
        return str.concat(repeat(padChar, pads));
    }


    @Nullable
    public static String rightPad(@Nullable final String str, final int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (!StringUtils.hasLength(padStr)) {
            padStr = SymbolConstants.SPACE;
        }
        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) {
            // returns original String when possible
            return str;
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return rightPad(str, size, padStr.charAt(0));
        }
        if (pads == padLen) {
            return str.concat(padStr);
        } else if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        } else {
            final char[] padding = new char[pads];
            final char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return str.concat(new String(padding));
        }
    }


    @Nullable
    public static String leftPad(@Nullable final String str, final int size) {
        return leftPad(str, size, CharPool.SPACE);
    }


    @Nullable
    public static String leftPad(@Nullable final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            // returns original String when possible
            return str;
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return repeat(padChar, pads).concat(str);
    }


    @Nullable
    public static String leftPad(@Nullable final String str, final int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (!StringUtils.hasLength(padStr)) {
            padStr = SymbolConstants.SPACE;
        }
        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) {
            // returns original String when possible
            return str;
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }
        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            final char[] padding = new char[pads];
            final char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }


    @Nullable
    public static String mid(@Nullable final String str, int pos, final int len) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (len < 0 || pos > length) {
            return SymbolConstants.EMPTY;
        }
        if (pos < 0) {
            pos = 0;
        }
        if (length <= pos + len) {
            return str.substring(pos);
        }
        return str.substring(pos, pos + len);
    }


    public static boolean isHttpUrl(String text) {
        return text.startsWith("http://") || text.startsWith("https://");
    }


    public static String underlineToHump(String para) {
        StringBuilder result = new StringBuilder();
        String[] a = para.split("_");
        for (String s : a) {
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    public static String lowerFirst(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= CharPool.U_A && firstChar <= CharPool.U_Z) {
            char[] arr = str.toCharArray();
            arr[0] += (CharPool.L_A - CharPool.U_A);
            return new String(arr);
        }
        return str;
    }

    public static String upperFirst(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= CharPool.L_A && firstChar <= CharPool.L_Z) {
            char[] arr = str.toCharArray();
            arr[0] -= (CharPool.L_A - CharPool.U_A);
            return new String(arr);
        }
        return str;
    }

    public static String humpToUnderline(String para) {
        para = lowerFirst(para);
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toLowerCase();
    }


    public static String lineToHump(String para) {
        StringBuilder result = new StringBuilder();
        String[] a = para.split("-");
        for (String s : a) {
            if (result.isEmpty()) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }


    public static String humpToLine(String para) {
        para = lowerFirst(para);
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "-");
                temp += 1;
            }
        }
        return sb.toString().toLowerCase();
    }

    public static StringBuilder appendBuilder(StringBuilder sb, CharSequence... strs) {
        for (CharSequence str : strs) {
            sb.append(str);
        }
        return sb;
    }

    public static String removeSuffix(CharSequence str, CharSequence suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return SymbolConstants.EMPTY;
        }

        final String str2 = str.toString();
        if (str2.endsWith(suffix.toString())) {
            return subPre(str2, str2.length() - suffix.length());
        }
        return str2;
    }

    public static String subPre(CharSequence string, int toIndex) {
        return sub(string, 0, toIndex);
    }

    public static String sub(CharSequence str, int fromIndex, int toIndex) {
        if (isEmpty(str)) {
            return SymbolConstants.EMPTY;
        }
        int len = str.length();

        if (fromIndex < 0) {
            fromIndex = len + fromIndex;
            if (fromIndex < 0) {
                fromIndex = 0;
            }
        } else if (fromIndex > len) {
            fromIndex = len;
        }

        if (toIndex < 0) {
            toIndex = len + toIndex;
            if (toIndex < 0) {
                toIndex = len;
            }
        } else if (toIndex > len) {
            toIndex = len;
        }

        if (toIndex < fromIndex) {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }

        if (fromIndex == toIndex) {
            return SymbolConstants.EMPTY;
        }

        return str.toString().substring(fromIndex, toIndex);
    }

    public static String subBefore(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        if (isEmpty(string) || separator == null) {
            return null == string ? null : string.toString();
        }

        final String str = string.toString();
        final String sep = separator.toString();
        if (sep.isEmpty()) {
            return SymbolConstants.EMPTY;
        }
        final int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
    }

    public static String subAfter(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        if (isEmpty(string)) {
            return null == string ? null : string.toString();
        }
        if (separator == null) {
            return SymbolConstants.EMPTY;
        }
        final String str = string.toString();
        final String sep = separator.toString();
        final int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
        if (pos == INDEX_NOT_FOUND) {
            return SymbolConstants.EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    public static String subBetween(CharSequence str, CharSequence before, CharSequence after) {
        if (str == null || before == null || after == null) {
            return null;
        }

        final String str2 = str.toString();
        final String before2 = before.toString();
        final String after2 = after.toString();

        final int start = str2.indexOf(before2);
        if (start != INDEX_NOT_FOUND) {
            final int end = str2.indexOf(after2, start + before2.length());
            if (end != INDEX_NOT_FOUND) {
                return str2.substring(start + before2.length(), end);
            }
        }
        return null;
    }

    private static final String EMAIL_PATTERN = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";

    public static String getEmailFromStr(String context) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(context);
        while (matcher.find()) {
            String email = matcher.group();
            return email;
        }
        return null;
    }

    public static String subBetween(CharSequence str, CharSequence beforeAndAfter) {
        return subBetween(str, beforeAndAfter, beforeAndAfter);
    }

}
