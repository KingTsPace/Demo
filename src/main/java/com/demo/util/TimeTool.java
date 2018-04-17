package com.demo.util;

import com.demo.common.IopConst;
import com.demo.common.IopException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


/**
 * des:时间工具类
 * 
 * @author sWX301447
 * 
 */
public final class TimeTool
{
    /**
     * yyyyMM格式
     */
    public final static String YYYYMM = "yyyyMM";

    /**
     * UTC时间参数
     */
    public final static String UTC = "UTC";

    /**
     * 零时区
     */
    public final static String GMT_0 = "GMT-0";

    /**
     * yyyyMMddhhmmss格式的数据
     */
    public final static String DEFAULTFORMATER = "yyyyMMddHHmmss";

    private static volatile TimeTool instance;

    private TimeTool()
    {

    }

    public static TimeTool getInstance()
    {
        if (instance == null)
        {
            synchronized (TimeTool.class)
            {
                if (instance == null)
                {
                    instance = new TimeTool();
                }
            }
        }
        return instance;
    }

    /**
     * des：将时间转换为format的格式的String
     * 
     * @param date
     *            时间
     * @param format
     *            格式
     * @return 生成对应格式的字符串
     */
    public String parseDate(Date date, String format)
    {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 将本地时间String转换为Date
     * 
     * @param time
     *            本地时间
     * @param format
     *            格式
     * 
     * @return
     * @throws IopException
     */
    public Date parseFormatLocalTime(String time, String format) throws IopException
    {
        return parseFormat(time, format, TimeZone.getDefault());
    }

    /**
     * des:将时间String转换为Date
     * 
     * @param time
     *            时间
     * @param format
     *            时间格式
     * @param timeZone
     *            时区
     * @return Date
     * @throws IopException
     */
    public Date parseFormat(String time, String format, TimeZone timeZone) throws IopException
    {
        Date date = new Date();
        DateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(timeZone);
        try
        {
            date = sdf.parse(time);
        }
        catch (ParseException e)
        {
            StringBuffer sb = new StringBuffer(IopConst.STRING_EMPTY);
            sb.append("Unparseable date: ");
            sb.append(time);
            sb.append("format is");
            sb.append(format);
            throw new IopException(sb.toString());
        }

        return date;
    }

    /**
     * 
     * @param utcTime
     *            UTC时间
     * @param format
     *            转换格式
     * @return UTC时间
     * @throws IopException
     */
    public Date parseFormatUTC(String utcTime, String format) throws IopException
    {
        return parseFormat(utcTime, format, TimeZone.getTimeZone(UTC));
    }

    /**
     * 
     * @param utcTime
     *            UTC时间
     * @param format
     *            转换格式
     * @return 毫秒的时间
     * @throws IopException
     */
    public long getLocalTimeFromUTC(String utcTime, String format) throws IopException
    {
        return parseFormat(utcTime, format, TimeZone.getTimeZone(UTC)).getTime();
    }

    /**
     * 
     * @param time
     *            本地时间
     * @param format
     *            转换格式
     * @return 毫秒时间
     * @throws IopException
     */
    public long getLocalTime(String time, String format) throws IopException
    {
        return parseFormatLocalTime(time, format).getTime();
    }

    /**
     * des:将long转换为时间
     * 
     * @param time
     *            时间
     * @param format
     *            格式
     * @return
     */
    public String formatTime(long time, String format)
    {
        Date date = new Date(time);
        return parseDate(date, format);
    }

    /**
     * des:将当前时间转换为format格式
     * 
     * @param format
     *            格式
     * @return
     */
    public String formatLocalTime(String format)
    {
        return formatTime(System.currentTimeMillis(), format);
    }

    /**
     * des:获取现在的UTC时间
     * 
     * @param format
     * @return 现在的UTC时间
     */

    /**
     * 
     * @return
     */
    public String formatUTCTime()
    {
        String format = DEFAULTFORMATER;
        Date currentDate = new Date();
        DateFormat formatter = new SimpleDateFormat(format);
        TimeZone utcTimezone = TimeZone.getTimeZone(GMT_0);
        formatter.setTimeZone(utcTimezone);
        String utc_date = formatter.format(currentDate);
        return utc_date;
    }

    /**
     * 
     * @param format
     * @return
     */
    public String formatUTCTime(String format)
    {
        if ((null == format) || (format.trim().length() == 0))
        {
            format = DEFAULTFORMATER;
        }
        Date currentDate = new Date();
        DateFormat formatter = new SimpleDateFormat(format);
        TimeZone utcTimezone = TimeZone.getTimeZone(GMT_0);
        formatter.setTimeZone(utcTimezone);
        String utc_date = formatter.format(currentDate);
        return utc_date;

    }

    /**
     * 
     * @param date
     *            时间
     * @param parameter
     *            1--year,2--month,5--date
     * @param num
     *            增加的时间
     * @return 增加后的时间
     */
    public Date addTime(Date date, int parameter, int num)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(parameter, num);
        return cal.getTime();
    }

    /**
     * 获取某一天对应的周日
     * 
     * @param date
     * @param formart
     * @return
     * @throws ParseException
     */
    public Date getWeekDay(String date, String formart) throws ParseException
    {

        SimpleDateFormat dateFormat = new SimpleDateFormat(formart);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (dayWeek == 1)
        {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        calendar.setFirstDayOfWeek(Calendar.FRIDAY);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, 8 - day);
        return calendar.getTime();
    }

    /**
     * 根据类型转换时间
     * 
     * @param map
     *            用于存放beginTime，endTime的
     * @param beginTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @param timeType
     *            类型，1为时，2为天，3为周，4为月
     * @throws ParseException
     */
    public void checkDate(Map<String, Object> map, String beginTime, String endTime, int timeType) throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parse = new SimpleDateFormat("yyyyMMdd");
        beginTime = "".equals(beginTime) ? "" : parse.format(dateFormat.parse(beginTime));
        endTime = "".equals(endTime) ? "" : parse.format(dateFormat.parse(endTime));

        Calendar calendar = Calendar.getInstance(); // 得到日历
        // Time Type 判断是时周天月
        int endYear = 0;
        int endMonth = 0;
        int startMonth = 0;
        int startYear = 0;

        boolean flag = IopConst.STRING_EMPTY.equals(beginTime) && IopConst.STRING_EMPTY.equals(endTime);
        switch (timeType)
        {
        // 时
            case 1:
                if (flag)
                {
                    calendar.setTime(new Date());// 把当前时间赋给日历
                    calendar.add(Calendar.DATE, -1); // 设置为前一天
                    map.put(IopConst.Activity.ENDTIME, parse.format(calendar.getTime()) + "23");
                    map.put(IopConst.Activity.BEGINTIME, parse.format(calendar.getTime()) + "00");
                }
                else
                {
                    // 0点统计 0-1点内的数据
                    map.put(IopConst.Activity.BEGINTIME, beginTime + "00");
                    map.put(IopConst.Activity.ENDTIME, endTime + "23");
                }
                break;
            case 2:
                // 日
                if (flag)
                {
                    // 默认展示最近30天的数据
                    calendar.setTime(new Date());
                    calendar.add(Calendar.DATE, -1);
                    map.put(IopConst.Activity.ENDTIME, parse.format(calendar.getTime()));
                    calendar.add(Calendar.DATE, -30);
                    map.put(IopConst.Activity.BEGINTIME, parse.format(calendar.getTime()));
                }
                else
                {
                    map.put(IopConst.Activity.BEGINTIME, beginTime);
                    map.put(IopConst.Activity.ENDTIME, endTime);
                }
                break;
            case 3:
                // 周
                if (flag)
                {
                    calendar.setTime(new Date());

                    calendar.setTime(getWeekDay(dateFormat.format(calendar.getTime()), "yyyy-MM-dd"));
                    calendar.add(Calendar.DATE, -7);

                    endTime = parse.format(calendar.getTime());

                    calendar.add(Calendar.DATE, -21);
                    beginTime = parse.format(calendar.getTime());
                }
                else
                {
                    Date begin = getWeekDay(beginTime, "yyyyMMdd");
                    beginTime = parse.format(begin);
                    Date end = getWeekDay(endTime, "yyyyMMdd");
                    endTime = parse.format(end);
                }
                map.put(IopConst.Activity.ENDTIME, endTime);
                map.put(IopConst.Activity.BEGINTIME, beginTime);
                break;
            case 4:
                if (flag)
                {
                    calendar.setTime(new Date());
                    // System.out.println(calendar.get(Calendar.MONTH));
                    calendar.add(Calendar.MONTH, -1);
                    endYear = calendar.get(Calendar.YEAR);
                    endMonth = calendar.get(Calendar.MONTH) + 1;
                    calendar.add(Calendar.MONTH, -3);
                    startYear = calendar.get(Calendar.YEAR);
                    startMonth = calendar.get(Calendar.MONTH) + 1;
                }
                else
                {
                    calendar.setTime(parse.parse(endTime));
                    endMonth = calendar.get(Calendar.MONTH) + 1;
                    endYear = calendar.get(Calendar.YEAR);

                    calendar.setTime(parse.parse(beginTime));
                    startMonth = calendar.get(Calendar.MONTH) + 1;
                    startYear = calendar.get(Calendar.YEAR);
                }
                map.put(IopConst.Activity.ENDTIME, IopConst.STRING_EMPTY + endYear
                        + (endMonth < 10 ? String.valueOf('0') + endMonth : endMonth));
                map.put(IopConst.Activity.BEGINTIME, IopConst.STRING_EMPTY + startYear
                        + (startMonth < 10 ? String.valueOf('0') + startMonth : startMonth));
                break;
            default:
                break;
        }
    }

    /**
     * 根据日期得到对应的星期
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public String getWeek(String date) throws ParseException
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(date);
        cal.setTime(parse);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w == 0)
        {
            w = 7;
        }
        return String.valueOf(w);
    }

    /**
     * 获取某段时间内的所有日期
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public List<Date> findDate(String beginTime, String endTime) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dateList = new ArrayList<Date>(IopConst.DEFAULTNUM);
        Date start = sdf.parse(beginTime);
        Date end = sdf.parse(endTime);
        dateList.add(start);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(end);
        boolean flag = end.after(calBegin.getTime());
        // 测试此日期是否在指定日期之后
        while (flag)
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(calBegin.getTime());
            flag = end.after(calBegin.getTime());
        }
        return dateList;
    }

    /**
     * 查询一段时间范围内每月最后一天的日期信息
     * 
     * @param beginTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public List<String> getLastDay(String beginTime, String endTime) throws ParseException
    {
        List<String> list = new ArrayList<String>(IopConst.DEFAULTNUM);
        Calendar calendar = Calendar.getInstance(); // 得到日历
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        calendar.setTime(dateFormat.parse(dateFormat.format(sdf.parse(beginTime)))); // 将日期设置为指定时间

        boolean flag = dateFormat.parse(dateFormat.format(sdf.parse(endTime))).after(calendar.getTime());
        while (flag)
        {
            calendar.set(Calendar.DAY_OF_MONTH, 1); // 将日期设置为该月第一天
            calendar.roll(Calendar.DATE, -1); // 将日期回滚到当月的最后一天
            list.add(dateFormat.format(calendar.getTime()));
            flag = dateFormat.parse(dateFormat.format(sdf.parse(endTime))).after(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            calendar.setTime(calendar.getTime());
        }
        return list;

    }

}
