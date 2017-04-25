package com.lawu.eshop.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Leach
 * @date 2017/3/22
 */
public class DateUtil {

    // 默认日期格式
    private static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";

    // 斜杠日期格式
    private static final String DATE_SPRIT_FORMAT = "yyyy/MM/dd";

    // int日期格式
    private static final String DATE_INT_FORMAT = "yyyyMMdd";

    // 默认日期时间格式
    private static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 斜杠日期时间格式
    private static final String DATETIME_SPRIT_FORMAT = "yyyy/MM/dd HH:mm:ss";

    // int日期时间格式
    private static final String DATETIME_INT_FORMAT = "yyyyMMddHHmmss";

    //默认时间格式
    private static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";

    //int时间格式
    private static final String TIME_INT_FORMAT = "HHmmss";

    // 默认日期格式化
    private static DateFormat dateFormat = null;

    // 斜杠日期格式化
    private static DateFormat dateSpritFormat = null;

    // int日期格式化
    private static DateFormat dateIntFormat = null;

    // 日期时间格式化
    private static DateFormat dateTimeFormat = null;

    // 斜杠日期时间格式化
    private static DateFormat dateTimeSpritFormat = null;

    // int日期时间格式化
    private static DateFormat dateTimeIntFormat = null;

    //时间格式化
    private static DateFormat timeFormat = null;

    //int时间格式化
    private static DateFormat timeIntFormat = null;

    private static Calendar gregorianCalendar = null;

    static {
        dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        dateSpritFormat = new SimpleDateFormat(DATE_SPRIT_FORMAT);
        dateIntFormat = new SimpleDateFormat(DATE_INT_FORMAT);
        dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        dateTimeSpritFormat = new SimpleDateFormat(DATETIME_SPRIT_FORMAT);
        dateTimeIntFormat = new SimpleDateFormat(DATETIME_INT_FORMAT);
        timeFormat = new SimpleDateFormat(TIME_DEFAULT_FORMAT);
        timeIntFormat = new SimpleDateFormat(TIME_INT_FORMAT);
        gregorianCalendar = new GregorianCalendar();
    }

    /**
     * 获取当前日期   yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateFormat.format(new Date());
    }

    /**
     * 获取当前日期   yyyy/MM/dd
     *
     * @return String
     */
    public static String getSpritDate() {
        return dateSpritFormat.format(new Date());
    }

    /**
     * 获取当前日期   yyyyMMdd
     *
     * @return String
     */
    public static String getIntDate() {
        return dateIntFormat.format(new Date());
    }

    /**
     * 获取当前日期时间   yyyy-MM-dd HH:mm:ss
     *
     * @return String
     */
    public static String getDateTime() {
        return dateTimeFormat.format(new Date());
    }

    /**
     * 获取当前日期时间   yyyy/MM/dd HH:mm:ss
     *
     * @return String
     */
    public static String getSpritDateTime() {
        return dateTimeSpritFormat.format(new Date());
    }

    /**
     * 获取当前日期时间   yyyyMMddHHmmss
     *
     * @return String
     */
    public static String getIntDateTime() {
        return dateTimeIntFormat.format(new Date());
    }

    /**
     * 获取当前时间   HH:mm:ss
     *
     * @return String
     */
    public static String getTime() {
        return timeFormat.format(new Date());
    }

    /**
     * 获取当前时间   HHmmss
     *
     * @return String
     */
    public static String getIntTime() {
        return timeIntFormat.format(new Date());
    }

    /**
     * date格式转换为double格式
     *
     * @param date
     * @return
     */
    public static Double dateDateToDoubleDate(Date date) {
        return Double.valueOf(dateTimeIntFormat.format(date));
    }

    /**
     * 日期格式化yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getDateTimeFormat(Date date) {
        return dateTimeFormat.format(date);
    }

    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getDateFormat(Date date) {
        return dateFormat.format(date);
    }

    /**
     * 两个时间相隔天数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static int daysOfTwo(Date beginDate, Date endDate) {
        if (null == beginDate || null == endDate) {
            return -1;
        }

        long intervalMilli = endDate.getTime() - beginDate.getTime();
        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }

    /**
     * 两个时间相隔天数
     *
     * @param beginDate 开始日期
     * @return
     */
    public static int daysOfTwo(Date beginDate) {
        if (null == beginDate) {
            return -1;
        }

        Date endDate = new Date();
        long intervalMilli = endDate.getTime() - beginDate.getTime();
        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }

    //-------------------------------------------------------------网上下载暂未用到-------------------------------------------------

    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     * @param format
     * @return
     */
    public static Date formatDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return HH:mm:ss
     */
    public static String getTimeFormat(Date date) {
        return timeFormat.format(date);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param
     * @return
     */
    public static String getDateFormat(Date date, String formatStr) {

        return new SimpleDateFormat(formatStr).format(date);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @return
     */
    public static Date getDateFormat(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    public static Date getDateTimeFormat(String date) {
        try {
            return dateTimeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前日期(yyyy-MM-dd)
     *
     * @param
     * @return
     */
    public static Date getNowDate() {
        return DateUtil.getDateFormat(dateFormat.format(new Date()));
    }

    /**
     * 获取当前日期星期一日期
     *
     * @return date
     */
    public static Date getFirstDayOfWeek() {
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前日期星期日日期
     *
     * @return date
     */
    public static Date getLastDayOfWeek() {
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期星期一日期
     *
     * @param
     * @return date
     */
    public static Date getFirstDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期星期一日期
     *
     * @param
     * @return date
     */
    public static Date getLastDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前月的第一天
     *
     * @return date
     */
    public static Date getFirstDayOfMonth() {
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前月的最后一天
     *
     * @return
     */
    public static Date getLastDayOfMonth() {
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        gregorianCalendar.add(Calendar.MONTH, 1);
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        gregorianCalendar.add(Calendar.MONTH, 1);
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期前一天
     *
     * @param date
     * @return
     */
    public static Date getDayBefore(Date date) {
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day - 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期后一天
     *
     * @param date
     * @return
     */
    public static Date getDayAfter(Date date) {
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day + 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getNowYear() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getNowMonth() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当月天数
     *
     * @return
     */
    public static int getNowMonthDay() {
        Calendar d = Calendar.getInstance();
        return d.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取时间段的每一天
     *
     * @param
     * @param
     * @return
     */
    public static List<Date> getEveryDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        // 格式化日期(yy-MM-dd)
        startDate = DateUtil.getDateFormat(DateUtil.getDateFormat(startDate));
        endDate = DateUtil.getDateFormat(DateUtil.getDateFormat(endDate));
        List<Date> dates = new ArrayList<Date>();
        gregorianCalendar.setTime(startDate);
        dates.add(gregorianCalendar.getTime());
        while (gregorianCalendar.getTime().compareTo(endDate) < 0) {
            // 加1天
            gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(gregorianCalendar.getTime());
        }
        return dates;
    }

    /**
     * 获取提前多少个月
     *
     * @param month
     * @return
     */
    public static Date getFirstMonth(int month) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -month);
        return c.getTime();
    }

    /**
     * 判断从开始的时间到现在的时间是否超过overTime数值的时间
     *
     * @param fromDate 开始时间
     * @param now      现在时间
     * @param overTime 超过的时间
     * @param field    单位
     * @return
     */
    public static boolean isExceeds(Date fromDate, Date now, int overTime, int field) {
        // 所有参数不能为空
        if (fromDate == null || now == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(fromDate);//把当前时间赋给日历  
        calendar.add(field, overTime);  //添加超过时间
        Date afterDate = calendar.getTime();

        if (now.getTime() >= afterDate.getTime()) {
            return true;
        }

        return false;
    }

    /**
     * 获取now之后overTime的时间
     *
     * @param now      现在时间
     * @param overTime 超过的时间
     * @param field    单位
     * @return
     */
    public static Date add(Date now, int overTime, int field) {
        // 所有参数不能为空
        if (now == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(now);//把当前时间赋给日历  
        calendar.add(field, overTime);  //添加超过时间

        return calendar.getTime();
    }
}
