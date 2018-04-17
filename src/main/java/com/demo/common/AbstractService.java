package com.demo.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



public abstract class AbstractService
{
    /**
     * 打印日志的实体
     */
    protected final static Logger logger = Logger.getLogger("operation");

    /**
     * 上下文
     */
    protected Map<String, Object> contextMap;

    /**
     * 请求
     */
    protected HttpServletRequest request;

    /**
     * 响应
     */
    protected HttpServletResponse response;
    
    /**
     * 返回的Map
     */
    private Map<String, Object> resMap;
    
    /**
     * 初始化json工具
     */
    protected JSONUtils jsonUtils = JSONUtils.getInstance();

    /**
     * 
     * @param key
     * @param value
     */
    public void appendJSON(String key, Object value)
    {
        if (StringUtils.isNotEmpty(key))
        {
            resMap.put(key, value);
        }
    }

    public Map<String, Object> getResMap()
    {
        return resMap;
    }

    public Map<String, Object> getContextMap()
    {
        return contextMap;
    }

    public void setContextMap(Map<String, Object> contextMap)
    {
        this.contextMap = contextMap;
    }

    public HttpServletRequest getRequest()
    {
        return request;
    }

    public void setRequest(HttpServletRequest request)
    {
        this.request = request;
    }

    public HttpServletResponse getResponse()
    {
        return response;
    }

    public void setResponse(HttpServletResponse response)
    {
        this.response = response;
    }

    public void init(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> lastcontextMap, Map<String, Object> resMap2)
    {
        request = req;
        response = resp;
        contextMap = lastcontextMap;
        resMap = resMap2;
    }

    public void started() throws IopException
    {
        if (!authUser(request, response))
        {
            contextMap.put(IopConst.EXECUTE, IopConst.IOP_RUN_FAILSE);
        }

        String exc = excute(contextMap);
        logger.info("AbstartService.started(), the service is " + contextMap.get(IopConst.SERVICEID) + " exceture is " + exc);
        contextMap.put(IopConst.STATUES, exc);

    }

    /**
     * 服务鉴权
     * 
     * @param req
     * @param resp
     * @return
     */
    public boolean authUser(HttpServletRequest req, HttpServletResponse resp)
    {

        return false;
    }

    public abstract String excute(Map<String, Object> context) throws IopException;

    public AbstractService()
    {
        super();
    }

}
