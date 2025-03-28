package com.cdkjframework.datasource.mybatispro.config;

import com.cdkjframework.config.DataSourceConfig;
import com.cdkjframework.datasource.mybatispro.connectivity.MybatisHikariCPConfiguration;
import com.cdkjframework.datasource.mybatispro.connectivity.MybatisProConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.datasource.mybatispro.config
 * @ClassName: MybatisProAutoConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/2/6 17:18
 * @Version: 1.0
 */
@Lazy(false)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({
        MybatisProConfig.class,
        DataSourceConfig.class
})
@ImportAutoConfiguration(value = {MybatisHikariCPConfiguration.class})
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ConditionalOnBean(MybatisMarkerConfiguration.Marker.class)
public class MybatisProAutoConfiguration implements InitializingBean {

    /**
     * MybatisPro 配置
     */
    private final MybatisProConfig mybatisProConfig;

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
