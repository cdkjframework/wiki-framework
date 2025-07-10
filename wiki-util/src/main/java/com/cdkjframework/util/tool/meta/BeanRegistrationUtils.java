package com.cdkjframework.util.tool.meta;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.util.tool.meta
 * @ClassName: BeanRegistrationUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class BeanRegistrationUtils {

  /**
   * 不存在注册定义
   *
   * @param registry  Bean定义注册表
   * @param beanName  bean名称
   * @param beanClass bean类
   * @return 返回结果
   */
  public static boolean registerBeanDefinitionIfNotExists(BeanDefinitionRegistry registry, String beanName,
                                                          Class<?> beanClass) {
    return registerBeanDefinitionIfNotExists(registry, beanName, beanClass, null);
  }

  /**
   * 不存在注册定义
   *
   * @param registry            Bean定义注册表
   * @param beanName            bean名称
   * @param beanClass           bean类
   * @param extraPropertyValues 额外属性值
   * @return 返回结果
   */
  public static boolean registerBeanDefinitionIfNotExists(BeanDefinitionRegistry registry, String beanName,
                                                          Class<?> beanClass, Map<String, Object> extraPropertyValues) {
    if (registry.containsBeanDefinition(beanName)) {
      return false;
    }

    // 获取已注册服务
    String[] candidates = registry.getBeanDefinitionNames();
    for (String candidate : candidates) {
      BeanDefinition beanDefinition = registry.getBeanDefinition(candidate);
      if (Objects.equals(beanDefinition.getBeanClassName(), beanClass.getName())) {
        return false;
      }
    }

    BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(beanClass).getBeanDefinition();

    if (extraPropertyValues != null) {
      for (Map.Entry<String, Object> entry : extraPropertyValues.entrySet()) {
        beanDefinition.getPropertyValues().add(entry.getKey(), entry.getValue());
      }
    }

    registry.registerBeanDefinition(beanName, beanDefinition);

    return true;
  }
}
