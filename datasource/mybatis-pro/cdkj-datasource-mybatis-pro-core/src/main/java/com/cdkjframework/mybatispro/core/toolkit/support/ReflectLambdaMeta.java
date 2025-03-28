package com.cdkjframework.mybatispro.core.toolkit.support;

import com.cdkjframework.mybatispro.core.toolkit.ClassUtils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by hcl at 2021/5/14
 */
@Slf4j
public class ReflectLambdaMeta implements LambdaMeta {
  private final SerializedLambda lambda;

  private final ClassLoader classLoader;

  public ReflectLambdaMeta(SerializedLambda lambda, ClassLoader classLoader) {
    this.lambda = lambda;
    this.classLoader = classLoader;
  }


  @Override
  public String getImplMethodName() {
    return lambda.getImplMethodName();
  }

  @Override
  public Class<?> getInstantiatedClass() {
    String instantiatedMethodType = lambda.getInstantiatedMethodType();
    String instantiatedType = instantiatedMethodType.substring(2, instantiatedMethodType.indexOf(StringUtils.SEMICOLON)).replace(StringUtils.SLASH, StringUtils.DOT);
    return ClassUtils.toClassConfident(instantiatedType, this.classLoader);
  }

}
