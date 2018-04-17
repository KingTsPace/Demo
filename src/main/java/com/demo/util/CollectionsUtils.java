package com.demo.util;

import java.util.ArrayList;
import java.util.List;

import com.demo.common.IopConst;
import org.apache.commons.collections.CollectionUtils;


/**
 * des：集合公共类
 * 
 * @author sWX301447
 * 
 */

public final class CollectionsUtils
{

    private static volatile CollectionsUtils instance;

    private CollectionsUtils()
    {
    }

    public static CollectionsUtils getInstance()
    {
        if (instance == null)
        {
            synchronized (CollectionsUtils.class)
            {
                if (instance == null)
                {
                    instance = new CollectionsUtils();
                }
            }
        }
        return instance;
    }

    /**
     * des：去重
     * 
     * @param list
     *            需要去重的List
     * @return 去重的集合
     */
    public List<String> removeRepection(List<String> list)
    {
        List<String> newList = new ArrayList<String>(IopConst.DEFAULTNUM);
        if (CollectionUtils.isEmpty(list))
        {
            return list;
        }
        int length = list.size();
        for (int i = 0; i < length; i++)
        {
            if (newList.contains(list.get(i)))
            {
                continue;
            }
            newList.add(list.get(i));
        }
        return newList;
    }

}
