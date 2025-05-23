/*
 * Copyright (c) 2011-2024, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cdkjframework.mybatispro.core.annotation;


import java.lang.annotation.*;

/**
 * 序列主键策略
 * <p>oracle</p>
 *
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface KeySequence {

    /**
     * 序列名
     */
    String value() default "";

    /**
     * 数据库类型，未配置默认使用注入 IKeyGenerator 实现，多个实现必须指定
     */
    DbType dbType() default DbType.OTHER;
}
