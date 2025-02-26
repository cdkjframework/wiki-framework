package com.cdkjframework.mybatispro.core.toolkit.reflect;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.toolkit.reflect
 * @ClassName: IGenericTypeResolver
 * @Description: 泛型类助手（用于隔离Spring的代码）
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface IGenericTypeResolver {

  /**
   * 获取泛型参数类型
   *
   * @param clazz      泛型类
   * @param genericIfc 泛型接口
   * @return 泛型参数类型
   */
  Class<?>[] resolveTypeArguments(final Class<?> clazz, final Class<?> genericIfc);
}
