package com.cdkjframework.constant;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.consts
 * @ClassName: Application
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class Application implements ApplicationContextAware {

    /**
     * 应用程序上下文
     */
    public static ApplicationContext applicationContext;

    /**
     * 批注元数据
     */
    public static AnnotationMetadata annotationMetadata;

    /**
     * Spring Boot applicationContext 设置
     *
     * @param appContext 参数
     * @throws BeansException 异常信息
     */
    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext = appContext;
    }
}
