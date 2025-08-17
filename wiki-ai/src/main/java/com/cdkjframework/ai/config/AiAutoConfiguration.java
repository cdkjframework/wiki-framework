package com.cdkjframework.ai.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.config
 * @ClassName: AiAutoConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/7/20 22:03
 * @Version: 1.0
 */
@Lazy(false)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({
        AiConfiguration.class
})
//@ImportAutoConfiguration(value = {MybatisDruidDbConfiguration.class})
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ConditionalOnBean(AiMarkerConfiguration.Marker.class)
public class AiAutoConfiguration {
}
