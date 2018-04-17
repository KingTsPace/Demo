package com.demo.common;

/**
 * des:异常类
 * 
 * @author sWX301447
 * 
 */
public class IopException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = -909201627778530094L;

    /**
     * 异常的描述信息
     * 
     * @param msg
     */
    public IopException(String msg)
    {
        super("throw BpdoException." + msg);
    }
    
    /**
     * 
     * @param message
     * @param cause
     */
    public IopException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * 异常抛出
     * @param cause
     */
    public IopException(Throwable cause) {
        super(cause);
    }
}
