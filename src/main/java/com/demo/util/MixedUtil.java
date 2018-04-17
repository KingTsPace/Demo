package com.demo.util;

import com.demo.common.IopConst;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;




/**
 * @author zWX454479 混合工具类，集合小一些小工具
 */
public class MixedUtil
{

    private static volatile MixedUtil util;

    public static MixedUtil getInstance()
    {
        if(util == null)
        {
            synchronized (MixedUtil.class)
            {
                if(util == null)
                {
                    util = new MixedUtil();
                }
            }
        }
        return util;
    }

    public static final String FORMARTDATETYPE_1 = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMARTDATETYPE_2 = "yyyy-MM-dd";

    public static final String FORMARTDATETYPE_3 = "yyyy-MM-dd HH:mm";

    public static final String FORMARTDATETYPE_4 = "yyyy/MM/dd HH:mm:ss";

    public static final String FORMARTDATETYPE_5 = "yyyyMMddHHmmss";
    
    public static final String FORMARTDATETYPE_6 = "yyyy-MM-dd HH";
    
    public static final String FORMARTDATETYPE_7 = "yyyy-MM";
    
    public static final String FORMARTDATETYPE_8 = "yyyy-MM周";

    /**
     * @param dateStr
     *            日期字符串
     * @param formatDateType
     *            时间转换类型
     * @return
     */
    public Date parseDate(String dateStr, String formatDateType)
    {
        if (dateStr == null || StringUtils.equals(dateStr, IopConst.STRING_EMPTY))
        {
            return null;
        }
        try
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDateType);
            return simpleDateFormat.parse(dateStr);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 自动判断日期格式，生成最简单的日期
     * 
     * @param dateStr
     *            日期字符串
     * @param formatDateType
     *            时间转换类型
     * @return
     */
    public Date parseDate(String dateStr)
    {
        if (dateStr == null || StringUtils.equals(dateStr, ""))
        {
            return null;
        }
        try
        {
            String formartStr = null;
            if (dateStr.indexOf(":") != -1)
            {
                formartStr = FORMARTDATETYPE_1;
            }
            else
            {
                formartStr = FORMARTDATETYPE_2;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formartStr);
            return simpleDateFormat.parse(dateStr);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 日期转化为String
     * 
     * @param date
     * @return date string
     */
    public String fmtDate(Date date)
    {
        if (null == date)
        {
            return null;
        }
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 判断某属性是否存在set方法
     * 
     * @param methods
     * @param fieldGetMet
     * @return 是否存在
     */
    public boolean checkSetMet(Method[] methods, String fieldSetMet)
    {
        int length = methods.length;
        for (int i = 0; i < length; i++)
        {
            if (StringUtils.equals(methods[i].getName(), fieldSetMet))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某属性是否存在get方法
     * 
     * @param methods
     * @param fieldGetMet
     * @return 是否存在
     */
    public boolean checkGetMet(Method[] methods, String fieldGetMet)
    {
        int length = methods.length;
        for (int i = 0; i < length; i++)
        {
            if (StringUtils.equals(methods[i].getName(), fieldGetMet))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 拼接属性的set方法
     * 
     * @param fieldName
     * @return set方法
     */
    public String parSetName(String fieldName)
    {
        int startIndex = 0;
        if (fieldName == null || StringUtils.equals(IopConst.STRING_EMPTY, fieldName))
        {
            return null;
        }
        if (StringUtils.equals(String.valueOf(fieldName.charAt(0)), String.valueOf('_')))
        {
            startIndex = 1;
        }
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase(Locale.US) + fieldName.substring(startIndex + 1);
    }

    /**
     * 拼接属性的set方法
     * 
     * @param fieldName
     * @return set方法
     */
    public String parGetName(String fieldName)
    {
        int startIndex = 0;
        if (fieldName == null || StringUtils.equals(IopConst.STRING_EMPTY, fieldName))
        {
            return null;
        }
        if (StringUtils.equals(String.valueOf(fieldName.charAt(0)), String.valueOf('_')))
        {
            startIndex = 1;
        }
        return "get" + fieldName.substring(startIndex, startIndex + 1).toUpperCase(Locale.US) + fieldName.substring(startIndex + 1);
    }

    public static String getQuesFileName()
    {
        Date dt = new Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        String fileName = fmt.format(dt);
        return fileName;
    }

    /**
     * 获得当前时间，并且是固定的格式“yyyyMMdd” 用于内容分析的文件命名
     * 
     * @return String
     */
    public static String getCurrentTime()
    {
        Date dt = new Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        String time = fmt.format(dt);
        return time;
    }

    /**
     * 获得当前时间，并且是固定的格式“yyyyMMddHHmmss” 用于效果预测的文件命名
     * 
     * @return
     */
    public static String getTime()
    {
        Date dt = new Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = fmt.format(dt);
        return time;
    }

    public List<Integer> arrayToIntegerList(List<Double> array)
    {
        List<Integer> list = new ArrayList<Integer>(IopConst.DEFAULTNUM);
        DecimalFormat format = new DecimalFormat("0");
        int size = array.size();
        for (int i = 0; i < size; i++)
        {
            list.add(Integer.parseInt(format.format(array.get(i) + 0.5d)));
        }
        return list;
    }
}
