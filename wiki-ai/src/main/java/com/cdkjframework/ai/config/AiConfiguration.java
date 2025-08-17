package com.cdkjframework.ai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.config
 * @ClassName: AiConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/7/20 22:01
 * @Version: 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "wiki.ai")
public class AiConfiguration {
}
