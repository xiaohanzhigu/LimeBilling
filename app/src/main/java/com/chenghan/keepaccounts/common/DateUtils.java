package com.chenghan.keepaccounts.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private static SimpleDateFormat format = null;

    public static String toStr(long writeTime) {;
        format = new SimpleDateFormat(DateType.NYRSF);
        String format = DateUtils.format.format(writeTime);
        return format;
    }

    /**
     * @param source   如2022-12-16 07:14:42
     * @param dateType
     * @return
     * @throws ParseException
     */
    public static long parse(String source, String dateType) throws ParseException {
        format = new SimpleDateFormat(dateType);
        long time = format.parse(source).getTime();
        return time;
    }

    public static long getMinMonthDate(String d) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        format = new SimpleDateFormat(DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        String[] split = d.split("-");
        int year = Integer.valueOf(split[0]);
        int month = Integer.valueOf(split[1]);
        Date date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate localDate = yearMonth.atDay(1);
            LocalDateTime startOfDay = localDate.atStartOfDay();
            ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));
            date = Date.from(zonedDateTime.toInstant());
        }
        format = new SimpleDateFormat(DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        String beginTimeStr = format.format(date);
        long beginTime;
        try {
            beginTime = parse(beginTimeStr, DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        return beginTime;
    }

    public static long getMaxMonthDate(String d) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        format = new SimpleDateFormat(DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        String[] split = d.split("-");
        int year = Integer.valueOf(split[0]);
        int month = Integer.valueOf(split[1]);
        Date date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate endOfMonth = yearMonth.atEndOfMonth();
            LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
            date = Date.from(zonedDateTime.toInstant());
        }
        format = new SimpleDateFormat(DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        String beginTimeStr = format.format(date);
        long endTime;
        try {
            endTime = parse(beginTimeStr, DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return endTime;
    }

    /**
     * 获取今天开始的日期
     * @return
     */
    public static String getTodayTime() {
        Date now = new Date();//获取当前时间;
        format = new SimpleDateFormat(DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        String todayDate = format.format(now);
        return todayDate;
    }


    public static String getTodayTime(String dateType) {
        Date now = new Date();//获取当前时间;
        format = new SimpleDateFormat(dateType);
        String todayDate = format.format(now);
        return todayDate;
    }

    public static String getNowTime() {
        format = new SimpleDateFormat("HH:mm:ss");
        Date day = new Date();//获取当前时间;
        String now = format.format(day);
        return now;
    }

    public static String getNow() {
        format = new SimpleDateFormat(DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        Date day = new Date();//获取当前时间;
        String now = format.format(day);
        return now;
    }

    public static String getNowYear() {
        String now = getNow();
        return now.substring(0, 4);
    }

    public static String getNowMonth() {
        String now = getNow();
        return now.substring(6, 7);
    }

    /**
     * 获取今天是本周的第几天
     *
     * @return
     */
    public static int getWeekDay() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        format = new SimpleDateFormat(DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        calendar = Calendar.getInstance();
        Date today = new Date();
        calendar.setTime(today);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekDay == 1) {
            weekDay = 7;
        } else {
            weekDay = weekDay - 1;
        }
        return weekDay;
    }

    /**
     * 获取今天是本月的第几天
     *
     * @return
     */
    public static int getMonthDay() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        format = new SimpleDateFormat(DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        calendar = Calendar.getInstance();
        Date today = new Date();
        calendar.setTime(today);
        int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
        return monthDay;
    }

    /**
     * 获取今天是本年的第几天
     *
     * @return
     */
    public static int getYearDay() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        format = new SimpleDateFormat(DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        calendar = Calendar.getInstance();
        Date today = new Date();
        calendar.setTime(today);
        int yearDay = calendar.get(Calendar.DAY_OF_YEAR);
        return yearDay;
    }

    /**
     * 获取当月有几天
     *
     * @param
     * @return
     */
    public static int getDaysOfMonth() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        format = new SimpleDateFormat(DateType.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        Date date = new Date();
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


}
