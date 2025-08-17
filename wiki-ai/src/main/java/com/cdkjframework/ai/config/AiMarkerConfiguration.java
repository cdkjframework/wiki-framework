package com.cdkjframework.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.config
 * @ClassName: AiMarkerConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/7/20 22:04
 * @Version: 1.0
 */
@Configuration(proxyBeanMethods = false)
public class AiMarkerConfiguration {

    /**
     * Marker
     *
     * @return Marker
     */
    @Bean
    public Marker mybatisMarker() {
        return new Marker();
    }

    /**
     * Marker
     */
    public static class Marker {

    }
}
