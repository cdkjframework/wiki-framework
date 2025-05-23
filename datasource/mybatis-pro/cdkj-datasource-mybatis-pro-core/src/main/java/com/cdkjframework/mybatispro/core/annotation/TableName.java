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
 * 数据库表相关
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface TableName {

    /**
     * 实体对应的表名
     */
    String value() default "";

    /**
     * schema
     * <p>
     * 配置此值将覆盖全局配置的 schema
     *
     */
    String schema() default "";

    /**
     * 是否保持使用全局的 tablePrefix 的值
     * <p> 只生效于 既设置了全局的 tablePrefix 也设置了上面 {@link #value()} 的值 </p>
     * <li> 如果是 false , 全局的 tablePrefix 不生效 </li>
     *
     */
    boolean keepGlobalPrefix() default false;

    /**
     * 实体映射结果集,
     * 只生效于 mp 自动注入的 method
     */
    String resultMap() default "";

    /**
     * 是否自动构建 resultMap 并使用,
     * 只生效于 mp 自动注入的 method,
     * 如果设置 resultMap 则不会进行 resultMap 的自动构建并注入,
     * 只适合个别字段 设置了 typeHandler 或 jdbcType 的情况
     *
     */
    boolean autoResultMap() default false;

    /**
     * 只需要的属性名
     * <p>
     * 与{@link #excludeProperty()} 二选一配置,都配置了则只有此配置生效
     *
     */
    String[] properties() default {};

    /**
     * 需要排除的属性名
     * <p>
     * 与{@link #properties()} 二选一配置,都配置了则{@link #properties()} 配置生效
     *
     */
    String[] excludeProperty() default {};
}
