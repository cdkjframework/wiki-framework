package com.cdkjframework.mybatispro.core.injector;

import com.cdkjframework.mybatispro.core.mapper.MybatisMapperAnnotationBuilder;
import org.apache.ibatis.builder.annotation.MethodResolver;

import java.lang.reflect.Method;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core
 * @ClassName: InjectorResolver
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public class InjectorResolver extends MethodResolver {

  /**
   * 注入器解析器
   */
  private final MybatisMapperAnnotationBuilder annotationBuilder;

  /**
   * 构造方法
   *
   * @param annotationBuilder 注入器解析器
   */
  public InjectorResolver(MybatisMapperAnnotationBuilder annotationBuilder) {
    super(annotationBuilder, null);
    this.annotationBuilder = annotationBuilder;
  }

  /**
   * 解析方法
   */
  @Override
  public void resolve() {
    annotationBuilder.parserInjector();
  }
}
