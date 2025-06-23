package com.cdkjframework.center.annotation;

import com.cdkjframework.center.generate.CdkjCoreConfigRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.annotation
 * @ClassName: AutoGenerate
 * @Description: 自动生成工具
 * @Author: xiaLin
 * @Version: 1.0
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CdkjCoreConfigRegistrar.class})
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
     * 是否生成 myBatis 模板
     *
     * @return 返回结果
     */
    boolean myBatis() default true;

    /**
     * 对接查询那些表
     *
     * @return 返回结果
     */
    String[] notTables() default {};

    /**
     * 根目录
     *
     * @return 返回结果
     */
    String basePath() default "";

    /**
     * 路径 (jpa 为:1、2、3、4、5、8，myBatis 为:1、2、3、4、5、6、7)
     * 当 jpa myBatis 都为 true 是则全部为必填写
     * 当 jpa 为 true 长度为 6，vo、dto、entity、controller、service、repository
     * 当 myBatis 为 true 长度为 7，vo、dto、entity、controller、service、mapper、mapperXml
     * (1) vo
     * (2) dto
     * (3) entity
     * (4) controller
     * (5) service
     * (6) mapper
     * (7) mapperXml
     * (8) repository
     *
     * @return 返回路径
     */
    String[] path() default "";

    /**
     * 目录结构
     *
     * @return 返回结果集
     */
    String basePackage() default "com.framewiki";

    /**
     * 项目名称
     *
     * @return 返回结果
     */
    String projectName() default "com.framewiki";
}
