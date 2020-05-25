package com.cdkjframework.util.tool;

import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.mapper.ReflectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.util.tool
 * @ClassName: CopyUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class CopyUtils {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(CopyUtils.class);

    /**
     * 数据类型
     */
    private static final String DATA_TYPE = "java.util.ArrayList";

    /**
     * 类的类型
     */
    private static final String CLASS_TYPE = "java";

    /**
     * 获取到空值列
     *
     * @param source 原数据源
     * @return 返回结果
     */
    public static <S> String[] getNullPropertyNames(S source) {
        //包装 bean
        final BeanWrapper src = new BeanWrapperImpl(source);
        //获取属性描述符
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        //记录信息
        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 拷贝 list 数据
     *
     * @param list   源数据
     * @param target 要转换成的对象
     * @return 返回对象数据集
     */
    public static <S, T> List<T> copyPropertiesList(List<S> list, Class<T> target) {
        List<T> result = new ArrayList();
        if (list != null) {
            for (S o : list) {
                try {
                    T d = target.newInstance();
                    copyProperties(o, d, false);
                    result.add(d);
                } catch (Exception e) {
                    logUtil.error(e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 拷贝 list 数据
     *
     * @param list   源数据
     * @param target 要转换成的对象
     * @return 返回对象数据集
     */
    public static <S, T> List<T> copyNoNullPropertiesList(List<S> list, Class<T> target) {
        List<T> result = new ArrayList();
        if (list != null) {
            for (S o : list) {
                try {

                    T d = target.newInstance();
                    copyProperties(o, d, true);
                    result.add(d);
                } catch (Exception e) {
                    logUtil.error(e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * 拷贝数据
     *
     * @param source 原数据源
     * @param target 当前数据
     */
    public static <S, T> void copyProperties(S source, T target) {
        copyProperties(source, target, false);
    }

    /**
     * 拷贝数据
     *
     * @param source 原数据源
     * @param target 当前数据
     */
    public static <S, T> void copyNoNullProperties(S source, T target) {
        copyProperties(source, target, true);
    }

    /**
     * 拷贝数据
     *
     * @param source 原数据源
     * @param target 当前数据
     */
    public static <S, T> T copyProperties(S source, Class<T> target) {
        T t;
        try {
            t = target.newInstance();
            copyProperties(source, t, false);
            return t;
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
            return null;
        }
    }

    /**
     * 拷贝数据
     *
     * @param source 原数据源
     * @param target 当前数据
     */
    public static <S, T> T copyNoNullProperties(S source, Class<T> target) {
        T t;
        try {
            t = target.newInstance();
            copyProperties(source, t, true);
            return t;
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
            return null;
        }
    }

    /**
     * 拷贝数据
     *
     * @param source 原数据源
     * @param target 当前数据
     * @param isNull 是否过虑空值
     * @param <S>    原参数
     * @param <T>    指定实体
     */
    private static <S, T> void copyProperties(S source, T target, boolean isNull) {
        try {
            if (isNull) {
                BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
            } else {
                BeanUtils.copyProperties(source, target);
            }
            // 验证是否有实体信息

            Field[] targetFields = target.getClass().getDeclaredFields();
            Field[] fields = source.getClass().getDeclaredFields();
            for (Field targetField :
                    targetFields) {
                targetField.setAccessible(true);
                // 读取值
                Object value = ReflectionUtils.getFieldValue(targetField, target);
                String typeName = value.getClass().getTypeName();
                if (typeName.contains(DATA_TYPE)) {
                    setArrayList((ArrayList) value, target, targetField);
                }
                if (value != "") {
                    continue;
                }
                // 验证是否有相同字段
                Optional<Field> optionalField = Arrays.stream(fields)
                        .filter(f -> f.getName().equals(targetField.getName()))
                        .findFirst();
                if (!optionalField.isPresent()) {
                    continue;
                }
                Field field = optionalField.get();
                field.setAccessible(true);
                // 读取值
                value = ReflectionUtils.getFieldValue(field, source);
                if (value == "") {
                    continue;
                }
                Object clazz = targetField.getType().newInstance();
                copyProperties(value, clazz);
                ReflectionUtils.setFieldValue(target, targetField, clazz);
            }
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
        }
    }

    /**
     * 设置列表
     *
     * @param arrayList   列表数据
     * @param targetField 目标类
     */
    private static <T> void setArrayList(ArrayList arrayList, T target, Field targetField) throws IllegalAccessException, InstantiationException {
        Class clazz = (Class) ((ParameterizedType) targetField.getGenericType()).getActualTypeArguments()[0];
        if (clazz.getTypeName().contains(CLASS_TYPE)) {
            return;
        }
        List list = new ArrayList();
        for (Object obj :
                arrayList) {
            Object objClazz = clazz.newInstance();
            copyProperties(obj, objClazz);
            list.add(objClazz);
        }
        ReflectionUtils.setFieldValue(target, targetField, list);
    }
}
