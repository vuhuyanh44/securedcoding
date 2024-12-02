package com.lgcns.hrm.cv.common.utils;

import com.lgcns.hrm.cv.common.constants.SymbolConstants;
import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@UtilityClass
public class DateUtil {

    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "dd-MM-yyyy";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_DATE_ORIGINAL = "yyyy-MM-dd";

    public static final ConcurrentDateFormat DATETIME_FORMAT = ConcurrentDateFormat.of(PATTERN_DATETIME);
    public static final ConcurrentDateFormat DATE_FORMAT = ConcurrentDateFormat.of(PATTERN_DATE);
    public static final ConcurrentDateFormat TIME_FORMAT = ConcurrentDateFormat.of(PATTERN_TIME);

    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DateUtil.PATTERN_DATETIME);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DateUtil.PATTERN_DATE);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(DateUtil.PATTERN_TIME);


    public static Date now() {
        return new Date();
    }


    public static Date plusYears(Date date, int yearsToAdd) {
        return DateUtil.set(date, Calendar.YEAR, yearsToAdd);
    }


    public static Date plusMonths(Date date, int monthsToAdd) {
        return DateUtil.set(date, Calendar.MONTH, monthsToAdd);
    }


    public static Date plusWeeks(Date date, int weeksToAdd) {
        return DateUtil.plus(date, Period.ofWeeks(weeksToAdd));
    }

    public static Date plusDays(Date date, long daysToAdd) {
        return DateUtil.plus(date, Duration.ofDays(daysToAdd));
    }

    public static Date plusHours(Date date, long hoursToAdd) {
        return DateUtil.plus(date, Duration.ofHours(hoursToAdd));
    }


    public static Date plusMinutes(Date date, long minutesToAdd) {
        return DateUtil.plus(date, Duration.ofMinutes(minutesToAdd));
    }

    public static Date plusSeconds(Date date, long secondsToAdd) {
        return DateUtil.plus(date, Duration.ofSeconds(secondsToAdd));
    }


    public static Date plusMillis(Date date, long millisToAdd) {
        return DateUtil.plus(date, Duration.ofMillis(millisToAdd));
    }

    public static Date plusNanos(Date date, long nanosToAdd) {
        return DateUtil.plus(date, Duration.ofNanos(nanosToAdd));
    }


    public static Date plus(Date date, TemporalAmount amount) {
        Instant instant = date.toInstant();
        return Date.from(instant.plus(amount));
    }


    public static Date minusYears(Date date, int years) {
        return DateUtil.set(date, Calendar.YEAR, -years);
    }

    public static Date minusMonths(Date date, int months) {
        return DateUtil.set(date, Calendar.MONTH, -months);
    }


    public static Date minusWeeks(Date date, int weeks) {
        return DateUtil.minus(date, Period.ofWeeks(weeks));
    }


    public static Date minusDays(Date date, long days) {
        return DateUtil.minus(date, Duration.ofDays(days));
    }


    public static Date minusHours(Date date, long hours) {
        return DateUtil.minus(date, Duration.ofHours(hours));
    }


    public static Date minusMinutes(Date date, long minutes) {
        return DateUtil.minus(date, Duration.ofMinutes(minutes));
    }

    public static Date minusSeconds(Date date, long seconds) {
        return DateUtil.minus(date, Duration.ofSeconds(seconds));
    }


    public static Date minusMillis(Date date, long millis) {
        return DateUtil.minus(date, Duration.ofMillis(millis));
    }


    public static Date minusNanos(Date date, long nanos) {
        return DateUtil.minus(date, Duration.ofNanos(nanos));
    }

    public static Date minus(Date date, TemporalAmount amount) {
        Instant instant = date.toInstant();
        return Date.from(instant.minus(amount));
    }


    private static Date set(Date date, int calendarField, int amount) {
        Assert.notNull(date, "The date must not be null");
        Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }


    public static String formatDateTime(Date date) {
        return DATETIME_FORMAT.format(date);
    }


    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }


    public static String formatTime(Date date) {
        return TIME_FORMAT.format(date);
    }


    public static String format(Date date, String pattern) {
        return ConcurrentDateFormat.of(pattern).format(date);
    }


    public static String formatDateTime(TemporalAccessor temporal) {
        return DATETIME_FORMATTER.format(temporal);
    }

    public static String formatDate(TemporalAccessor temporal) {
        return DATE_FORMATTER.format(temporal);
    }

    public static String formatTime(TemporalAccessor temporal) {
        return TIME_FORMATTER.format(temporal);
    }


    public static String format(TemporalAccessor temporal, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(temporal);
    }

    public static Date parse(String dateStr, String pattern) {
        ConcurrentDateFormat format = ConcurrentDateFormat.of(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static Date parse(String dateStr, ConcurrentDateFormat format) {
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> T parse(String dateStr, String pattern, TemporalQuery<T> query) {
        return DateTimeFormatter.ofPattern(pattern).parse(dateStr, query);
    }


    public static Instant toInstant(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    public static LocalDateTime toDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }


    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(DateUtil.toInstant(dateTime));
    }

    public static Date toDate(final LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Calendar toCalendar(final LocalDateTime localDateTime) {
        return GregorianCalendar.from(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));
    }


    public static long toMilliseconds(final LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static long toMilliseconds(LocalDate localDate) {
        return toMilliseconds(localDate.atStartOfDay());
    }

    public static LocalDateTime fromCalendar(final Calendar calendar) {
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }


    public static LocalDateTime fromInstant(final Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }


    public static LocalDateTime fromDate(final Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }


    public static LocalDateTime fromMilliseconds(final long milliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
    }


    public static Duration between(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive);
    }

    public static Period between(LocalDate startDate, LocalDate endDate) {
        return Period.between(startDate, endDate);
    }


    public static Duration between(Date startDate, Date endDate) {
        return Duration.between(startDate.toInstant(), endDate.toInstant());
    }


    public static String secondToTime(Long second) {
        if (second == null || second == 0L) {
            return SymbolConstants.EMPTY;
        }

        long days = second / 86400;

        second = second % 86400;

        long hours = second / 3600;

        second = second % 3600;

        long minutes = second / 60;

        second = second % 60;
        if (days > 0) {
            return StringUtil.format("{} days {} hours {} minutes {} seconds", days, hours, minutes, second);
        } else {
            return StringUtil.format("{}hours{}minutes{}seconds", hours, minutes, second);
        }
    }

    public static String today() {
        return format(new Date(), "yyyyMMdd");
    }

}
