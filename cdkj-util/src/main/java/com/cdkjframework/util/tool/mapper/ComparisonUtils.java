package com.cdkjframework.util.tool.mapper;

import com.cdkjframework.entity.ComparisonEntity;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: ComparisonUtil
 * @Description: 数据比较
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class ComparisonUtils {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(ComparisonUtils.class);


    /**
     * 调用object 的 method 所代表的方法，其方法的参数是 parameters
     */
    static List<String> dataTypeList = Arrays.asList("java.util.ArrayList", "java.util.List");

    /**
     * 实体比较
     *
     * @param source 原实体
     * @param target 目标实体
     * @param <T>    原实体
     * @param <S>    目标实体
     * @return 返回结果
     */
    public static <T, S> List<ComparisonEntity> EntityComparison(T source, S target) {
        return EntityComparison(source, target, false);
    }

    /**
     * 实体比较
     *
     * @param source 原实体
     * @param target 目标实体
     * @param isList 是否解析 list
     * @param <T>    原实体
     * @param <S>    目标实体
     * @return 返回结果
     */
    public static <T, S> List<ComparisonEntity> EntityComparison(T source, S target, boolean isList) {
        List<ComparisonEntity> entityList = new ArrayList<>();
        if (source == null || target == null) {
            return entityList;
        }

        List<Field> sourceFields = ReflectionUtils.getDeclaredFields(source.getClass());
        try {
            for (Field field :
                    sourceFields) {

                //目标值
                Object targetValue = ReflectionUtils.getFieldValue(target, field.getName());
                if (targetValue == null) {
                    continue;
                }
                //验证是否为 list 数据
                String dataType = field.getType().getName();
                if (dataTypeList.contains(dataType) && isList) {
                    Object sourceList = ReflectionUtils.getFieldValue(field, source);
                    List list = ListComparison((List) sourceList, (List) targetValue, true);
                    entityList.addAll(list);
                    continue;
                }

                //获取值 value
                String sourceValue = ReflectionUtils.getFieldValue(field, source).toString();
                if (sourceValue.equals(targetValue.toString())) {
                    continue;
                }
                ComparisonEntity entity = new ComparisonEntity();
                entity.setField(field.getName());
                entity.setCurrentValue(targetValue.toString());
                entity.setOriginalValue(sourceValue);

                entityList.add(entity);
            }
        } catch (Exception ex) {
            logUtil.error(ex.getMessage());
        }
        //返回结果
        return entityList;
    }

    /**
     * 实体比较
     *
     * @param source 原实体
     * @param target 目标实体
     * @param <T>    原实体
     * @param <S>    目标实体
     * @return 返回结果
     */
    public static <T, S> List<ComparisonEntity> ListComparison(List<T> source, List<S> target) {
        return ListComparison(source, target, false);
    }

    /**
     * 实体比较
     *
     * @param source 原实体
     * @param target 目标实体
     * @param isList 是否解析 list
     * @param <T>    原实体
     * @param <S>    目标实体
     * @return 返回结果
     */
    public static <T, S> List<ComparisonEntity> ListComparison(List<T> source, List<S> target, boolean isList) {
        List<ComparisonEntity> entityList = new ArrayList<>();
        if (source == null || target == null) {
            return entityList;
        }
        for (int i = 0; i < source.size(); i++) {
            if (target.size() <= i) {
                continue;
            }
            T t = source.get(i);
            S s = target.get(i);
            //数据
            List list = EntityComparison(t, s, isList);
            entityList.addAll(list);
        }
        //返回结果
        return entityList;
    }
}