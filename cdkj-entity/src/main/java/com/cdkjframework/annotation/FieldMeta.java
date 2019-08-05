package com.cdkjframework.annotation;

import java.lang.annotation.*;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.util.files.excel
 * @ClassName: FileMeta
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Retention(RetentionPolicy.RUNTIME)
//定义注解的作用目标**作用范围字段、枚举的常量/方法
@Target({ElementType.FIELD, ElementType.METHOD})
//说明该注解将被包含在javadoc中
@Documented
public @interface FieldMeta {

    /**
     * 字段名称
     *
     * @return 名称
     */
    String name() default "";

    /**
     * 字段描述
     *
     * @return 返回字符串
     */
    String description() default "";

    /**
     * 是否在列表中显示
     *
     * @return 返回布尔值
     */
    boolean visible() default true;

    /**
     * 排序字段
     *
     * @return 返回顺序
     */
    int order() default 0;

    /**
     * 时间 格式化
     *
     * @return 对时间进行格式
     */
    String dateFormatter() default "";
}