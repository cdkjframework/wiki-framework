package com.cdkj.framework.core.annotation;


import com.cdkj.framework.enums.ERegexType;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2017/7/4.
 * 数据验证注解
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {

    /**
     * 是否为空
     *
     * @return
     */
    boolean notEmpty() default false;

    /**
     * 最少长度
     *
     * @return
     */
    int minLength() default 0;

    /**
     * 最大长度
     *
     * @return
     */
    int maxLength() default 0;

    /**
     * 提供几种常用的正则验证
     *
     * @return
     */
    ERegexType regexType() default ERegexType.NONE;

    /**
     * 自定义正则验证
     *
     * @return
     */
    String regexExpression() default "";

    /**
     * 参数或者字段描述,这样能够显示友好的异常信息
     *
     * @return
     */
    String description() default "";

    /**
     * 时间格式化
     *
     * @return
     */
    String dateFormat() default "yyyy-MM-dd HH:mm:ss";

}