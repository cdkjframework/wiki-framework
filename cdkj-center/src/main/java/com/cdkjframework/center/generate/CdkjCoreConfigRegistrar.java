package com.cdkjframework.center.generate;

import com.cdkjframework.center.annotation.EnableAutoGenerate;
import com.cdkjframework.constant.Application;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.meta.BeanRegistrationUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.center.generate
 * @ClassName: CdkjCoreConfigRegistrar
 * @Description: 自动代码启用注入
 * @Author: xiaLin
 * @Version: 1.0
 */

public class CdkjCoreConfigRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * bean 注册定义
     *
     * @param annotationMetadata 批注元数据
     * @param registry           bean 定义注册表
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        Application.annotationMetadata = annotationMetadata;
        Map<String, Object> map = annotationMetadata.getAnnotationAttributes(EnableAutoGenerate.class.getName());
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(map);

        Map<String, Object> propertySourcesPlaceholderPropertyValues = new HashMap<>(IntegerConsts.ONE);
        propertySourcesPlaceholderPropertyValues.put("order", IntegerConsts.ZERO);

        BeanRegistrationUtils.registerBeanDefinitionIfNotExists(registry, Application.class.getName(), Application.class);
    }
}
