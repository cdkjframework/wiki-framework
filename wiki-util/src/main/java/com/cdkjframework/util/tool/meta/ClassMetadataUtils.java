package com.cdkjframework.util.tool.meta;

import com.cdkjframework.constant.Application;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.util.tool.mapper
 * @ClassName: InterfaceUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class ClassMetadataUtils {

    /**
     * 获取属性
     *
     * @param beanClass 类型
     * @return 返回结果
     */
    public static Map<String, Object> getAttribute(Class beanClass) {
        MultiValueMap<String, Object> valueMap = Application.annotationMetadata.getAllAnnotationAttributes(beanClass.getName());
        Map<String, Object> map = new HashMap<>(32);
        for (Map.Entry<String, List<Object>> entry :
                valueMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * 获取名称
     *
     * @param beanClass 类型
     * @param name      名称
     * @return 返回结果
     */
    public static String getAttributeString(Class beanClass, String name) {
        Map<String, Object> map = getAttribute(beanClass);
        if (StringUtils.isNullAndSpaceOrEmpty(map.get(name))) {
            return "";
        } else {
            return map.get(name).toString();
        }
    }

    /**
     * 获取名称
     *
     * @param beanClass 类型
     * @param name      名称
     * @return 返回结果
     */
    public static Boolean getAttributeBoolean(Class beanClass, String name) {
        Map<String, Object> map = getAttribute(beanClass);
        return StringUtils.convertBoolean(map.get(name));
    }

    /**
     * 获取名称
     *
     * @param beanClass 类型
     * @param name      名称
     * @return 返回结果
     */
    public static int getAttributeInt(Class beanClass, String name) {
        Map<String, Object> map = getAttribute(beanClass);
        return StringUtils.convertInt(map.get(name));
    }
}
