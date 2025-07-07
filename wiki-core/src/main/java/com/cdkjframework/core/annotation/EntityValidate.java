package com.cdkjframework.core.annotation;

import com.cdkjframework.enums.AnnotationEnums;

import java.lang.annotation.*;

/**
 * @ProjectName: hongtu.slps.bms
 * @Package: com.cdkjframework.core.annotation
 * @ClassName: EntityVerify
 * @Description: 实体验证
 * @Author: xiaLin
 * @Version: 1.0
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityValidate {

    /**
     * 是否验证
     *
     * @return 返回结果
     */
    boolean validate() default true;

    /**
     * 是否可为空
     *
     * @return 返回结果
     */
    boolean isEmpty() default true;

    /**
     * 数据类型
     *
     * @return 返回结果
     */
    AnnotationEnums dataType() default AnnotationEnums.String;

    /**
     * 提示消息
     *
     * @return 返回结果
     */
    String message() default "验证未通过";
}
