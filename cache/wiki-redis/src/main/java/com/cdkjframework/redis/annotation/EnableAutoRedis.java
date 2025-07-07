package com.cdkjframework.redis.annotation;

import com.cdkjframework.redis.config.RedisMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.redis.annotation
 * @ClassName: EnableAutoRedis
 * @Author: xiaLin
 * @Description: Java 类说明
 * @Date: 2024/6/6 19:53
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisMarkerConfiguration.class})
public @interface EnableAutoRedis {
}
