package com.cdkjframework.redis.connectivity;

import com.cdkjframework.redis.realize.SearchCommands;
import com.cdkjframework.util.log.LogUtils;
import com.redislabs.lettusearch.RediSearchClient;
import com.redislabs.lettusearch.RediSearchCommands;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.redis
 * @ClassName: RedisConfiguration
 * @Description: Redis 缓存工具配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@Component
@AutoConfigureOrder(value = 4)
public class RedisSearchConfiguration extends BaseRedisConfiguration {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(RedisSearchConfiguration.class);

    /**
     * redis搜索
     *
     * @return 返回结果
     */
    @Bean(name = "redisSearchCommands")
    public RediSearchCommands<String, String> redisSearchCommands() {
        if (!redisConfig.isSearch()) {
            return new SearchCommands();
        }

        RediSearchClient searchClient = RediSearchClient.create();
        return new SearchCommands();
    }

}
