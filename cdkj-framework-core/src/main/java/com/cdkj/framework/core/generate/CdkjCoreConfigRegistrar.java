package com.cdkj.framework.core.generate;

import com.cdkj.framework.consts.Application;
import com.cdkj.framework.core.annotation.EnableAutoGenerate;
import com.ctrip.framework.apollo.spring.util.BeanRegistrationUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.autogenerate
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
