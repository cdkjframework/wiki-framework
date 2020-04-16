package com.cdkjframework.util.date;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.number.ConvertUtils;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.date
 * @ClassName: LocalDateUtils
 * @Description: JDK 8 时间
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class LocalDateUtils {

    /**
     * 日期时间格式
     */
    public static final String DATE = "yyyy-MM-dd";
    public static final String DATE_NOT_LINE = "yyyyMMdd";
    public static final String DATE_NOT_LINE_SHORT_YEAR = "yyMMdd";
    public static final String DATE_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DATE_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_HH_MM_SS_Z1 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_HH_MM_SS_Z2 = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DATE_HH_MM_SS_Z3 = "yyyy-MM-dd'T'HH:mm:ssz";
    public static final String DATE_HH_MM_SS_Z4 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_HH_MM_SS_A = "MM/dd/yyyy HH:mm:ss a";
    public static final String DATE_HHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_YEAR = "yyyy";
    public static final String DATE_MONTH = "yyyy-MM";
    public static final String DATE_DAY = "yyyy-MM-dd";

    /**
     * 获取到当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return 返回结果
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 格式化 2019-11-19T15:16:17
     *
     * @param dateTime 时间
     * @return
     */
    public static LocalDateTime dateTimeParse(String dateTime) {
        return LocalDateTime.parse(dateTime);
    }

    /**
     * 返回结果
     *
     * @param dateTime 时间
     * @param pattern  指定格式
     * @return
     */
    public static LocalDateTime dateTimeParse(String dateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    /**
     * 获取当前日期
     *
     * @return 返回结果
     */
    public static LocalDate getCurrentDate() {
        return getCurrentDateTime().toLocalDate();
    }

    /**
     * 日期格式 2019-11-19
     *
     * @param date 指定日期
     * @return 返回日期
     */
    public static LocalDate dateParse(String date) {
        return LocalDate.parse(date);
    }

    /**
     * 日期格式
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 返回结果
     */
    public static LocalDate dateParse(String year, String month, String day) {
        return dateParse(ConvertUtils.convertInt(year), ConvertUtils.convertInt(month), ConvertUtils.convertInt(day));
    }

    /**
     * 日期格式
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 返回结果
     */
    public static LocalDate dateParse(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    /**
     * 将指定时间转换为时间戳
     *
     * @param localDate 日期
     * @return 返回结果
     */
    public static long localDateToTimestamp(LocalDate localDate) {
        if (localDate == null) {
            return 0;
        }
        return localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }

    /**
     * 将指定时间转换为时间戳
     *
     * @param localDateTime 日期时间
     * @return 返回结果
     */
    public static long localDateTimeToTimestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return 0;
        }
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * 获取当前时间
     *
     * @return 返回结果
     */
    public static LocalTime getCurrentTime() {
        return getCurrentDateTime().toLocalTime();
    }

    /**
     * 日期格式
     *
     * @param time 时间
     * @return 返回结果
     */
    public static LocalTime timeParse(String time) {
        return LocalTime.parse(time);
    }

    /**
     * 时间格式
     *
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return 返回结果
     */
    public static LocalTime timeParse(String hour, String minute, String second) {
        return timeParse(ConvertUtils.convertInt(hour), ConvertUtils.convertInt(minute), ConvertUtils.convertInt(second));
    }

    /**
     * 时间格式
     *
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return 返回结果
     */
    public static LocalTime timeParse(int hour, int minute, int second) {
        return LocalTime.of(hour, minute, second);
    }

    /**
     * 格式化当前时间为固定格式
     *
     * @return 返回结果字符串
     */
    public static String dateTimeCurrentFormatter() {
        return dateTimeCurrentFormatter(DATE_HH_MM_SS);
    }

    /**
     * 格式化当前时间为指定格式
     *
     * @return 返回结果字符串
     */
    public static String dateTimeCurrentFormatter(String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(getCurrentDateTime());
    }

    /**
     * 格式化指定时间为固定格式
     *
     * @return 返回结果字符串
     */
    public static String dateTimeFormatter(LocalDateTime dateTime) {
        return dateTimeFormatter(dateTime, DATE_HH_MM_SS);
    }

    /**
     * 格式化指定时间为指定格式
     *
     * @return 返回结果字符串
     */
    public static String dateTimeFormatter(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(dateTime);
    }

    /**
     * 指定时间与当前时间相减
     *
     * @param dateTime 指定时间
     * @return 年
     */
    public static long subtractYear(LocalDateTime dateTime) {
        long day = subtractDay(dateTime);
        return day / 365;
    }

    /**
     * 指定时间与当前时间相减
     *
     * @param dateTime 指定时间
     * @return 天
     */
    public static long subtractDay(LocalDateTime dateTime) {
        Duration duration = subtract(dateTime);
        return duration.toDays();
    }

    /**
     * 指定时间与当前时间相减
     *
     * @param dateTime 指定时间
     * @return 小时
     */
    public static long subtractHour(LocalDateTime dateTime) {
        Duration duration = subtract(dateTime);
        return duration.toHours();
    }

    /**
     * 指定时间与当前时间相减
     *
     * @param dateTime 指定时间
     * @return 分钟
     */
    public static long subtractMinute(LocalDateTime dateTime) {
        Duration duration = subtract(dateTime);
        return duration.toMinutes();
    }

    /**
     * 指定时间与当前时间相减
     *
     * @param dateTime 指定时间
     * @return 返回结果
     */
    public static Duration subtract(LocalDateTime dateTime) {
        return subtract(getCurrentDateTime(), dateTime);
    }

    /**
     * 指定时间与当前时间相减
     *
     * @param startDateTime 指定时间
     * @param endDateTime   指定时间
     * @return 返回结果
     */
    public static Duration subtract(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime);
    }

    /**
     * 指定时间与当前时间相减
     *
     * @param startDateTime 指定时间
     * @param endDateTime   指定时间
     * @return 年
     */
    public static long subtractYear(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        long day = subtractDay(startDateTime, endDateTime);
        return day / 365;

    }

    /**
     * 指定时间与当前时间相减
     *
     * @param startDateTime 指定时间
     * @param endDateTime   指定时间
     * @return 天
     */
    public static long subtractDay(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = subtract(startDateTime, endDateTime);
        return duration.toDays();
    }

    /**
     * 指定时间与当前时间相减
     *
     * @param startDateTime 指定时间
     * @param endDateTime   指定时间
     * @return 小时
     */
    public static long subtractHour(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = subtract(startDateTime, endDateTime);
        return duration.toHours();
    }

    /**
     * 指定时间与当前时间相减
     *
     * @param startDateTime 指定时间
     * @param endDateTime   指定时间
     * @return 分钟
     */
    public static long subtractMinute(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = subtract(startDateTime, endDateTime);
        return duration.toMinutes();
    }

    /**
     * 指定月份天数
     *
     * @param month 月份
     * @return 返回结果
     */
    public static long designatedToDay(int month) {
        // 获取当前时间
        LocalDate localDate = getCurrentDate();
        localDate = localDate.withMonth(month).with(TemporalAdjusters.lastDayOfMonth());
        return localDate.getDayOfMonth();
    }

    /**
     * 指定年月份天数
     *
     * @param year  年份
     * @param month 月份
     * @return 返回结果
     */
    public static long designatedToDay(int year, int month) {
        // 获取当前时间
        LocalDate localDate = getCurrentDate();
        localDate = localDate.withYear(year).withMonth(month).with(TemporalAdjusters.lastDayOfMonth());
        return localDate.getDayOfMonth();
    }

    /**
     * 指定月份周
     *
     * @param month 月份
     * @return 返回结果
     */
    public static long designatedToWeek(int month) {
        // 获取当前时间
        LocalDate localDate = getCurrentDate();
        localDate = localDate.withMonth(month);
        return localDate.getDayOfWeek().getValue();
    }

    /**
     * 指定年月份周
     *
     * @param year  年份
     * @param month 月份
     * @return 返回结果
     */
    public static long designatedToWeek(int year, int month) {
        // 获取当前时间
        LocalDate localDate = getCurrentDate();
        localDate = localDate.withYear(year).withMonth(month);
        return localDate.getDayOfWeek().getValue();
    }

    /**
     * 时间比较
     *
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @return 返回结果
     */
    public static boolean greater(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return startDateTime.isBefore(endDateTime);
    }

    /**
     * 时间比较 2
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 返回结果
     */
    public static boolean greater(LocalDate startDate, LocalDate endDate) {
        return startDate.isBefore(endDate);
    }

    /**
     * 时间比较 3
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回结果
     */
    public static boolean greater(LocalTime startTime, LocalTime endTime) {
        return startTime.isBefore(endTime);
    }

    /**
     * 时间戳转换为时间
     *
     * @param timestamp 时间戳
     * @return 返回时间
     */
    public static LocalDateTime timestampToLocalDateTime(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, IntegerConsts.ZERO, ZoneOffset.ofHours(IntegerConsts.EIGHT));
    }

    /**
     * 时间戳转换为时间字符
     *
     * @param timestamp 时间戳
     * @return 返回时间字符
     */
    public static String timestampToDateTimeString(long timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(timestamp, IntegerConsts.ZERO, ZoneOffset.ofHours(IntegerConsts.EIGHT));
        return dateTimeFormatter(localDateTime);
    }

    /**
     * 时间戳转换为时间字符
     *
     * @param timestamp 时间戳
     * @param pattern   时间格式
     * @return 返回时间字符
     */
    public static String timestampToDateTimeString(long timestamp, String pattern) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(timestamp, IntegerConsts.ZERO, ZoneOffset.ofHours(IntegerConsts.EIGHT));
        return dateTimeFormatter(localDateTime, pattern);
    }

    /**
     * 时间戳转换为日期
     *
     * @param timestamp 时间戳
     * @return 返回时间
     */
    public static LocalDate timestampToLocalDate(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, IntegerConsts.ZERO, ZoneOffset.ofHours(IntegerConsts.EIGHT)).toLocalDate();
    }

    /**
     * 时间戳转换为日期字符
     *
     * @param timestamp 时间戳
     * @return 返回时间字符
     */
    public static String timestampToDateString(long timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(timestamp, IntegerConsts.ZERO, ZoneOffset.ofHours(IntegerConsts.EIGHT));
        return dateTimeFormatter(localDateTime, DATE);
    }

    /**
     * 时间戳转换为日期字符
     *
     * @param timestamp 时间戳
     * @param pattern   时间格式
     * @return 返回日期字符
     */
    public static String timestampToDateString(long timestamp, String pattern) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(timestamp, IntegerConsts.ZERO, ZoneOffset.ofHours(IntegerConsts.EIGHT));
        return dateTimeFormatter(localDateTime, pattern);
    }
}
