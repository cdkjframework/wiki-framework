package com.cdkjframework.mybatispro.core.toolkit.reflect;

import com.cdkjframework.mybatispro.core.toolkit.ClassUtils;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.toolkit
 * @ClassName: GenericTypeUtils
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public class GenericTypeUtils {


  /**
   * 能否加载SpringCore包
   *
   * @since 3.5.4
   */
  private static boolean loadSpringCore = false;

  static {
    try {
      ClassUtils.toClassConfident("org.springframework.core.GenericTypeResolver");
      loadSpringCore = true;
    } catch (Exception exception) {
      // ignore
    }
  }

  private static IGenericTypeResolver GENERIC_TYPE_RESOLVER;

  /**
   * 获取泛型工具助手
   */
  public static Class<?>[] resolveTypeArguments(final Class<?> clazz, final Class<?> genericIfc) {
    if (null == GENERIC_TYPE_RESOLVER) {
      // 直接使用 spring 静态方法，减少对象创建
      return SpringReflectionHelper.resolveTypeArguments(clazz, genericIfc);
    }
    return GENERIC_TYPE_RESOLVER.resolveTypeArguments(clazz, genericIfc);
  }

  /**
   * 设置泛型工具助手。如果不想使用Spring封装，可以使用前替换掉
   */
  public static void setGenericTypeResolver(IGenericTypeResolver genericTypeResolver) {
    GENERIC_TYPE_RESOLVER = genericTypeResolver;
  }

  /**
   * 判断是否有自定泛型提取类或能否加载SpringCore进行泛型提取
   *
   * @return 是否有实现
   * @since 3.5.4
   */
  public static boolean hasGenericTypeResolver() {
    return GENERIC_TYPE_RESOLVER != null || loadSpringCore;
  }
}
