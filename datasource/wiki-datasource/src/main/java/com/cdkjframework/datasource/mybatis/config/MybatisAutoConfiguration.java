package com.cdkjframework.datasource.mybatis.config;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.config.DataSourceConfig;
import com.cdkjframework.datasource.mybatis.aspect.DataSourceAspect;
import com.cdkjframework.datasource.mybatis.connectivity.MapperPrimaryScannerConfiguration;
import com.cdkjframework.datasource.mybatis.connectivity.MapperScannerConfiguration;
import com.cdkjframework.datasource.mybatis.connectivity.MybatisConfiguration;
import com.cdkjframework.datasource.mybatis.connectivity.MybatisDruidDbConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.datasource.mybatis.config
 * @ClassName: MybatisAutoConfiguration
 * @Description: Mybatis 自动配置类
 * @Author: xiaLin
 * @Date: 2023/12/5 17:29
 * @Version: 1.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({
    CustomConfig.class,
    MybatisConfig.class,
    DataSourceConfig.class
})
@Import({
    MybatisDruidDbConfiguration.class,
    MybatisConfiguration.class,
    MapperPrimaryScannerConfiguration.class,
    MapperScannerConfiguration.class,
    DataSourceAspect.class
})
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ConditionalOnBean(MybatisMarkerConfiguration.Marker.class)
public class MybatisAutoConfiguration {
}
