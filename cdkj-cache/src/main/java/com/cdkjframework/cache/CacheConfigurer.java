package com.cdkjframework.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.cache
 * @ClassName: CacheConfigurer
 * @Description: java类作用描述
// 实现CachingConfigurer，然后注入需要的cacheManager和keyGenerator；
//从spring4开始默认的keyGenerator是SimpleKeyGenerator
// 使用@EnableCaching启用Cache注解支持
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@EnableCaching(proxyTargetClass = true)
public class CacheConfigurer implements CachingConfigurer {
    /**
     *
     * @return
     */
    @Override
    public CacheManager cacheManager() {
//        try {
//            RedisCacheManagerBuilder
//            net.sf.ehcache.CacheManager ehcacheCacheManager
//                    = new net.sf.ehcache.CacheManager(new ClassPathResource("ehcache.xml").getInputStream());
//
//            EhCacheCacheManager cacheCacheManager = new EhCacheCacheManager(ehcacheCacheManager);
//            return cacheCacheManager;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    /**
     *
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }
}
