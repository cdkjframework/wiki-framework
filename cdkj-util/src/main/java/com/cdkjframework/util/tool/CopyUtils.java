package com.cdkjframework.util.tool;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.mapper.ReflectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

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
        if (source == null) {
            return new String[]{};
        }
        //包装 bean
        final BeanWrapper wrapper = new BeanWrapperImpl(source);
        //获取属性描述符
        PropertyDescriptor[] propertyList = wrapper.getPropertyDescriptors();

        //记录信息
        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor property : propertyList) {
            Object srcValue = wrapper.getPropertyValue(property.getName());
            if (srcValue == null) {
                emptyNames.add(property.getName());
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
    @Deprecated
    public static <S, T> List<T> copyPropertiesList(List<S> list, Class<T> target) {
        return copyProperties(list, target);
    }

    /**
     * 拷贝 list 数据
     *
     * @param list   源数据
     * @param target 要转换成的对象
     * @return 返回对象数据集
     */
    @Deprecated
    public static <S, T> List<T> copyNoNullPropertiesList(List<S> list, Class<T> target) {
        return copyProperties(list, target);
    }

    /**
     * 拷贝数据
     *
     * @param source 原数据源
     * @param target 当前数据
     */
    @Deprecated
    public static <S, T> void copyProperties(S source, T target) {
        copyProperties(source, target, false);
    }

    /**
     * 拷贝数据
     *
     * @param source 原数据源
     * @param target 当前数据
     */
    @Deprecated
    public static <S, T> void copyNoNullProperties(S source, T target) {
        copyProperties(source, target, true);
    }

    /**
     * 拷贝数据
     *
     * @param sourceList 原数据源
     * @param target     目标数据
     */
    public static <S, T> List<T> copyProperties(List<S> sourceList, Class<T> target) {
        return copyNoNullProperties(sourceList, target);
    }

    /**
     * 拷贝数据
     *
     * @param source 原数据源
     * @param target 当前数据
     */
    public static <S, T> T copyProperties(S source, Class<T> target) {
        T t = null;
        try {
            BeanCopier beanCopier = create(source.getClass(), target);
            t = target.getDeclaredConstructor().newInstance();
            beanCopier.copy(source, t, null);
        } catch (Exception ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
        }
        return t;
    }

    /**
     * 拷贝数据
     *
     * @param sourceList 原数据源
     * @param target     目标数据
     */
    public static <S, T> List<T> copyNoNullProperties(List<S> sourceList, Class<T> target) {
        List<T> result = new ArrayList();
        if (!CollectionUtils.isEmpty(sourceList) && target != null) {
            for (S s :
                    sourceList) {
                try {
                    T t = copyNoNullProperties(s, target);
                    result.add(t);
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
    public static <S, T> T copyNoNullProperties(S source, Class<T> target) {
        T t;
        try {
            t = target.getDeclaredConstructor().newInstance();
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
            // 验证是否有实体信息
            if (source == null) {
                return;
            }
            BeanCopier beanCopier = create(source.getClass(), target.getClass());
            beanCopier.copy(source, target, null);
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
    private static <T> void buildArrayList(ArrayList arrayList, T target, Field targetField) throws IllegalAccessException, InstantiationException {
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

    /**
     * 创建信息
     *
     * @param source 原类型
     * @param target 目标类型
     * @return 返回创建结果
     */
    private static BeanCopier create(Class source, Class target) {
        return BeanCopier.create(source, target, false);
    }
}
