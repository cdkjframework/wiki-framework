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
 * 乐观锁注解
 * <p>
 * 支持的字段类型:
 * long,
 * Long,
 * int,
 * Integer,
 * java.util.Date,
 * java.sql.Timestamp,
 * java.time.LocalDateTime
 * java.time.Instant
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Version {
}
