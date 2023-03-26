package com.cdkjframework.datasource.mongodb.connectivity;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.datasource.mongodb.config.MongoConfig;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterConnectionMode;
import com.mongodb.connection.ClusterType;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MongoConfiguration {

    /**
     * 读取配置文件配置
     */
    private final MongoConfig mongodbConfig;
    /**
     * 自定义配置
     */
    private final CustomConfig customConfig;

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(MongoConfiguration.class);

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
        MongoClient mongoClient;
        // 是否为集群
        if (mongodbConfig.isCluster()) {
            mongoClient = mongoDbClusterFactory();
        } else {
            mongoClient = mongoClient();
        }

        logUtil.info("mongodb 配置结束");
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongodbConfig.getDataSource());
    }

    /**
     * 构建集群连接
     *
     * @return 返回mongo客户端
     */
    public MongoClient mongoDbClusterFactory() {
        String uri;
        if (StringUtils.isNotNullAndEmpty(mongodbConfig.getPassword()) &&
                StringUtils.isNotNullAndEmpty(mongodbConfig.getUserName())) {

            if (mongodbConfig.isEncryption()) {
                AesUtils aes = new AesUtils(customConfig);
                uri = String.format("mongodb://%s:%s@%s:%d/%s",
                        AesUtils.base64Decrypt(mongodbConfig.getUserName()),
                        AesUtils.base64Decrypt(mongodbConfig.getPassword()),
                        AesUtils.base64Decrypt(mongodbConfig.getUri()),
                        mongodbConfig.getPort(), mongodbConfig.getAdminSource());
            } else {
                uri = String.format("mongodb://%s:%s@%s:%d/%s",
                        mongodbConfig.getUserName(), mongodbConfig.getPassword(), mongodbConfig.getUri(),
                        mongodbConfig.getPort(), mongodbConfig.getAdminSource());
            }
        } else {
            uri = String.format("mongodb://%s:%d/%s", mongodbConfig.getUri(),
                    mongodbConfig.getPort(), mongodbConfig.getDataSource());
        }
        //客户端配置（连接数、副本集群验证）
        MongoClientSettings.Builder builder = MongoClientSettings.builder();
        ClusterType clusterType = mongodbConfig.isSharded() ? ClusterType.SHARDED : ClusterType.REPLICA_SET;
        MongoClientSettings setting = builder.applyToClusterSettings(cluster -> {
            ConnectionString connectionString = new ConnectionString(uri);
            cluster.applyConnectionString(connectionString)
                    .mode(ClusterConnectionMode.MULTIPLE)
                    .requiredClusterType(clusterType);
        }).build();
        // 返回结果
        return MongoClients.create(setting);
    }

    /**
     * 客户端
     *
     * @return 客户端
     */
    public MongoClient mongoClient() {
        MongoClientSettings.Builder builder = MongoClientSettings.builder();
        ServerAddress serverAddress = null;
        if (StringUtils.isNotNullAndEmpty(mongodbConfig.getUserName()) &&
                StringUtils.isNotNullAndEmpty(mongodbConfig.getPassword())) {
            MongoCredential credential;
            if (mongodbConfig.isEncryption()) {
                AesUtils aes = new AesUtils(customConfig);
                serverAddress = new ServerAddress(AesUtils.base64Decrypt(mongodbConfig.getUri()), mongodbConfig.getPort());
                credential = MongoCredential.createCredential(AesUtils.base64Decrypt(mongodbConfig.getUserName()),
                        mongodbConfig.getDataSource(), AesUtils.base64Decrypt(mongodbConfig.getPassword()).toCharArray());
            } else {
                credential = MongoCredential.createCredential(mongodbConfig.getUserName(), mongodbConfig.getDataSource(), mongodbConfig.getPassword().toCharArray());
            }
            builder = builder.credential(credential);
        }
        if (serverAddress == null) {
            serverAddress = new ServerAddress(mongodbConfig.getUri(), mongodbConfig.getPort());
        }

        final ServerAddress finalServerAddress = serverAddress;
        MongoClientSettings setting = builder.applyToClusterSettings(cluster ->
                cluster.hosts(Arrays.asList(finalServerAddress))
                        .mode(ClusterConnectionMode.SINGLE)
                        .requiredClusterType(ClusterType.STANDALONE)
        ).build();
        // 返回结果
        return MongoClients.create(setting);
    }
}
