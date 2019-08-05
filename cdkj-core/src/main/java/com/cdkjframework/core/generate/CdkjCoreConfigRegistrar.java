package com.cdkjframework.core.generate;

import com.cdkjframework.constant.Application;
import com.cdkjframework.core.annotation.EnableAutoGenerate;
import com.ctrip.framework.apollo.spring.util.BeanRegistrationUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.autogenerate
 * @ClassName: GenerateConfigRegistrar
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

        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, Application.class.getName(), Application.class);

    }
}
