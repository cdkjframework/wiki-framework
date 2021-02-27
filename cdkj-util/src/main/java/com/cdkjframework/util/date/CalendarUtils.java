package com.cdkjframework.util.date;

import com.cdkjframework.constant.IntegerConsts;

import java.util.Calendar;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.date
 * @ClassName: CalendarUtils
 * @Description: 阴阳历转换工具
 * @Author: xiaLin
 * @Version: 1.0
 */
public class CalendarUtils {

    /**
     * 年
     */
    private int year = 0;

    /**
     * 月
     */
    private int month = 0;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * 农历年份
     */
    private int chineseYear;

    /**
     * 农历月份
     */
    private int chineseMonth;

    /**
     * 农历日
     */
    private int chineseDay;

    /**
     * 判断闰月
     */
    private boolean leap;

    // 1-4: 表示当年有无闰年，有的话，为闰月的月份，没有的话，为0。
    // 5-16：为除了闰月外的正常月份是大月还是小月，1为30天，0为29天。
    // 注意：从1月到12月对应的是第16位到第5位。
    // 17-20： 表示闰月是大月还是小月，仅当存在闰月的情况下有意义。
    // 举例：
    // 1980年的数据是： 0x095b0
    // 二进制：0000 1001 0101 1011 0000
    // 表示1980年没有闰月，从1月到12月的天数依次为：30、29、29、30 、29、30、29、30、 30、29、30、30。
    // 1982年的数据是：0x0a974
    // 0000 1010 0 1001 0111 0100
    // 表示1982年的4月为闰月，即有第二个4月，且是闰小月。
    // 从1月到13月的天数依次为：30、29、30、29、 29(闰月)、 30、29、29、30、 29、30、30、30。
    long[] lunarInfo = {0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
            0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0,
            0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0, 0x0ea50, 0x06e95,
            0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0,
            0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8,
            0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
            0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0, 0x049b0,
            0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3,
            0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0,
            0x0abb7, 0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176,
            0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
            0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0,
            0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};
    String[] Gan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    String[] Zhi = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    String[] chineseMonthValue = {"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"};
    String[] chineseDayValue = {"初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十", "十一", "十二", "十三", "十四", "十五",
            "十六", "十七", "十八", "十九", "二十", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十",};
    String[] solarTerm = {"小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋",
            "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};

    // "小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨",
    // "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑",
    // "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"
    // 定义数组，存储的是20世纪和21世纪的节气C值
    double[] SolarTerms_C_20thcentury = {6.11, 20.84, 4.6295, 19.4599, 6.3826, 21.4155, 5.59, 20.888, 6.318, 21.86,
            6.5, 22.2, 7.928, 23.65, 8.35, 23.95, 8.44, 23.822, 9.098, 24.218, 8.218, 23.08, 7.9, 22.6};
    double[] SolarTerms_C_21stcentury = {5.4055, 20.12, 3.87, 18.73, 5.63, 20.646, 4.81, 20.1, 5.52, 21.04, 5.678,
            21.37, 7.108, 22.83, 7.5, 23.13, 7.646, 23.042, 8.318, 23.438, 7.438, 22.36, 7.18, 21.94};

    /**
     * 传回农历 y年的总天数
     *
     * @param y 年
     * @return 返回总天数
     */
    public int yearDays(int y) {
        int i, totalDay = 348;
        final int hexStart = 0x8000;
        final int hexEnd = 0x8;
        for (i = hexStart; i > hexEnd; i >>= IntegerConsts.ONE) {
            if ((lunarInfo[y - 1900] & i) != IntegerConsts.ZERO) {
                totalDay += IntegerConsts.ONE;
            }
        }
        return (totalDay + leapDays(y));
    }

    /**
     * 传回农历 y年闰月的天数
     *
     * @param y
     * @return
     */
    public int leapDays(int y) {
        final int hex = 0x10000;
        final int year = 1900;
        if (leapMonth(y) != IntegerConsts.ZERO) {
            if ((lunarInfo[y - year] & hex) != IntegerConsts.ZERO) {
                return IntegerConsts.THIRTY;
            } else {
                return 29;
            }
        } else {
            return IntegerConsts.ZERO;
        }
    }

    /**
     * 传回农历 y年闰哪个月 1-12 , 没闰传回 0
     *
     * @param y 年
     * @return 总天数
     */
    public int leapMonth(int y) {
        final int year = 1900;
        return (int) (lunarInfo[y - year] & 0xf);
    }

    /**
     * 传回农历 y年m月的总天数
     *
     * @param y 年
     * @param m 月
     * @return 总天数
     */
    public int monthDays(int y, int m) {
        final int hex = 0x10000;
        final int year = 1900;
        if ((lunarInfo[y - year] & (hex >> m)) == IntegerConsts.ZERO) {
            return 29;
        } else {
            return IntegerConsts.THIRTY;
        }
    }

    /**
     * 传回农历年
     *
     * @return
     */
    public String getChinaYearString() {
        final int year = 1900;
        int num = chineseYear - year + 36;
        return (Gan[num % IntegerConsts.TEN] + Zhi[num % 12]);
    }

    /**
     * 传回农历月
     *
     * @return
     */
    public String getChinaMonthString() {
        if (leap == true) {
            return "闰" + chineseMonthValue[chineseMonth - IntegerConsts.ONE];
        } else {
            return chineseMonthValue[chineseMonth - IntegerConsts.ONE] + "月";
        }
    }

    /**
     * 传回农历日
     *
     * @return
     */
    public String getChinaDayString() {
        return chineseDayValue[chineseDay];
    }

    /**
     * 计算当年当月day日所对应的农历：chineseyear,chinesemonth,chineseday
     *
     * @param day 天
     */
    public void getFirstDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        int leapMonth = 0;
        Calendar basedate = Calendar.getInstance();
        basedate.set(1900, 1 - 1, 31);
        // 求出和1900年1月31日相差的天数
        int offset = (int) ((cal.getTimeInMillis() - basedate.getTimeInMillis()) / (1000 * 60 * 60 * 24));
        // 用offset减去每农历年的天数
        // 计算当天是农历第几天
        // i最终结果是农历的年份
        // offset是当年的第几天
        int lyear, daysOfYear = 0;
        for (lyear = 1900; lyear < 2050 && offset > 0; lyear++) {
            daysOfYear = yearDays(lyear);
            offset = offset - daysOfYear;
        }
        if (offset < 0) {
            offset = offset + daysOfYear;
            lyear--;
        }
        // 农历年份
        this.chineseYear = lyear;
        // 闰哪个月,1-12
        leapMonth = leapMonth(lyear);
        leap = false;
        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int lmonth, daysOfMonth = 0;
        for (lmonth = 1; lmonth < 13 && offset > 0; lmonth++) {
            // 闰月
            if (leapMonth > 0 && lmonth == (leapMonth + 1) && !leap) {
                --lmonth;
                leap = true;
                daysOfMonth = leapDays(lyear);
            } else {
                daysOfMonth = monthDays(lyear, lmonth);
            }
            offset = offset - daysOfMonth;
            // 解除闰月
            if (leap && lmonth == (leapMonth + 1)) {
                leap = false;
            }
        }
        // offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && lmonth == leapMonth + 1) {
            if (leap) {
                leap = false;
            } else {
                leap = true;
                --lmonth;
            }
        }
        // offset小于0时，也要校正
        if (offset < 0) {
            offset = offset + daysOfMonth;
            lmonth--;
        }
        this.chineseMonth = lmonth;
        this.chineseDay = offset;
    }

    /**
     * 返回日期数组的空数据个数，即1号所在行中有多少个空位置 1号不一定是在该月日历中的第一个位置
     *
     * @return 返回结果
     */
    public int getWeekDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - IntegerConsts.ONE, IntegerConsts.ONE);
        return calendar.get(Calendar.DAY_OF_WEEK) - IntegerConsts.ONE;
    }

    /**
     * 当月有多少天
     *
     * @return 返回总天
     */
    public int getDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int day = 0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            day = 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30;
        }
        if (month == 2) {
            if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
                day = 29;
            } else {
                day = 28;
            }
        }
        return day;
    }

    /**
     * 返回阳历日期数组
     *
     * @return
     */
    public String[] getCalendar() {
        int weekDay = getWeekDay();
        int day = getDay();
        // 空数据个数加当月天数，即为日期数组的长度
        String[] days = new String[weekDay + day];
        for (int i = 0; i < weekDay; i++) {
            days[i] = "";
        }
        for (int i = weekDay, n = 1; i < weekDay + day; i++) {
            days[i] = String.valueOf(n);
            n++;
        }
        return days;
    }

    /**
     * 返回农历日期数组
     *
     * @return
     */
    public String[] getChineseCalendar() {
        getFirstDay(IntegerConsts.ONE);
        int weekDay = getWeekDay();
        int day = getDay();
        String[] days = new String[weekDay + day];
        for (int i = 0; i < weekDay; i++) {
            days[i] = "";
        }
        System.arraycopy(chineseDayValue, chineseDay, days, weekDay, monthDays(chineseYear, chineseMonth) - chineseDay);
        System.arraycopy(chineseDayValue, 0, days, monthDays(chineseYear, chineseMonth) - chineseDay + weekDay,
                day - (monthDays(chineseYear, chineseMonth) - chineseDay));

        return days;
    }

    /**
     * 返回包含节日的农历日期数组
     *
     * @return
     */
    public String[] getChineseCalendarFestival() {
        String[] days = getChineseCalendar();
        int weekDay = getWeekDay();
        for (int i = 0; i < days.length; i++) {
            if ("初一".equals(days[i])) {
                getFirstDay(i);
                days[i] = getChinaMonthString();
            }
            if ("正月".equals(days[i])) {
                days[i] = "春节";
            }
            if ("正月".equals(getChinaMonthString()) && "十五".equals(days[i])) {
                days[i] = "元宵";
            }
            if ("五月".equals(getChinaMonthString()) && "初五".equals(days[i])) {
                days[i] = "端午";
            }
            if ("七月".equals(getChinaMonthString()) && "初七".equals(days[i])) {
                days[i] = "七夕";
            }
            if ("七月".equals(getChinaMonthString()) && "十五".equals(days[i])) {
                days[i] = "中元";
            }
            if ("八月".equals(getChinaMonthString()) && "十五".equals(days[i])) {
                days[i] = "中秋";
            }
            if ("九月".equals(getChinaMonthString()) && "初九".equals(days[i])) {
                days[i] = "重阳";
            }
            if ("腊月".equals(getChinaMonthString()) && "初八".equals(days[i])) {
                days[i] = "腊八";
            }
            if ("腊月".equals(getChinaMonthString()) && "三十".equals(days[i])) {
                days[i] = "除夕";
            }
            if (getMonth() == 1 && i == (weekDay + 0)) {
                days[i] = "元旦";
            }
            if (getMonth() == 2 && i == (weekDay + 13)) {
                days[i] = "情人";
            }
            if (getMonth() == 3 && i == (weekDay + 7)) {
                days[i] = "妇女";
            }
            if (getMonth() == 3 && i == (weekDay + 11)) {
                days[i] = "植树";
            }
            if (getMonth() == 5 && i == (weekDay + 0)) {
                days[i] = "劳动";
            }
            if (getMonth() == 5 && i == (weekDay + 3)) {
                days[i] = "青年";
            }
            if (getMonth() == 6 && i == (weekDay + 0)) {
                days[i] = "儿童";
            }
            if (getMonth() == 7 && i == (weekDay + 0)) {
                days[i] = "建党";
            }
            if (getMonth() == 8 && i == (weekDay + 0)) {
                days[i] = "建军";
            }
            if (getMonth() == 9 && i == (weekDay + 9)) {
                days[i] = "教师";
            }
            if (getMonth() == 10 && i == (weekDay + 0)) {
                days[i] = "国庆";
            }
            if (getMonth() == 12 && i == (weekDay + 24)) {
                days[i] = "圣诞";
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 5 - 1);
        int sundays = 0;
        for (int i = 1; i <= getDay(); i++) {
            cal.set(Calendar.DATE, i);
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                sundays++;
                if (sundays == 2) {
                    if (getMonth() == 5) {
                        days[weekDay + i - 1] = "母亲";
                    }
                    break;
                }
            }
        }
        cal.set(Calendar.MONTH, 6 - 1);
        sundays = 0;
        for (int i = 1; i <= getDay(); i++) {
            cal.set(Calendar.DATE, i);
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                sundays++;
                if (sundays == 3) {
                    if (getMonth() == 6) {
                        days[weekDay + i - 1] = "父亲";
                    }
                    break;
                }
            }
        }
        return days;
    }

    /**
     * 返回包含节日和节气的农历日期数组
     *
     * @return 返回结果
     */
    public String[] getChineseCalendarFestivalSolarTerms() {
        String[] days = getChineseCalendarFestival();
        int weekDay = getWeekDay();
        // num =[Y*D+C]-L 寿星通用公式
        double D = 0.2422;
        double[] SolarTerms_C = null;
        // 20世纪
        if (year >= 1901 && year <= 2000) {
            SolarTerms_C = SolarTerms_C_20thcentury;
            // 21世纪
        } else if (year >= 2001 && year <= 2100) {
            SolarTerms_C = SolarTerms_C_21stcentury;
        }
        double C1 = SolarTerms_C[(month) * 2 - 1 - 1];
        double C2 = SolarTerms_C[month * 2 - 1];
        int Y = year % 100;
        int L = (Y) / 4;
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            // 注意：凡闰年3月1日前闰年数要减一，即：L=[(Y-1)/4],因为小寒、大寒、立春、雨水这两个节气都小于3月1日
            if (C1 == 5.4055 || C1 == 3.87) {
                L = (Y - 1) / 4;
            }
        }
        int num1 = (int) ((Y * D + C1) - L);
        int num2 = (int) ((Y * D + C2) - L);
        days[num1 + weekDay - 1] = solarTerm[(month) * 2 - 1 - 1];
        days[num2 + weekDay - 1] = solarTerm[(month) * 2 - 1];
        return days;
    }
}