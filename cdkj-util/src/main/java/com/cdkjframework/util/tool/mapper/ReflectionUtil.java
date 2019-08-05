package com.cdkjframework.util.tool.mapper;

import com.cdkjframework.constant.DataTypeConst;
import com.cdkjframework.util.log.LogUtil;
import com.cdkjframework.util.tool.StringUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.core.util.tool
 * @ClassName: ReflectionUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class ReflectionUtil {

    /**
     * 日志
     */
    private static LogUtil logUtil = LogUtil.getLogger(ReflectionUtil.class);

    /**
     * 获取字段值
     *
     * @param field 字段
     * @param t     实体
     * @param <T>   实体类型
     * @return 返回结果值
     */
    public static <T> Object getFieldValue(Field field, T t) {
        Object value = "";
        try {
            //抑制Java对其的检查
            field.setAccessible(true);
            value = field.get(t);
            if (StringUtil.isNullAndSpaceOrEmpty(value)) {
                value = "";
            }
        } catch (IllegalAccessException e) {
            logUtil.error(e.getMessage());
        }

        //返回结果
        return value;
    }

    /**
     * 获取方法
     *
     * @param object
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (Exception e) {
                logUtil.error(e);
            }
        }
        return method;
    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected, default)
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @param parameters     : 父类中的方法参数
     * @param dataType       : 数据类型
     * @return 父类中方法的执行结果
     */
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,
                                      Object[] parameters, String dataType) {
        //根据 对象、方法名和对应的方法参数 通过取 Method 对象
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        //抑制Java对方法进行检查,主要是针对私有方法而言
        method.setAccessible(true);
        try {
            if (null != method) {
                //调用object 的 method 所代表的方法，其方法的参数是 parameters
                final List<String> dataTypeList = new ArrayList<>();
                dataTypeList.add("java.util.ArrayList");
                dataTypeList.add("java.util.List");
                if (dataTypeList.contains(dataType)) {
                    return method.invoke(object, parameters);
                } else {
                    Object value = ConvertDataTypes(parameters, dataType);
                    return method.invoke(object, new Object[]{value});
                }
            }
        } catch (IllegalArgumentException e) {
            logUtil.info(dataType);
            logUtil.error(e);
        } catch (IllegalAccessException e) {
            logUtil.error(e);
        } catch (InvocationTargetException e) {
            logUtil.error(e);
        }
        return null;
    }

    /**
     * 获取字段
     *
     * @param object
     * @param fieldName
     * @return
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;
        List<Field> fieldList = getDeclaredFields(object);
        try {
            Optional<Field> optional = fieldList
                    .stream()
                    .filter(f -> fieldName.equals(f.getName()))
                    .findFirst();
            if (optional.isPresent()) {
                field = optional.get();
            }
        } catch (Exception e) {
            logUtil.error(e);
        }
        return field;
    }

    /**
     * 获取到全部实体属性
     * 包括父类
     *
     * @param t   实体
     * @param <T> 实体类型
     * @return 返回结果字段
     */
    public static <T> List<Field> getDeclaredFields(T t) {
        return getDeclaredFields(t.getClass());
    }

    /**
     * 获取到全部实体属性
     * 包括父类
     *
     * @param clazz 实体
     * @param <T>   实体类型
     * @return 返回结果字段
     */
    public static <T> List<Field> getDeclaredFields(Class<T> clazz) {
        List<Field> fieldList = new ArrayList<>();
        try {
            //当前类的属性
            Field[] fields = clazz.getDeclaredFields();
            fieldList = Arrays.stream(fields)
                    .collect(Collectors.toList());
            //父类字段

            Field[] parentFields = clazz.getSuperclass().getDeclaredFields();
            List list = Arrays.stream(parentFields).collect(Collectors.toList());
            fieldList.addAll(list);
        } catch (Exception ex) {
            logUtil.error(ex);
        }

        //返回结果
        return fieldList;
    }

    /**
     * 设置字段值
     *
     * @param object
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        //根据 对象和属性名通过取 Field对象
        Field field = getDeclaredField(object, fieldName);
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //将 object 中 field 所代表的值 设置为 value
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            logUtil.error(e);
        } catch (IllegalAccessException e) {
            logUtil.error(e);
        }

    }

    /**
     * 获取字段值
     *
     * @param object
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object object, String fieldName) {
        //根据 对象和属性名通过取 Field对象
        Field field = getDeclaredField(object, fieldName);
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //获的属性值
            return field.get(object);
        } catch (Exception e) {
            logUtil.error(e);
        }
        return null;
    }


    /**
     * 数据类型转换
     *
     * @param parameters 数据
     * @param dataType   数据类型
     * @return 返回结果
     */
    private static Object ConvertDataTypes(Object[] parameters, String dataType) {
        Object obj = null;
        if (parameters.length == 0 || StringUtil.isNullAndSpaceOrEmpty(parameters[0])) {
            return obj;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String value = parameters[0].toString();
        switch (dataType) {
            case DataTypeConst.booleanName:
            case DataTypeConst
                    .bigBooleanName:
                obj = StringUtil.convertBoolean(value);
                break;
            case DataTypeConst.bigDecimalName:
                obj = BigDecimal.valueOf(Float.valueOf(value));
                break;
            case DataTypeConst.dateName:
                if (value.contains("CST")) {
                    obj = new Date(value);
                } else {
                    try {
                        obj = dateFormat.parse(value);
                    } catch (ParseException e) {
                        logUtil.error(DataTypeConst.dateName + "：" + value);
                        logUtil.error(e);
                    }
                }
                break;
            case DataTypeConst.intName:
            case DataTypeConst.integerName:
                obj = Integer.valueOf(value);
                break;
            case DataTypeConst.timestampName:
                Long timestamp = StringUtil.convertLong(value);
                if (timestamp > 0) {
                    obj = new Timestamp(Long.valueOf(value));
                } else {
                    obj = new Timestamp(new Date(value).getTime());
                }
                break;
            case DataTypeConst.sqlDateName:
                obj = new java.sql.Date(Long.valueOf(value));
                break;
            case DataTypeConst.StringName:
                obj = String.valueOf(value);
                break;
            case DataTypeConst.doubleName:
            case DataTypeConst.bigDoubleName:
                obj = Double.valueOf(value);
                break;
            default:
                obj = parameters;
                break;
        }
        //返回结果
        return obj;
    }
}
