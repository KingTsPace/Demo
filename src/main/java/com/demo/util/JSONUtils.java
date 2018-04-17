package com.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.demo.common.IopConst;
import com.demo.common.IopException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * des：JSON的工具类
 * 
 * @author zWX418688
 * 
 */
public final class JSONUtils
{
    private static volatile JSONUtils jsonUtils;

    private String simpleName = null;

    private String genericType = null;

    private int start = 0;

    private int end = 0;

    // private JSONArray jsonArray = null;

    private MixedUtil mixedUtil = MixedUtil.getInstance();// 拼接SET,GET方法

    public static JSONUtils getInstance()
    {
        if (jsonUtils == null)
        {
            synchronized (JSONUtils.class)
            {
                if (jsonUtils == null)
                {
                    jsonUtils = new JSONUtils();
                }
            }
        }
        return jsonUtils;
    }

    private JSONUtils()
    {

    }

    public String getValues(JSONObject object, String key) throws JSONException
    {

        String values = null;
        if (object.has(key))
        {
            values = object.getString(key);
        }
        return values;
    }

    // Begin: modify by ywx418687 on 20161216

    /**
     * 删除JSON中有"_"的键值
     * 
     * @param object
     * @return
     */
    public JSONObject delUnderline(JSONObject object)
    {
        // 获得key迭代
        Iterator<?> keys = object.keys();

        // 需要删除的key集合
        List<String> unNeedKeys = new ArrayList<String>(IopConst.DEFAULTNUM);

        String next = null;

        // 遍历key
        boolean flag = keys.hasNext();
        while (flag)
        {
            next = keys.next().toString();
            if (next.contains("_"))
            {
                unNeedKeys.add(next);
            }
            flag = keys.hasNext();
        }

        int len = unNeedKeys.size();
        for (int i = 0; i < len; i++)
        {
            object.remove(unNeedKeys.get(i));
        }

        return object;
    }

    /**
     * 删除JSON值为null的键值
     * 
     * @param object
     * @return
     */
    public JSONObject delNull(JSONObject object, String... params) throws JSONException
    {
        // 获得key迭代
        Iterator<?> keys = object.keys();

        // 需要删除的key集合
        List<String> unNeedKeys = new ArrayList<String>(IopConst.DEFAULTNUM);

        String next = null;
        JSONArray jsonArray = null;

        Object jsonNull = JSONObject.NULL;

        // 遍历key
        boolean flag = keys.hasNext();
        int plen = params.length;
        while (flag)
        {
            next = keys.next().toString();

            // JSONObject$Null
            if (object.get(next) instanceof JSONArray)
            {
                jsonArray = object.getJSONArray(next);
                int size = jsonArray.length();
                for (int i = 0; i < size; i++)
                {
                    if (jsonArray.get(i) instanceof JSONObject)
                    {
                        delNull(jsonArray.getJSONObject(i), params);
                    }
                }
            }
            if (object.get(next) instanceof JSONObject)
            {
                delNull(object.getJSONObject(next), params);
            }
            if (object.get(next) == jsonNull)
            {
                unNeedKeys.add(next);
            }
            else
            {
                for (int i = 0; i < plen; i++)
                {
                    if (object.getString(next).equals(params[i]))
                    {
                        unNeedKeys.add(next);
                    }
                }
            }

            flag = keys.hasNext();
        }

        // 删除key和value
        int len = unNeedKeys.size();
        for (int i = 0; i < len; i++)
        {
            object.remove(unNeedKeys.get(i));
        }

        return object;
    }

    /**
     * 将对象集合转成JSONArray
     * 
     * @param <T>
     * @param beanList
     *            对象集合
     * @param flag
     *            是否删除为null的数据(true删除,false不删除)
     * @param params
     *            需要删除的额外的字符串，可以不传
     * @return JSONArray
     * @throws IopException
     */
    @SuppressWarnings("unchecked")
    public <T> JSONArray beanListToArray(List<T> beanList, boolean flag, boolean arrayFlag, String... params) throws IopException
    {
        try
        {
            JSONArray array = new JSONArray();

            if (CollectionUtils.isEmpty(beanList))
            {
                return array;
            }

            JSONObject object = null;
            JSONArray temp = null;
            int len = beanList.size();
            for (int i = 0; i < len; i++)
            {
                if (beanList.get(i) instanceof List)
                {
                    temp = beanListToArray((List<Object>) beanList.get(i), flag);
                    array.put(temp);
                }
                else
                {
                    object = new JSONObject(beanList.get(i));

                    if (flag)
                    {
                        delNull(object, params);
                    }
                    if (arrayFlag)
                    {
                        replaceList(object, beanList.get(i));
                    }
                    delUnderline(object);
                    array.put(object);
                }
            }
            return array;
        }
        catch (JSONException e)
        {
            throw new IopException(e);
        }

    }

    public <T> JSONArray beanListToArray(List<T> beanList, boolean flag, String... params) throws JSONException
    {
        try
        {
            return beanListToArray(beanList, flag, false, params);
        }
        catch (IopException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将对象集合转成JSONArray(不删除为null的数据)
     * 
     * @param <T>
     * @param beanList
     *            对象集合
     * @return JSONArray
     * @throws JSONException
     */
    public <T> JSONArray beanListToArray(List<T> beanList) throws JSONException
    {
        return beanListToArray(beanList, false);
    }

    // END: modify by ywx418687 on 20161216

    /**
     * 将JSONArray转换成List<Integer>
     * 
     * @param array
     * @return List
     * @throws JSONException
     */
    public List<Integer> arrayToIntList(JSONArray array) throws JSONException
    {
        List<Integer> list = new ArrayList<Integer>(IopConst.DEFAULTNUM);
        if (array == null)
        {
            return list;
        }
        int length = array.length();

        for (int i = 0; i < length; i++)
        {
            list.add(array.getInt(i));
        }
        return list;

    }

    /**
     * 将JSONArray转换成List<String>
     * 
     * @param array
     * @return List
     * @throws JSONException
     */
    public List<String> arrayToStrList(JSONArray array) throws JSONException
    {
        if (array == null)
        {
            return null;
        }
        List<String> list = new ArrayList<String>(IopConst.DEFAULTNUM);
        int length = array.length();
        if (length == 0)
        {
            return list;
        }
        else
        {
            for (int i = 0; i < length; i++)
            {
                list.add(array.getString(i));
            }
            return list;
        }
    }

    /**
     * 获取JSONArray
     * 
     * @param object
     * @param key
     * @return
     * @throws JSONException
     */
    public JSONArray getJSONArray(JSONObject object, String key) throws JSONException
    {

        if (!object.has(key))
        {
            return new JSONArray();
        }

        String str = object.getString(key);

        if (StringUtils.isBlank(key))
        {
            return new JSONArray();
        }
        return new JSONArray(str);
    }

    /**
     * @param jsonObject
     *            json对象
     * @param clazz
     *            类
     * @return
     * @throws Exception
     */
    public <T> Object jsonToBean(JSONObject jsonObject, Class<?> clazz, String... params) throws Exception
    {
        // 获取所有声明的属性
        Field[] fields = clazz.getDeclaredFields();

        Field field = null;
        // 获取所有声明的方法
        Method[] methods = clazz.getDeclaredMethods();
        Object bean = clazz.newInstance();

        List<String> paramsList = new ArrayList<String>(Arrays.asList(params));
        Object object = null;
        int size = fields.length;
        JSONArray jsonArray = null;
        String substring = null;

        for (int j = 0; j < size; j++)
        {
            // 对于每一个属性
            field = fields[j];

            if (jsonObject.has(field.getName()))// 如果JSON中存在此属性的字段名
            {
                String fieldSetName = mixedUtil.parSetName(field.getName());// 获取字段的SET方法
                if (fieldSetName == null || !mixedUtil.checkSetMet(methods, fieldSetName))// 遍历方法集判断是否存在这样字段的SET方法
                {
                    continue;
                }

                Method fieldSetMethod = clazz.getMethod(fieldSetName, field.getType());// 把SET方法字符串变成可执行的方法
                if (paramsList.contains(field.getName()))
                {
                    fieldSetMethod.invoke(bean, jsonObject.getString(field.getName()));// 保证1.0
                    continue;
                }

                if (StringUtils.equals(field.getType().getSimpleName(), "List"))// 如果类型是LIST
                {
                    List<Object> list = new ArrayList<Object>(IopConst.DEFAULTNUM);
                    jsonArray = jsonObject.getJSONArray(field.getName());// 获取JSON中的此字段的ARRAY
                    genericType = field.getGenericType().toString();// 得到
                                                                    // java.lang.List<java.lang.Integer>
                    start = genericType.indexOf("<");
                    end = genericType.indexOf(">");
                    // 截取泛型
                    substring = genericType.substring(start + 1, end);
                    int length = jsonArray.length();
                    for (int i = 0; i < length; i++)
                    {
                        if (substring.startsWith("java.lang"))// 表示String,Integer之类
                        {
                            object = jsonArray.getString(i);
                        }
                        else
                        {
                            object = jsonToBean(jsonArray.getJSONObject(i), Class.forName(substring));// 实体类则调用自己
                        }
                        list.add(object);
                    }
                    fieldSetMethod.invoke(bean, field.getType().cast(list));// 调用THIS表示的方法
                }
                else if (StringUtils.equals(field.getType().getSimpleName(), "Double"))
                {
                    fieldSetMethod.invoke(bean, jsonObject.getDouble(field.getName()));// 保证1.0
                }
                else if (!field.getType().getName().startsWith("java.lang"))
                {
                    object = jsonToBean(jsonObject.getJSONObject(field.getName()), Class.forName(field.getType().getName()));
                    fieldSetMethod.invoke(bean, field.getType().cast(object));
                }
                else
                {
                    fieldSetMethod.invoke(bean, field.getType().cast(jsonObject.get(field.getName())));
                }
            }

        }
        return bean;
    }

    /**
     * 将JSONArray转换成List<Map>
     * 
     * @param array
     * @return List
     * @throws JSONException
     */
    public List<Map<String, Object>> arrayToMapList(JSONArray array) throws JSONException
    {
        Map<String, Object> map = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(IopConst.DEFAULTNUM);

        if (array == null || array.length() == 0)
        {
            return list;

        }
        int size = array.length();
        for (int i = 0; i < size; i++)
        {
            map = objectToMap(array.getJSONObject(i));
            list.add(map);
        }

        return list;
    }

    public Map<String, Object> objectToMap(JSONObject object) throws JSONException
    {
        Map<String, Object> map = new HashMap<String, Object>(IopConst.DEFAULTNUM);

        JSONArray array = object.names();
        String key = IopConst.STRING_EMPTY;
        if (array != null)
        {
            int size = array.length();
            for (int i = 0; i < size; i++)
            {
                key = array.getString(i);
                map.put(key, object.get(key));
            }
        }
        return map;
    }

    /**
     * 讲list中的null转成空字符串
     * 
     * @param list
     * @return
     */
    public List<String> removeNull(List<String> list)
    {
        if (list.contains(null))
        {
            List<String> nullList = new ArrayList<String>(IopConst.DEFAULTNUM);
            nullList.add(null);
            list.removeAll(nullList);
            list.add(IopConst.STRING_EMPTY);
        }
        return list;
    }

    /**
     * 解决json转换后，集合中的String等类型的数据转换异常
     * 
     * @param jsonObject
     * @param t
     * @throws IopException
     */
    @SuppressWarnings("unchecked")
    public <T> void replaceList(JSONObject jsonObject, T t) throws IopException
    {

        try
        {
            Class<?> clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            // 获取所有声明的方法
            Method[] methods = clazz.getDeclaredMethods();
            Method method = null;
            simpleName = null;
            genericType = null;
            String substring = null;
            start = 0;
            end = 0;
            List<T> cast = null;
            JSONArray jsonArray = null;
            Field field = null;
            String fieldSetName = null;
            String fieldGetName = null;
            int len = 0;
            int lLen = 0;
            boolean flag = true;
            for (int i = 0; i < fields.length; i++)
            {
                field = fields[i];
                fieldSetName = mixedUtil.parSetName(field.getName());
                fieldGetName = mixedUtil.parGetName(field.getName());
                flag = replaceList_CheckField(fieldSetName, fieldGetName, methods);
                if (!flag)
                {
                    continue;
                }
                simpleName = field.getType().getSimpleName();
                if (!"List".equals(simpleName))
                {
                    continue;
                }
                genericType = field.getGenericType().toString();
                start = genericType.indexOf("<");
                end = genericType.indexOf(">");
                substring = genericType.substring(start + 1, end);
                method = clazz.getMethod(fieldGetName);// 获取get方法
                if (jsonObject.has(field.getName()) && jsonObject.get(field.getName()) != JSONObject.NULL)
                {
                    jsonArray = new JSONArray();
                    if (substring.startsWith("java.lang"))
                    {
                        jsonObject.remove(field.getName());
                        List<Object> list = (List<Object>) method.invoke(t);
                        lLen = list.size();
                        for (int j = 0; j < lLen; j++)
                        {
                            jsonArray.put(list.get(j));
                        }
                        jsonObject.put(field.getName(), jsonArray);
                    }
                    else
                    {
                        cast = (List<T>) field.getType().cast(method.invoke(t));
                        jsonArray = jsonObject.getJSONArray(field.getName());
                        len = jsonArray.length();
                        for (int j = 0; j < len; j++)
                        {
                            replaceList(jsonArray.getJSONObject(j), cast.get(j));
                        }
                    }
                }
                else
                {
                    continue;
                }
            }
        }
        catch (ReflectiveOperationException e)
        {
            throw new IopException(e);
        }
        catch (JSONException e)
        {
            throw new IopException(e);
        }
    }

    //replaceList方法圈复杂度抽取
    private boolean replaceList_CheckField(String fieldSetName, String fieldGetName, Method[] methods)
    {
        if (fieldSetName == null || fieldGetName == null)
        {
            return false;
        }
        if (!mixedUtil.checkSetMet(methods, fieldSetName) || !mixedUtil.checkGetMet(methods, fieldGetName))
        {
            return false;
        }
        return true;
    }
}
