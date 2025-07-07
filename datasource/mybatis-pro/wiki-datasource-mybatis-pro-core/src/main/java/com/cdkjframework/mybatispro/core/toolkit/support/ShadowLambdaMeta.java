package com.cdkjframework.mybatispro.core.toolkit.support;


import com.cdkjframework.mybatispro.core.toolkit.ClassUtils;
import com.cdkjframework.util.tool.StringUtils;

/**
 * 基于 {@link SerializedLambda} 创建的元信息
 * <p>
 * Create by hcl at 2021/7/7
 */
public class ShadowLambdaMeta implements LambdaMeta {
  private final SerializedLambda lambda;

  public ShadowLambdaMeta(SerializedLambda lambda) {
    this.lambda = lambda;
  }

  @Override
  public String getImplMethodName() {
    return lambda.getImplMethodName();
  }

  @Override
  public Class<?> getInstantiatedClass() {
    String instantiatedMethodType = lambda.getInstantiatedMethodType();
    String instantiatedType = instantiatedMethodType.substring(2, instantiatedMethodType.indexOf(StringUtils.SEMICOLON)).replace(StringUtils.SLASH, StringUtils.DOT);
    return ClassUtils.toClassConfident(instantiatedType, lambda.getCapturingClass().getClassLoader());
  }

}
