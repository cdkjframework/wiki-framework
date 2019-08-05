package com.cdkjframework.datasource.non.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.database.non.elasticsearch.config
 * @ClassName: ElasticSearchReadConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@ConfigurationProperties(prefix = "spring.datasource.elasticsearch")
public class ElasticSearchConfig {

    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * 簇节点
     */
    private String clusterNodes;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }
}
