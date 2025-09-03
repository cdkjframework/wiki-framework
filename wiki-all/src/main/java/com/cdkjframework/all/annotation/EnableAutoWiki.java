package com.cdkjframework.all.annotation;

import com.cdkjframework.datasource.mybatis.annotation.EnableAutoMybatis;
import com.cdkjframework.redis.annotation.EnableAutoRedis;

import java.lang.annotation.*;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.all.annotation
 * @ClassName: EnableAutoWiki
 * @Description: 启用 WIKI 自动配置（Mybatis数据库连接）
 * @Author: xiaLin
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableAutoRedis
@EnableAutoMybatis
public @interface EnableAutoWiki {
}
