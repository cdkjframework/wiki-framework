package com.cdkjframework.redisjson.connectivity;

import com.cdkjframework.redisjson.config.RedisJsonConfig;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.redisjson.connectivity
 * @ClassName: RedisJsonConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@Component
public class RedisJsonConfiguration implements ApplicationRunner {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(RedisJsonConfiguration.class);

  /**
     * 构造函数
     */
    public RedisJsonConfiguration(RedisJsonConfig redisJsonConfig) {
      /**
       * 配置
       */
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        HostAndPort config = new HostAndPort(redisJsonConfig.getHost(), redisJsonConfig.getPort());
//        JReJSON client= new JReJSON(config);

//        PooledJedisConnectionProvider provider = new PooledJedisConnectionProvider(config);
//        UnifiedJedis client = new UnifiedJedis(provider);
    }
}
