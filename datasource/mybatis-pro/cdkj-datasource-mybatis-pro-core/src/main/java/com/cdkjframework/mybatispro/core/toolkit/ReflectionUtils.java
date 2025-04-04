package com.cdkjframework.mybatispro.core.toolkit;

import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.mybatispro.core.toolkit.reflect.GenericTypeUtils;
import com.cdkjframework.mybatispro.core.toolkit.reflect.TypeParameterResolver;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.CollectUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.toolkit
 * @ClassName: ReflectionUtils
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public class ReflectionUtils {

  /**
   * class field cache
   */
  private static final Map<Class<?>, List<Field>> CLASS_FIELD_CACHE = new ConcurrentHashMap<>();

  @Deprecated
  private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new IdentityHashMap<>(8);

  private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_TO_WRAPPER_MAP = new IdentityHashMap<>(8);

  static {
    PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
    PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
    PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, char.class);
    PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, double.class);
    PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, float.class);
    PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, int.class);
    PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, long.class);
    PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, short.class);
    for (Map.Entry<Class<?>, Class<?>> entry : PRIMITIVE_WRAPPER_TYPE_MAP.entrySet()) {
      PRIMITIVE_TYPE_TO_WRAPPER_MAP.put(entry.getValue(), entry.getKey());
    }
  }

  /**
   * 获取字段值
   *
   * @param entity    实体
   * @param fieldName 字段名称
   * @return 属性值
   * @deprecated 3.5.4
   */
  @Deprecated
  public static Object getFieldValue(Object entity, String fieldName) {
    Class<?> cls = entity.getClass();
    Map<String, Field> fieldMaps = getFieldMap(cls);
    try {
      Field field = fieldMaps.get(fieldName);
      AssertUtils.isEmptyMessage(field, "Error: NoSuchField in %s for %s.  Cause:" + cls.getSimpleName() + fieldName);
      field.setAccessible(true);
      return field.get(entity);
    } catch (ReflectiveOperationException e) {
      throw new GlobalRuntimeException("Error: Cannot read field in %s.  Cause:", e);
    }
  }

  /**
   * <p>
   * 反射对象获取泛型
   * </p>
   *
   * @param clazz      对象
   * @param genericIfc 所属泛型父类
   * @param index      泛型所在位置
   * @return Class
   */
  public static Class<?> getSuperClassGenericType(final Class<?> clazz, final Class<?> genericIfc, final int index) {
    // 这里泛型逻辑提取进行了调整,如果在Spring项目情况或者自定义了泛型提取,那就优先走这里,否则使用框架内置的进行泛型提取.
    Class<?> userClass = ClassUtils.getUserClass(clazz);
    if (GenericTypeUtils.hasGenericTypeResolver()) {
      Class<?>[] typeArguments = GenericTypeUtils.resolveTypeArguments(userClass, genericIfc);
      return null == typeArguments ? null : typeArguments[index];
    }
    return (Class<?>) TypeParameterResolver.resolveClassIndexedParameter(userClass, genericIfc, index);
  }

  /**
   * <p>
   * 获取该类的所有属性列表
   * </p>
   *
   * @param clazz 反射类
   */
  public static Map<String, Field> getFieldMap(Class<?> clazz) {
    List<Field> fieldList = getFieldList(clazz);
    return CollectUtils.isNotEmpty(fieldList) ? fieldList.stream().collect(Collectors.toMap(Field::getName, Function.identity())) : Collections.emptyMap();
  }

  /**
   * <p>
   * 获取该类的所有属性列表
   * </p>
   *
   * @param clazz 反射类
   */
  public static List<Field> getFieldList(Class<?> clazz) {
    if (Objects.isNull(clazz)) {
      return Collections.emptyList();
    }
    return CollectUtils.computeIfAbsent(CLASS_FIELD_CACHE, clazz, k -> {
      Field[] fields = k.getDeclaredFields();
      List<Field> superFields = new ArrayList<>();
      Class<?> currentClass = k.getSuperclass();
      while (currentClass != null) {
        Field[] declaredFields = currentClass.getDeclaredFields();
        Collections.addAll(superFields, declaredFields);
        currentClass = currentClass.getSuperclass();
      }
      /* 排除重载属性 */
      Map<String, Field> fieldMap = excludeOverrideSuperField(fields, superFields);
      /*
       * 重写父类属性过滤后处理忽略部分，支持过滤父类属性功能
       * 场景：中间表不需要记录创建时间，忽略父类 createTime 公共属性
       * 中间表实体重写父类属性 ` private transient Date createTime; `
       */
      return fieldMap.values().stream()
          /* 过滤静态属性 */
          .filter(f -> !Modifier.isStatic(f.getModifiers()))
          /* 过滤 transient关键字修饰的属性 */
          .filter(f -> !Modifier.isTransient(f.getModifiers()))
          .collect(Collectors.toList());
    });
  }

  /**
   * <p>
   * 排序重置父类属性
   * </p>
   *
   * @param fields         子类属性
   * @param superFieldList 父类属性
   */
  public static Map<String, Field> excludeOverrideSuperField(Field[] fields, List<Field> superFieldList) {
    // 子类属性
    Map<String, Field> fieldMap = Stream.of(fields).collect(toMap(Field::getName, identity(),
        (u, v) -> {
          throw new IllegalStateException(String.format("Duplicate key %s", u));
        },
        LinkedHashMap::new));
    superFieldList.stream().filter(field -> !fieldMap.containsKey(field.getName()))
        .forEach(f -> fieldMap.put(f.getName(), f));
    return fieldMap;
  }

  /**
   * 判断是否为基本类型或基本包装类型
   *
   * @param clazz class
   * @return 是否基本类型或基本包装类型
   */
  @Deprecated
  public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
    AssertUtils.isEmptyMessage(clazz, "Class must not be null");
    return (clazz.isPrimitive() || PRIMITIVE_WRAPPER_TYPE_MAP.containsKey(clazz));
  }

  public static Class<?> resolvePrimitiveIfNecessary(Class<?> clazz) {
    return (clazz.isPrimitive() && clazz != void.class ? PRIMITIVE_TYPE_TO_WRAPPER_MAP.get(clazz) : clazz);
  }
}
