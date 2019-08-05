package com.cdkjframework.annotation;

import java.lang.annotation.*;

/**
 * @description 对象copy字段映射关系
 * @author zhanglei
 * @create 2017-07-23 19:48
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMapping {
    String name() default "";
}
