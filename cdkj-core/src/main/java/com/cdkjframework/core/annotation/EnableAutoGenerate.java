package com.cdkjframework.core.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.annotation
 * @ClassName: AutoGenerate
 * @Description: 自动生成工具
 * @Author: xiaLin
 * @Version: 1.0
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Import({CdkjCoreConfigRegistrar.class})
public @interface EnableAutoGenerate {

    /**
     * 是否自动根据实体修改数据
     *
     * @return 返回布尔值
     */
    boolean update() default false;

    /**
     * 是否生成JAP模板
     *
     * @return 返回结果
     */
    boolean jpa() default false;

    /**
     * 对接查询那些表
     *
     * @return 返回结果
     */
    String[] notTables() default {};

    /**
     * 目录结构
     *
     * @return 返回结果集
     */
    String basePackage() default "com.cdkjframework";

    /**
     * 项目名称
     *
     * @return 返回结果
     */
    String projectName() default "com.cdkjframework";
}