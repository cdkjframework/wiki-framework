package com.cdkjframework.datasource.non.elasticsearch.connectivity;

import com.cdkjframework.datasource.non.elasticsearch.config.ElasticSearchConfig;
import com.cdkjframework.util.log.LogUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.database.non.elasticsearch.connectivity
 * @ClassName: ElasticSearchConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class ElasticsearchConfiguration implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {


    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger("ElasticsearchConfiguration");

    /**
     * ES 配置
     */
    private ElasticSearchConfig elasticSearchConfig;

    /**
     * 传输客户端
     */
    private TransportClient client;

    @Override
    public void destroy() throws Exception {
        try {
            logUtil.info("Closing elasticSearch client");
            if (client != null) {
                client.close();
            }
        } catch (final Exception e) {
            logUtil.error(e, "Error closing ElasticSearch client: ");
        }
    }

    @Override
    public TransportClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<TransportClient> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildClient();
    }

    /**
     * 建立客户端
     */
    private void buildClient() {
        try {
            final String splitCharacter = ",";
            final String regexCharacter = ":";
            PreBuiltTransportClient preBuiltTransportClient = new PreBuiltTransportClient(settings());
            if (!"".equals(elasticSearchConfig.getClusterNodes())) {
                for (String nodes : elasticSearchConfig.getClusterNodes().split(splitCharacter)) {
                    String InetSocket[] = nodes.split(regexCharacter);
                    String Address = InetSocket[0];
                    Integer port = Integer.valueOf(InetSocket[1]);
                    preBuiltTransportClient.addTransportAddress(new
                            TransportAddress(InetAddress.getByName(Address), port));
                }
                client = preBuiltTransportClient;
            }
        } catch (UnknownHostException e) {
            logUtil.error(e.getMessage());
        }
    }

    /**
     * 初始化默认的client
     */
    private Settings settings() {
        Settings settings = Settings.builder()
                .put("cluster.name", elasticSearchConfig.getClusterName())
                .put("client.transport.sniff", true)
                .build();
        client = new PreBuiltTransportClient(settings);

        //返回结果
        return settings;
    }
}
