package com.cdkjframework.util.tool.mapper;

import com.cdkjframework.enums.basics.BasicsEnum;
import com.cdkjframework.util.tool.StringUtil;
import com.ctrip.framework.apollo.Config;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.core.util.tool
 * @ClassName: MapperUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class MapperUtil {

    /**
     * List 对象映射
     *
     * @param list 转换值
     * @return 返回结果
     * @throws Exception 异常信息
     */
    public static <T, S> List listToList(List<T> list, Class<S> clazz) throws Exception {
        List<S> arrayList = new ArrayList<>();
        for (T t : list) {
            arrayList.add(entityToEntity(t, clazz));
        }
        return arrayList;
    }

    /**
     * 单个对象映射
     *
     * @param t 实体
     * @return 返回结果
     * @throws Exception 异常信息
     */
    public static <T, S> S entityToEntity(T t, Class<S> clazz) throws Exception {
        if (List.class.isAssignableFrom(t.getClass())) {
            throw new Exception("Can't be a list!");
        }

        S s = clazz.newInstance();
        // 获取目标参数所有字段
        List<Field> lists = ReflectionUtil.getDeclaredFields(clazz);

        Field[] fields = t.getClass().getDeclaredFields();
        final List<String> dataType = new ArrayList<>();
        dataType.add("java.util.ArrayList");
        dataType.add("java.util.List");

        for (Field temp : fields) {

            //验证修饰符
            if (validateModification(temp)) {
                continue;
            }
            temp.setAccessible(true);
            //从注解中获取目标字段
            String targetFieldName = FieldMappingUtil.getFieldNameByField(temp);
            //获取field
            String fieldName = StringUtil.isNullAndSpaceOrEmpty(targetFieldName) ? temp.getName() : targetFieldName;

            //获取目标对象字段
            Field targetField = getBusFieldByLists(fieldName, lists);
            if (null == targetField) {
                continue;
            }
            //获取值value
            Object value = temp.get(t);
            //验证数据是否为空
            if (StringUtil.isNullAndSpaceOrEmpty(value)) {
                continue;
            }
            //验证是否为 ArrayList 数据
            if (dataType.contains(temp.getType().getName())) {
                String typeName = targetField.getGenericType().getTypeName();
                String clazzName = typeName.substring(15, typeName.length() - 1);
                Class clazzEntity = Class.forName(clazzName);
                value = listToList((ArrayList) value, clazzEntity);
            }

            mapping(s, temp, value, targetField);
        }
        return s;
    }

    /**
     * 将 apollo 配置信息转换为实体
     *
     * @param config    配置
     * @param enumClazz 枚举
     * @param clazz     实体
     * @param <T>       类型
     * @return 返回结果
     */
    public static <T> T apolloToEntity(Config config, BasicsEnum[] enumClazz, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        //创建实体
        T t = clazz.newInstance();
        Set<String> set = config.getPropertyNames();
        if (set.size() == 0) {
            return t;
        }
        //读取变量
        Object[] objectArray = set.toArray();
        //读取字段属性
        List<Field> fields = ReflectionUtil.getDeclaredFields(clazz);
        for (Object obj :
                objectArray) {
            BasicsEnum basicsEnum = getEnumCode(enumClazz, obj.toString());
            if (basicsEnum == null) {
                continue;
            }
            //读取名称
            String fieldName = basicsEnum.getCode();
            //读取字段
            Field field = getBusFieldByLists(fieldName, fields);
            if (StringUtil.isNullAndSpaceOrEmpty(field)) {
                continue;
            }

            //读取数据值 并验证是否为空
            Object objValue = config.getProperty(basicsEnum.getValue(), "");
            if (StringUtil.isNullAndSpaceOrEmpty(objValue)) {
                continue;
            }
            Class clazzField = field.getType();
            fieldName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            //设置值
            ReflectionUtil.invokeMethod(t, fieldName, new Class[]{clazzField}, new Object[]{objValue}, clazzField.getName());
        }

        //返回结果
        return t;
    }

    /**
     * 获取枚举编码
     *
     * @param enumClazz 枚举
     * @param name      枚举值
     * @return 返回枚举编码
     */
    private static BasicsEnum getEnumCode(BasicsEnum[] enumClazz, String name) {
        Optional<BasicsEnum> optional = Arrays.stream(enumClazz)
                .filter(f -> f.getValue().equals(name))
                .findFirst();
        //读取信息
        if (optional.isPresent()) {
            return optional.get();
        }
        //返回结果
        return null;
    }

    /**
     * 映射
     *
     * @param s           实体
     * @param temp
     * @param value       值
     * @param targetField 目录字段
     * @param <S>         参数类型
     * @throws Exception 异常信息
     */
    private static <S> void mapping(S s, Field temp, Object value, Field targetField) throws Exception {
        //目标字段名字
        String targetFieldName = targetField.getName();
        String fieldName = "set" + targetFieldName.substring(0, 1).toUpperCase() + targetFieldName.substring(1);
        //获取数据类型
        String dataType = targetField.getType().getName();

        //判断属性是不是 非值类型集合
        if (validateListElementType(temp)) {
            List values = (ArrayList) value;
            //获取目标字段对象
            String paramClassPath = null;
            Type type = ReflectionUtil.getDeclaredField(s, targetFieldName).getGenericType();
            if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
                paramClassPath = ((ParameterizedType) type).getActualTypeArguments()[0].getTypeName();
            }
            Class paramClazz = Class.forName(paramClassPath);
            List params = new ArrayList<>();
            for (Object g : values) {
                params.add(entityToEntity(g, paramClazz));
            }
            ReflectionUtil.invokeMethod(s, fieldName, new Class[]{targetField.getType()}, new Object[]{params}, dataType);
        } else {
            ReflectionUtil.invokeMethod(s, fieldName, new Class[]{targetField.getType()}, new Object[]{value}, dataType);
        }
    }

    /**
     * 验证修饰符
     *
     * @param field 字段
     * @return 返回布尔值结果
     */
    private static boolean validateModification(Field field) {
        //跳过字段的修饰符Final，Static
        int modifiers = field.getModifiers();

        if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
            return true;
        }
        return false;
    }

    /**
     * 验证集合元素类型
     *
     * @param field 字段
     * @return 返回布尔值结果
     */
    private static boolean validateListElementType(Field field) {
        if (field.getGenericType() instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) field.getGenericType();
            //判断具体类的类型是不是值类型和类值引用类型
            if (pt.getRawType().equals(List.class)) {
                Type[] types = pt.getActualTypeArguments();

                String packageName = types[0].getTypeName();
                return !validatePackageType(packageName);
            }
        }
        return false;
    }

    /**
     * 验证包类型
     *
     * @param packageName 包名
     * @return 返回结果
     */
    private static boolean validatePackageType(String packageName) {
        String pattern = "java.lang.*";
        return Pattern.matches(pattern, packageName);
    }

    /**
     * 获取字段
     *
     * @param fieldName 字段名称
     * @param lists     字段集
     * @return
     */
    private static Field getBusFieldByLists(String fieldName, List<Field> lists) {
        for (Field temp : lists) {
            if (temp.getName().equals(fieldName)) {
                return temp;
            }
        }
        return null;
    }
}
