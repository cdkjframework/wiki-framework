package com.cdkjframework.license.util;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.license.service
 * @ClassName: DateUtils
 * @Description: 日期工具类
 * @Author: xiaLin
 * @Version: 1.0
 */
public class DateUtils {

  /**
   * 时间格式
   */
  private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
  /**
   * 时间格式
   */
  private static DateTimeFormatter dtF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  /**
   * 日历
   */
  private static Calendar calendar = Calendar.getInstance();

  /**
   * 获取当前时间
   *
   * @return 时间
   */
  public static synchronized String getCurrentDateForFile() {
    long currentTimeMillis = System.currentTimeMillis();
    Date date = new Date(currentTimeMillis);
    return sDateFormat.format(date);
  }

  /**
   * 时间转换
   *
   * @param time 时间
   * @return 时间戳
   * @throws GlobalException 异常
   */
  public static Long getTime(String time) throws GlobalException {
    if (CommonUtils.isEmpty(time)) {
      throw new GlobalException("时间[" + time + "]格式不合法");
    } else if (time.length() < IntegerConsts.ELEVEN) {
      dtF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate parse = LocalDate.parse(time, dtF);
      return LocalDate.from(parse).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    } else {
      LocalDateTime parse = LocalDateTime.parse(time, dtF);
      return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
  }

  /**
   * 获取时间
   *
   * @return 时间
   */
  public static synchronized String getDate() {
    Date date = new Date();
    sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sDateFormat.format(date);
  }

  /**
   * 时间戳转换成日期
   *
   * @param time 时间戳
   * @return 时间
   */
  public static synchronized String date2Str(Long time) {
    Date date = new Date(time);
    sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sDateFormat.format(date);
  }

  /**
   * 时间转换成字符串
   *
   * @param time 时间
   * @return 时间字符串
   */
  public static synchronized String date2Str(Date time) {
    sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sDateFormat.format(time);
  }

  /**
   * 字符串转换成时间
   *
   * @param time 时间字符串
   * @return 时间
   */
  public static synchronized Date str2Date(String time) throws GlobalException {
    sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    try {
      return sDateFormat.parse(time);
    } catch (Exception var2) {
      throw new GlobalException("字符串[" + time + "]转换日期格式异常");
    }
  }

  /**
   * 时间转换成字符串
   *
   * @param date  时间
   * @param mount 添加的月数
   * @return 时间字符串
   */
  public static Date addYear(Date date, int mount) {
    calendar.setTime(date);
    calendar.add(1, mount);
    return calendar.getTime();
  }

  /**
   * 时间转换成字符串
   *
   * @param time  时间
   * @param mount 添加的月数
   * @return 时间字符串
   */
  public static Date addYear(Long time, int mount) {
    Date date = new Date(time);
    calendar.setTime(date);
    calendar.add(1, mount);
    return calendar.getTime();
  }

  /**
   * 添加月
   *
   * @param date  日期
   * @param mount 月数
   * @return 日期
   */
  public static Date addMonth(Date date, int mount) {
    calendar.setTime(date);
    calendar.add(2, mount);
    return calendar.getTime();
  }

  /**
   * 获取指定时间增加指定月数后的时间
   *
   * @param time  时间
   * @param mount 月数
   * @return 时间
   */
  public static Date addMonth(Long time, int mount) {
    Date date = new Date(time);
    calendar.setTime(date);
    calendar.add(1, mount);
    return calendar.getTime();
  }

  /**
   * 添加月
   *
   * @param date  日期
   * @param mount 月数
   * @return 日期
   */
  public static Date addDay(Date date, int mount) {
    calendar.setTime(date);
    calendar.add(5, mount);
    return calendar.getTime();
  }

  /**
   * 添加天
   *
   * @param time  时间戳
   * @param mount 天数
   * @return 时间
   */
  public static Date addDay(Long time, int mount) {
    Date date = new Date(time);
    calendar.setTime(date);
    calendar.add(5, mount);
    return calendar.getTime();
  }
}
