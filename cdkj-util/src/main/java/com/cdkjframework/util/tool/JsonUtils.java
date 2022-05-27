package com.cdkjframework.util.tool;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;

import java.util.*;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.util.make
 * @ClassName: JsonUtil
 * @Description: JSON 对象操作
 * @Author: xiaLin
 * @Version: 1.0
 */

public class JsonUtils {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(JsonUtils.class);

    /**
     * 默认值
     */
    private static int initialCapacity = 73;

    /**
     * 是否为 json 数组对象
     *
     * @param json JSON字符串
     * @return 返回结果
     */
    public static boolean isValidArray(String json) {
        if (StringUtils.isNotNullAndEmpty(json)) {
            return JSON.isValidArray(json);
        } else {
            return false;
        }
    }

    /**
     * 返回 JSON 数组对象
     *
     * @param json JSON字符串
     * @return 返回结果
     */
    public static JSONArray parseArray(String json) {
        JSONArray jsonArray;
        try {
            if (StringUtils.isNotNullAndEmpty(json)) {
                jsonArray = JSON.parseArray(json);
            } else {
                jsonArray = new JSONArray();
            }
        } catch (Exception ex) {
            logUtil.error("字符串转JSON对象出错！" + ex.getMessage());
            jsonArray = new JSONArray();
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
        JSONObject jsonObject;
        try {
            if (StringUtils.isNotNullAndEmpty(json)) {
                jsonObject = JSON.parseObject(json);
            } else {
                jsonObject = new JSONObject();
            }
        } catch (Exception ex) {
            logUtil.error("字符串转JSON对象出错！" + ex.getMessage());
            jsonObject = new JSONObject();
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
        Map mapList = new HashMap(initialCapacity);
        if (StringUtils.isNullAndSpaceOrEmpty(jsonStr)) {
            return mapList;
        }
        try {
            if (isValidArray(jsonStr)) {
                mapList = jsonArrayToMap(parseArray(jsonStr));
            } else {
                mapList = jsonObjectToMap(parseObject(jsonStr));
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
        if (jsonObject == null || jsonObject.size() == IntegerConsts.ZERO) {
            return new HashMap<>(initialCapacity);
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
        if (jsonArray == null || jsonArray.size() == IntegerConsts.ZERO) {
            return new HashMap<>(initialCapacity);
        }
        //将数据转换
        final int initial = 130;
        Map<String, Object> mapList = new HashMap<String, Object>(initial);
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
            list = jsonArrayToList(parseArray(jsonStr), clazz);
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
            list = jsonArray.toJavaList(clazz);
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
            t = jsonObjectToBean(parseObject(jsonString), clazz);
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
            if (jsonObject != null && !jsonObject.isEmpty()) {
                t = (T) JSON.toJavaObject(jsonObject, clazz);
            }
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
            jsonObject = parseObject(objectToJsonString(obj));
        } catch (Exception ex) {
            logUtil.error("JSON转数据集出错！" + ex.getMessage());
        }

        //返回结果
        return jsonObject;
    }

    /**
     * 将数据源转换为 JSONObject
     *
     * @param object 数据源(字符串、数据集)
     * @return 返回结果
     */
    public static JSONArray listToJsonArray(Object object) {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = parseArray(objectToJsonString(object));
        } catch (Exception ex) {
            logUtil.error("JSON转数据集出错！" + ex.getMessage());
        }

        //返回结果
        return jsonArray;
    }

    /**
     * 将数据源转换为 JSONString
     *
     * @param object 数据源(字符串、数据集)
     * @return 返回结果
     */
    public static String listToJsonString(Object object) {
        JSONArray jsonArray = null;
        try {
            String json = objectToJsonString(object);
            if (isValidArray(json)) {
                jsonArray = parseArray(objectToJsonString(object));
            }
        } catch (Exception ex) {
            logUtil.error("JSON转数据集出错！" + ex.getMessage());
        }

        if (jsonArray == null) {
            return StringUtils.Empty;
        }

        //返回结果
        return JSON.toJSONString(jsonArray);
    }

    /**
     * 将 obj 转换为 string
     *
     * @param obj 实体
     * @return 返回结果
     */
    public static String objectToJsonString(Object obj) {
        String jsonObject = StringUtils.Empty;
        try {
            if (obj != null) {
                jsonObject = JSON.toJSONString(obj);
            }
        } catch (Exception ex) {
            logUtil.error("JSON转数据集出错！" + ex.getMessage());
        }

        //返回结果
        return jsonObject;
    }
}
