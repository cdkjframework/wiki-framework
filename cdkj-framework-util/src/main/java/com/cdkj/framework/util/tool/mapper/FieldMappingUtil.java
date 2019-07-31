package com.cdkj.framework.util.tool.mapper;

import com.cdkj.framework.annotation.FieldMapping;
import com.cdkj.framework.util.tool.StringUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.core.util.tool
 * @ClassName: FieldMappingUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class FieldMappingUtil {

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
        if (mapping != null && !StringUtil.isNullAndSpaceOrEmpty(mapping.name()) && !mapping.name().equals("")) {
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
                    StringUtil.isNullAndSpaceOrEmpty(mapping.name()) ||
                    !mapping.name().equals(mappingName)) {
                continue;
            }
            return temp;
        }
        return null;
    }
}
