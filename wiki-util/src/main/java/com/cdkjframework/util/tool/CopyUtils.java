package com.cdkjframework.util.tool;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.mapper.ReflectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
	 * 数据类型
	 */
	private static final List<String> DATA_TYPE = List.of("java.util.ArrayList", "java.util.List");

	/**
	 * 类的类型
	 */
	private static final String CLASS_TYPE = "java";
	/**
	 * 日志
	 */
	private static LogUtils logUtil = LogUtils.getLogger(CopyUtils.class);

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
        try {
            for (PropertyDescriptor property : propertyList) {
                Object srcValue = wrapper.getPropertyValue(property.getName());
                if (srcValue == null) {
                    emptyNames.add(property.getName());
                }
            }
        } catch (Exception e) {

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
        List<T> result = new ArrayList();
        if (list == null) {
            return result;
        }
        for (S o : list) {
            try {
                T d = target.newInstance();
                copyProperties(o, d, false);
                result.add(d);
            } catch (Exception e) {
                logUtil.error(e.getMessage());
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
    @Deprecated
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
     * @param sourceList 原数据源
     * @param targetList 当前数据
     */
    public static <S, T> List<T> copyProperties(List<S> sourceList, Class<T> targetList) {
        List<T> result = new ArrayList();
        if (sourceList != null) {
            for (S o : sourceList) {
                try {
                  T d = targetList.getDeclaredConstructor().newInstance();
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
    public static <S, T> T copyProperties(S source, Class<T> target) {
        T t;
        try {
          if (source == null) {
            return null;
          }
          t = target.getDeclaredConstructor().newInstance();
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
     * @param sourceList 原数据源
     * @param targetList 当前数据
     */
    public static <S, T> List<T> copyNoNullProperties(List<S> sourceList, Class<T> targetList) {
        List<T> result = new ArrayList();
        if (sourceList == null) {
            return result;
        }
        for (S o : sourceList) {
            try {

              T d = targetList.getDeclaredConstructor().newInstance();
                copyProperties(o, d, true);
                result.add(d);
            } catch (Exception e) {
                logUtil.error(e.getMessage());
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
          if (source == null) {
            return null;
          }
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
            String[] propertyNames = getNullPropertyNames(source);
            if (isNull && propertyNames.length > IntegerConsts.ZERO) {
                BeanUtils.copyProperties(source, target, propertyNames);
            } else {
                BeanUtils.copyProperties(source, target);
            }
            List<Field> targetFields = ReflectionUtils.getDeclaredFields(target.getClass());
            List<Field> fields = ReflectionUtils.getDeclaredFields(source.getClass());
            for (Field targetField :
                    targetFields) {
							targetField.setAccessible(true);
							// 读取值
							Object value = ReflectionUtils.getFieldValue(targetField, target);
							String typeName = targetField.getType().getTypeName();
							if (DATA_TYPE.contains(typeName)) {
								buildArrayList((ArrayList) value, target, targetField);
							}
							if (StringUtils.isNotNullAndEmpty(value)) {
								continue;
							}
							// 验证是否有相同字段
							Optional<Field> optionalField = fields.stream()
									.filter(f -> f.getName().equals(targetField.getName()))
									.findFirst();
							if (!optionalField.isPresent()) {
								continue;
							}
							Field field = optionalField.get();
							field.setAccessible(true);
							// 读取值
							value = ReflectionUtils.getFieldValue(field, source);
							if (StringUtils.isNullAndSpaceOrEmpty(value)) {
								continue;
							}
							typeName = value.getClass().getTypeName();
							Object clazz;
							if (targetField.getType().equals(Integer.class)) {
								clazz = targetField.getType().getConstructor(int.class).newInstance(IntegerConsts.ZERO);
							} else if ((targetField.getType().equals(LocalDateTime.class))) {
								clazz = LocalDateTime.parse(String.valueOf(value));
							} else if ((targetField.getType().equals(LocalDate.class))) {
								clazz = LocalDate.parse(String.valueOf(value));
							} else if (targetField.getType().equals(BigDecimal.class)) {
								clazz = BigDecimal.valueOf(Double.valueOf(String.valueOf(value)));
							} else if (DATA_TYPE.contains(typeName)) {
								buildArrayList((ArrayList) value, target, targetField);
								continue;
							} else {
								clazz = targetField.getType().getDeclaredConstructor().newInstance();
							}
							if (!clazz.getClass().getName().startsWith(CLASS_TYPE)) {
								copyProperties(value, clazz);
							}
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
    private static <T> void buildArrayList(ArrayList arrayList, T target, Field targetField) throws IllegalAccessException, InstantiationException {
        Class clazz = (Class) ((ParameterizedType) targetField.getGenericType()).getActualTypeArguments()[0];
        if (clazz.getTypeName().contains(CLASS_TYPE)) {
            return;
        }
        List list = new ArrayList();
        for (Object obj :
                arrayList) {
          Object objClazz = null;
          try {
            objClazz = clazz.getDeclaredConstructor().newInstance();
          } catch (InvocationTargetException ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
          } catch (NoSuchMethodException ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
          }
          copyProperties(obj, objClazz);
          list.add(objClazz);
        }
        ReflectionUtils.setFieldValue(target, targetField, list);
    }
}
