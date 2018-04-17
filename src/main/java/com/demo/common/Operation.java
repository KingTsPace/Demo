package com.demo.common;

import org.apache.log4j.Logger;

public class Operation
{
    protected final static Logger logger = Logger.getLogger(Operation.class);

    public static void error(String message, String e)
    {
        logger.error(message + IopConst.COMMA + e);
    }

    public static void debug(String method, String message)
    {
        logger.debug(method + IopConst.COMMA + message);
    }

    public static void info(String method, String message)
    {
        logger.info(method + IopConst.COMMA + message);
    }

    public static void info(String message)
    {
        logger.info(message);
    }
    /**
     * @param message 接口信息
     * @param e 抓取的异常
     */
    public static void error(String message,Exception e)
    {
        logger.error(message, e);
    }
    
    /**
     * @param message 接口信息
     * @param e 抓取的异常
     */
    public static void info(String message,Exception e)
    {
        logger.info(message, e);
    }
}
