package com.cdkjframework.util.tool.mapper;

import com.cdkjframework.annotation.FieldMapping;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.core.util.tool
 * @ClassName: FieldMappingUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class FieldMappingUtils {

    /**
     * mapping 信息
     */
    private static FieldMapping mapping;

    /**
     * 获取字段名
     *
     * @param field 字段
     * @return 返回结果
     */
    public static String getFieldNameByField(Field field) {
        String fieldName = field.getName();
        mapping = field.getAnnotation(FieldMapping.class);
        if (mapping != null && !StringUtils.isNullAndSpaceOrEmpty(mapping.name()) && !mapping.name().equals("")) {
            fieldName = mapping.name();
        }
        return fieldName;
    }


    /**
     * 通过映射名获取字段
     *
     * @param clazz       参数实体
     * @param mappingName 值
     * @return 返回结果
     */
    public static Field getFieldByMappingName(Class clazz, String mappingName) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field temp : fields) {
            mapping = temp.getAnnotation(FieldMapping.class);
            if (mapping == null || mapping.name() == null ||
                    StringUtils.isNullAndSpaceOrEmpty(mapping.name()) ||
                    !mapping.name().equals(mappingName)) {
                continue;
            }
            return temp;
        }
        return null;
    }
}
