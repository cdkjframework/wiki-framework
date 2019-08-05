package com.cdkjframework.util.tool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.util.log.LogUtil;

import java.util.*;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.util.make
 * @ClassName: JsonUtil
 * @Description: JSON 对象操作
 * @Author: xiaLin
 * @Version: 1.0
 */

public class JsonUtil {

    /**
     * 日志
     */
    private static LogUtil logUtil = LogUtil.getLogger(JsonUtil.class);

    /**
     * 默认值
     */
    private static int initialCapacity = 0;

    /**
     * 返回 JSON 数组对象
     *
     * @param json JSON字符串
     * @return 返回结果
     */
    public static JSONArray parseArray(String json) {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = JSONArray.parseArray(json);
        } catch (Exception ex) {
            logUtil.error("字符串转JSON对象出错！" + ex.getMessage());
        }

        //返回结果
        return jsonArray;
    }

    /**
     * 返回结果对象
     *
     * @param json JSON对象
     * @return 返回结果
     */
    public static JSONObject parseObject(String json) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = JSONObject.parseObject(json);
        } catch (Exception ex) {
            logUtil.error("字符串转JSON对象出错！" + ex.getMessage());
        }

        //返回结果
        return jsonObject;
    }

    /**
     * 将 JSON 字符串转换为 map
     *
     * @param jsonStr JSON 字符串
     * @return 返回 map
     */
    public static Map<String, Object> jsonStringToMap(String jsonStr) {
        Map mapList = new HashMap();
        if (StringUtil.isNullAndSpaceOrEmpty(jsonStr)) {
            return mapList;
        }
        try {
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            try {
                jsonObject = JSONObject.parseObject(jsonStr);
            } catch (Exception ex) {
                ex.printStackTrace();
                jsonArray = JSONArray.parseArray(jsonStr);
            }
            if (jsonObject != null) {
                mapList = jsonObjectToMap(jsonObject);
            } else if (jsonArray != null) {
                mapList = jsonArrayToMap(jsonArray);
            }
        } catch (Exception ex) {
            logUtil.error("字符串转JSON对象出错！" + ex.getMessage());
        }

        //返回结果
        return mapList;
    }

    /**
     * JSONObject 转换为MAP
     *
     * @param jsonObject JSON对象
     * @return 返回 map
     */
    public static Map<String, Object> jsonObjectToMap(JSONObject jsonObject) {
        if (jsonObject == null || jsonObject.size() == 0) {
            return new HashMap<String, Object>(initialCapacity);
        }
        Map<String, Object> map = new HashMap<String, Object>(jsonObject.size());
        Iterator<String> iterator = jsonObject.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = jsonObject.getString(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * JSONArray 转换为MAP
     *
     * @param jsonArray JSON对象
     * @return 返回 map
     */
    public static Map<String, Object> jsonArrayToMap(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.size() == 0) {
            return new HashMap<String, Object>(initialCapacity);
        }
        //将数据转换
        Map<String, Object> mapList = new HashMap<String, Object>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Map<String, Object> map = jsonObjectToMap(jsonObject);
            mapList.putAll(map);
        }

        //返回结果
        return mapList;
    }

    /**
     * 将 JSON 对象转换为数据集
     *
     * @param jsonStr json 对象
     * @param clazz   实体集
     * @param <T>     实体
     * @return 返回结果
     */
    public static <T> List<T> jsonStringToList(String jsonStr, Class<T> clazz) {
        List list = new ArrayList();
        try {
            list = jsonArrayToList(JSONArray.parseArray(jsonStr), clazz);
        } catch (Exception ex) {
            logUtil.error("JSON转数据集出错！" + ex.getMessage());
        }

        //返回结果
        return list;
    }

    /**
     * 将 JSONArray 对象转换为数据集
     *
     * @param jsonArray json 对象
     * @param clazz     实体集
     * @param <T>       实体
     * @return 返回结果
     */
    public static <T> List<T> jsonArrayToList(JSONArray jsonArray, Class<T> clazz) {
        List list = new ArrayList();
        try {
            list = JSONObject.parseArray(jsonArray.toJSONString(), clazz);
        } catch (Exception ex) {
            logUtil.error("JSON转数据集出错！" + ex.getMessage());
        }

        //返回结果
        return list;
    }

    /**
     * 将 jsonString 对象转换为数据集
     *
     * @param jsonString json 对象
     * @param <T>        实体
     * @return 返回结果
     */
    public static <T> T jsonStringToBean(String jsonString, Class<T> clazz) {
        T t = null;
        try {
            t = jsonObjectToBean(JSONObject.parseObject(jsonString), clazz);
        } catch (Exception ex) {
            logUtil.error("JSON转数据集出错！" + ex.getMessage());
        }

        //返回结果
        return t;
    }

    /**
     * 将 jsonString 对象转换为数据集
     *
     * @param jsonObject json 对象
     * @param <T>        实体
     * @return 返回结果
     */
    public static <T> T jsonObjectToBean(JSONObject jsonObject, Class<T> clazz) {
        T t = null;
        try {
            t = (T) JSONObject.toJavaObject(jsonObject, clazz);
        } catch (Exception ex) {
            logUtil.error("JSON转数据集出错！" + ex.getMessage());
        }

        //返回结果
        return t;
    }

    /**
     * 将数据源转换为 JSONObject
     *
     * @param obj 数据源(字符串、实体)
     * @return 返回结果
     */
    public static JSONObject beanToJsonObject(Object obj) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = JSONObject.parseObject(objectToJsonString(obj));
        } catch (Exception ex) {
            logUtil.error("JSON转数据集出错！" + ex.getMessage());
        }

        //返回结果
        return jsonObject;
    }

    /**
     * 将数据源转换为 JSONObject
     *
     * @param obj 数据源(字符串、数据集)
     * @return 返回结果
     */
    public static JSONArray listToJsonArray(Object obj) {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = JSONArray.parseArray(objectToJsonString(obj));
        } catch (Exception ex) {
            logUtil.error("JSON转数据集出错！" + ex.getMessage());
        }

        //返回结果
        return jsonArray;
    }

    /**
     * 将 obj 转换为 string
     *
     * @param obj 实体
     * @return 返回结果
     */
    public static String objectToJsonString(Object obj) {
        String jsonObject = "";
        try {
            jsonObject = JSONObject.toJSONString(obj);
        } catch (Exception ex) {
            logUtil.error("JSON转数据集出错！" + ex.getMessage());
        }

        //返回结果
        return jsonObject;
    }
}
