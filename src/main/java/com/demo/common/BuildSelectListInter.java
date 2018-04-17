package com.demo.common;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author zWX454479
 * 
 */
public interface BuildSelectListInter
{
    
    JSONArray buildSelectList(Map<String, Object> map) throws JSONException;

}
