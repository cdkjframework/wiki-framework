package com.cdkjframework.datasource.elasticsearch.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.database.non.elasticsearch.config
 * @ClassName: ElasticSearchReadConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.cdkj.datasource.elasticsearch")
public class ElasticSearchConfig {

    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * 簇节点
     */
    private String clusterNodes;
}
