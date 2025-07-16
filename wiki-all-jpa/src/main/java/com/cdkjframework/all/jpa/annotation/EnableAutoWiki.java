package com.cdkjframework.all.jpa.annotation;

import com.cdkjframework.datasource.jpa.annotation.EnableAutoJpa;
import com.cdkjframework.redis.annotation.EnableAutoRedis;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.all.jpa.annotation
 * @ClassName: EnableAutoWiki
 * @Description: 启用 WIKI 自动配置（JPA数据库连接）
 * @Author: xiaLin
 * @Version: 1.0
 */
@EnableAutoJpa
@EnableAutoRedis
public @interface EnableAutoWiki {
}
