package com.zrq.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class ConvertUtil {
    /**
     * Map转化为pojo类
     * @param paramMap
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T parseMap2Object(Map<String, Object> paramMap, Class<T> cls) {
        return JSONObject.parseObject(JSONObject.toJSONString(paramMap), cls);
    }

    /**
     * 对象转map
     * @param object
     * @return
     */
    public static Map<String, Object> object2Map(Object object){
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        Set<Map.Entry<String,Object>> entrySet = jsonObject.entrySet();
        Map<String, Object> map=new HashMap<String,Object>();
        for (Map.Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static String map2Url(Object object){
        StringBuffer result=new StringBuffer();
        Map<String,Object> map=object2Map(object);
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        int count=1;
        if(map.size()>0){
            result.append("?");
        }
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if(entry.getValue()!=null) {
                result.append(entry.getKey() + "=" + entry.getValue());
            }
            count++;
            if(count<=map.size()){
                result.append("&");
            }
         }
        return result.toString();
    }

    /**
     * 属性中有对象的
     * @param object
     * @return
     */
    public static String map2UrlRoom(Object object){
        StringBuffer result=new StringBuffer();
        Map<String,Object> map=object2Map(object);
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        int count=1;
        if(map.size()>0){
            result.append("?");
        }
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if(entry.getValue()!=null) {
                if(entry.getValue() instanceof JSONObject){
                    result.append(entry.getKey()+".id=" + ((JSONObject)entry.getValue()).get("id"));
                }else {
                    result.append(entry.getKey() + "=" + entry.getValue());
                }
            }
            count++;
            if(count<=map.size()){
                result.append("&");
            }
         }
        return result.toString();
    }

    public static String map2UrlContainTime(Object object){
        StringBuffer result=new StringBuffer();
        Map<String,Object> map=object2Map(object);
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        int count=1;
        if(map.size()>0){
            result.append("?");
        }
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if(entry.getValue()!=null) {
                if(entry.getValue() instanceof Date){
                    String str=TimeUtil.formatDate("yyyy-MM-dd HH:mm:ss",(Date)entry.getValue());
                    System.out.println(str);
                    result.append(entry.getKey() + "=" + str);
                }else {
                    result.append(entry.getKey() + "=" + entry.getValue());
                }
            }
            count++;
            if(count<=map.size()){
                result.append("&");
            }
         }
        return result.toString();
    }
}
