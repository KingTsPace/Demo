package com.demo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import com.demo.common.Developer;
import sun.misc.BASE64Encoder;

/**
 * @author zWX454479 MD5 加密
 */
public final class MD5Tool
{

    private static volatile MD5Tool md5Tool;

    private MessageDigest digest;

    private MD5Tool()
    {
        try
        {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            Developer.error("MD5Tool", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 单例
     * 
     * @return
     */
    public static MD5Tool getInstance()
    {
        if (md5Tool == null)
        {
            synchronized (MD5Tool.class)
            {
                if (md5Tool == null)
                {
                    md5Tool = new MD5Tool();
                }
            }
        }
        return md5Tool;
    }

    /**
     * md5 加密
     * 
     * @param content
     * @return
     */
    public String encoderByMd5(String content)
    {
        BASE64Encoder base64en = new BASE64Encoder();
        String newstr = null;
        try
        {
            newstr = base64en.encode(digest.digest(content.getBytes("utf-8")));
        }
        catch (UnsupportedEncodingException e)
        {
            Developer.error("MD5Tool", e);
            throw new RuntimeException(e);
        }
        return newstr;
    }
}
