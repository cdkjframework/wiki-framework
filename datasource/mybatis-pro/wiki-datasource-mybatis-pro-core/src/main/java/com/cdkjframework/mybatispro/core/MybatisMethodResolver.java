package com.cdkjframework.mybatispro.core;

import com.cdkjframework.mybatispro.core.mapper.MybatisMapperAnnotationBuilder;
import org.apache.ibatis.builder.annotation.MethodResolver;

import java.lang.reflect.Method;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core
 * @ClassName: MybatisMethodResolver
 * @Description: Mybatis 方法解析器
 * @Author: xiaLin
 * @Version: 1.0
 */
public class MybatisMethodResolver extends MethodResolver {

  /**
   * Mybatis映射器注释生成器
   */
  private final MybatisMapperAnnotationBuilder annotationBuilder;

  /**
   * 方法
   */
  private final Method method;

  /**
   * 构造方法
   *
   * @param annotationBuilder Mybatis映射器注释生成器
   * @param method            方法
   */
  public MybatisMethodResolver(MybatisMapperAnnotationBuilder annotationBuilder, Method method) {
    super(null, null);
    this.annotationBuilder = annotationBuilder;
    this.method = method;
  }

  /**
   * 解析方法
   */
  @Override
  public void resolve() {
    annotationBuilder.parseStatement(method);
  }
}
