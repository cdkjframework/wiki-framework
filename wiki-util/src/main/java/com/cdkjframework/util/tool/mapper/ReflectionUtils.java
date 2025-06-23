package com.cdkjframework.util.tool.mapper;

import com.cdkjframework.constant.DataTypeConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.util.tool
 * @ClassName: ReflectionUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class ReflectionUtils {

  /**
   * 日志
   */
  private static final LogUtils LOG = LogUtils.getLogger(ReflectionUtils.class);

  /**
   * 数据类型
   */
  private static final List<String> DATA_TYPE = List.of("java.util.ArrayList", "java.util.List");

  /**
   * 获取字段值
   *
   * @param field 字段
   * @param t     实体
   * @param <T>   实体类型
   * @return 返回结果值
   */
  public static <T> Object getFieldValue(Field field, T t) {
    Object value;
    try {
      //抑制Java对其的检查
      field.setAccessible(true);
      value = field.get(t);

      String typeName = field.getType().getTypeName();

      if (StringUtils.isNullAndSpaceOrEmpty(value)) {
        if (DATA_TYPE.contains(typeName)) {
          value = null;
        } else {
          value = StringUtils.EMPTY;
        }
      }
    } catch (IllegalAccessException e) {
      LOG.error(e.getMessage());
      value = StringUtils.EMPTY;
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
        LOG.error(e.getCause(), e.getMessage());
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
      //调用object 的 method 所代表的方法，其方法的参数是 parameters
      final List<String> dataTypeList = new ArrayList<>();
      dataTypeList.add("java.util.ArrayList");
      dataTypeList.add("java.util.List");
      if (dataTypeList.contains(dataType)) {
        return method.invoke(object, parameters);
      } else {
        Object value = convertDataTypes(parameters, dataType);
        return method.invoke(object, new Object[]{value});
      }
    } catch (IllegalArgumentException e) {
      LOG.info(dataType);
      LOG.error(e.getCause(), e.getMessage());
    } catch (IllegalAccessException | InvocationTargetException e) {
      LOG.error(e.getCause(), e.getMessage());
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
      LOG.error(e.getCause(), e.getMessage());
    }
    return field;
  }


  /**
   * 获取字段
   *
   * @param clazz     类型
   * @param fieldName 字段
   * @return
   */
  public static Field getDeclaredField(Class clazz, String fieldName) {
    Field field = null;
    List<Field> fieldList = getDeclaredFields(clazz);
    try {
      Optional<Field> optional = fieldList
          .stream()
          .filter(f -> fieldName.equals(f.getName()))
          .findFirst();
      if (optional.isPresent()) {
        field = optional.get();
      }
    } catch (Exception e) {
      LOG.error(e.getCause(), e.getMessage());
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
    List<Field> fields = new ArrayList<>();
    Class<?> t = clazz;
    while (t != null) {
      fields.addAll(Arrays.asList(t.getDeclaredFields()));
      t = t.getSuperclass();
    }
    return fields;
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
      LOG.error(e.getCause(), e.getMessage());
    } catch (IllegalAccessException e) {
      LOG.error(e.getCause(), e.getMessage());
    }

  }

  /**
   * 设置字段值
   *
   * @param target 对像值
   * @param field  字段
   * @param value  值
   */
  public static <T> void setFieldValue(T target, Field field, Object value) {
    //抑制Java对其的检查
    field.setAccessible(true);
    try {
      //将 target 中 field 所代表的值 设置为 value
      field.set(target, value);
    } catch (IllegalArgumentException e) {
      LOG.error(e.getCause(), e.getMessage());
    } catch (IllegalAccessException e) {
      LOG.error(e.getCause(), e.getMessage());
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
    if (field == null) {
      return null;
    }
    //抑制Java对其的检查
    field.setAccessible(true);
    try {
      //获的属性值
      return field.get(object);
    } catch (Exception e) {
      LOG.error(e.getCause(), e.getMessage());
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
  private static Object convertDataTypes(Object[] parameters, String dataType) {
    Object obj = null;
    if (parameters.length == 0 || StringUtils.isNullAndSpaceOrEmpty(parameters[0])) {
      return obj;
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String value = parameters[0].toString();
    DateFormat format = DateFormat.getDateInstance();
    switch (dataType) {
      case DataTypeConsts.BOOLEAN_NAME:
      case DataTypeConsts
          .BIG_BOOLEAN_NAME:
        obj = StringUtils.convertBoolean(value);
        break;
      case DataTypeConsts.BIG_DECIMAL_NAME:
        obj = BigDecimal.valueOf(Float.valueOf(value));
        break;
      case DataTypeConsts.DATE_NAME:
        final String cst = "CST";
        if (value.contains(cst)) {
          obj = format.format(value);
        } else {
          try {
            obj = dateFormat.parse(value);
          } catch (ParseException e) {
            LOG.error(DataTypeConsts.DATE_NAME + "：" + value);
            LOG.error(e.getCause(), e.getMessage());
          }
        }
        break;
      case DataTypeConsts.INT_NAME:
      case DataTypeConsts.INTEGER_NAME:
        obj = Integer.valueOf(value);
        break;
      case DataTypeConsts.JAVA_SQL_TIMESTAMP:
        Long timestamp = StringUtils.convertLong(value);
        if (timestamp > 0) {
          obj = new Timestamp(Long.valueOf(value));
        } else {
          try {
            obj = new Timestamp(format.parse(value).getTime());
          } catch (ParseException e) {
            LOG.error(e.getCause(), e.getMessage());
          }
        }
        break;
      case DataTypeConsts.SQL_DATE_NAME:
        obj = new java.sql.Date(Long.valueOf(value));
        break;
      case DataTypeConsts.STRING_NAME:
        obj = String.valueOf(value);
        break;
      case DataTypeConsts.DOUBLE_NAME:
      case DataTypeConsts.BIG_DOUBLE_NAME:
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
