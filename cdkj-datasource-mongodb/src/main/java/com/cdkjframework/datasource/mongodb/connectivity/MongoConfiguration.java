package com.cdkjframework.datasource.mongodb.connectivity;

import com.cdkjframework.datasource.mongodb.config.MongoConfig;
import com.cdkjframework.enums.datasource.ApolloMongoEnums;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.mapper.MapperUtils;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.database.non.mongodb.connectivity
 * @ClassName: MongoConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
public class MongoConfiguration {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(MongoConfiguration.class);

    /**
     * 读取配置文件配置
     */
    @Autowired
    private MongoConfig mongodbConfig;

    /**
     * apollo配置
     */
    @ApolloConfig("cdkj.mongodb")
    private Config apolloConfig;

    /**
     * mongo模板
     *
     * @return 返回结果
     */
    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }

    /**
     * 覆盖默认的MongoDbFactory
     *
     * @return 返回结果
     */
    @Bean
    public MongoDbFactory mongoDbFactory() {
        //客户端配置（连接数、副本集群验证）
        Boolean isConfig = apolloConfig == null || apolloConfig.getPropertyNames().size() == 0;
        MongoClientOptions.Builder mongoBuilder = new MongoClientOptions.Builder();
        if (isConfig && StringUtils.isNullAndSpaceOrEmpty(mongodbConfig.getUri())) {
            return new SimpleMongoDbFactory(new MongoClientURI("mongodb://127.0.0.1:27017/admin", mongoBuilder));
        }
        if (apolloConfig != null && StringUtils.isNullAndSpaceOrEmpty(mongodbConfig.getUri())) {
            setConfiguration();
        }
        logUtil.info("mongodb 进入配置");
        mongoBuilder.maxWaitTime(mongodbConfig.getMaxWaitTime());
        mongoBuilder.connectTimeout(mongodbConfig.getConnectTimeout());
        if (mongodbConfig.getMaxConnectionsPerHost() > mongodbConfig.getMinConnectionsPerHost() &&
                mongodbConfig.getMaxConnectionsPerHost() > 0) {
            mongoBuilder.minConnectionsPerHost(mongodbConfig.getMinConnectionsPerHost());
            mongoBuilder.connectionsPerHost(mongodbConfig.getMaxConnectionsPerHost());
        }
        String uri;
        if (StringUtils.isNullAndSpaceOrEmpty(mongodbConfig.getUserName()) ||
                StringUtils.isNullAndSpaceOrEmpty(mongodbConfig.getPassword())) {
            uri = String.format("mongodb://%s:%s/%s", mongodbConfig.getUri(), mongodbConfig.getPort(), mongodbConfig.getDataSource());
        } else {
            uri = String.format("mongodb://%s:%s@%s:%s/%s", mongodbConfig.getUserName(), mongodbConfig.getPassword(), mongodbConfig.getUri(), mongodbConfig.getPort(), mongodbConfig.getDataSource());
        }
        MongoClientURI mongoClientUrl = new MongoClientURI(uri, mongoBuilder);
        SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClientUrl);

        logUtil.info("mongodb 配置结束");
        return mongoDbFactory;
    }

    /**
     * 设置配置信息
     */
    private void setConfiguration() {
        try {
            mongodbConfig = MapperUtils.apolloToEntity(apolloConfig, ApolloMongoEnums.values(), MongoConfig.class);
        } catch (IllegalAccessException ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
        } catch (InstantiationException ex) {
            logUtil.error(ex.getCause(), ex.getMessage());
        }
    }
}
