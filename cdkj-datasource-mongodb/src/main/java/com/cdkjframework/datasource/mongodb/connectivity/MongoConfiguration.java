package com.cdkjframework.datasource.mongodb.connectivity;

import com.cdkjframework.datasource.mongodb.config.MongoConfig;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterConnectionMode;
import com.mongodb.connection.ClusterType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.Arrays;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.database.non.mongodb.connectivity
 * @ClassName: MongoConfiguration
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
    private final MongoConfig mongodbConfig;

    /**
     * 构造函数
     *
     * @param mongodbConfig mongo配置
     */
    public MongoConfiguration(MongoConfig mongodbConfig) {
        this.mongodbConfig = mongodbConfig;
    }

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
    public MongoDatabaseFactory mongoDbFactory() {
        //客户端配置（连接数、副本集群验证）
        String uri = "mongodb://127.0.0.1:27017/admin";
        if (StringUtils.isNullAndSpaceOrEmpty(mongodbConfig.getUri())) {
            return new SimpleMongoClientDatabaseFactory(uri);
        }
        logUtil.info("mongodb 进入配置");
        MongoClient mongoClient = mongoClient();

        logUtil.info("mongodb 配置结束");
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongodbConfig.getDataSource());
    }

    /**
     * 客户端
     *
     * @return 客户端
     */
    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings.Builder builder = MongoClientSettings.builder();
        if (StringUtils.isNotNullAndEmpty(mongodbConfig.getUserName()) &&
                StringUtils.isNotNullAndEmpty(mongodbConfig.getPassword())) {
            MongoCredential credential = MongoCredential.createCredential(mongodbConfig.getUserName(), mongodbConfig.getDataSource(), mongodbConfig.getPassword().toCharArray());
            builder = builder.credential(credential);
        }

        MongoClientSettings setting = builder.applyToClusterSettings(cluster ->
                cluster.hosts(Arrays.asList(new ServerAddress(mongodbConfig.getUri(), mongodbConfig.getPort())))
                        .mode(ClusterConnectionMode.SINGLE)
                        .requiredClusterType(ClusterType.STANDALONE)
        ).build();
        // 返回结果
        return MongoClients.create(setting);
    }
}