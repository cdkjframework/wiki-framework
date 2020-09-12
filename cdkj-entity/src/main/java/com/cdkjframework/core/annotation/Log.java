package com.cdkjframework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.log
 * @ClassName: Log
 * @Description: 日志
 * @Author: xiaLin
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {

    /**
     * 模块名称
     *
     * @return 返回结果
     */
    String name() default "";

    /**
     * 模块
     *
     * @return 返回结果
     */
    String module() default "";
}
