package com.cdkj.framework.core.annotation;

import com.cdkj.framework.enums.AnnotationEnum;

import java.lang.annotation.*;

/**
 * @ProjectName: hongtu.slps.bms
 * @Package: com.cdkj.framework.core.annotation
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
    AnnotationEnum dataType() default AnnotationEnum.String;

    /**
     * 提示消息
     *
     * @return 返回结果
     */
    String message() default "验证未通过";
}
