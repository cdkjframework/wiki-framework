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
   * 读取配置文件配置
   */
  private final MongoConfig mongoConfig;
  /**
   * 自定义配置
   */
  private final CustomConfig customConfig;

  /**
   * 构造函数
   */
  public MongoConfiguration(MongoConfig mongoConfig, CustomConfig customConfig) {
    this.mongoConfig = mongoConfig;
    this.customConfig = customConfig;
  }

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
    if (StringUtils.isNullAndSpaceOrEmpty(mongoConfig.getUri())) {
      return new SimpleMongoClientDatabaseFactory(uri);
    }

    logUtil.info("mongodb 进入配置");
    MongoClient mongoClient;
    // 是否为集群
    if (mongoConfig.isCluster()) {
      mongoClient = mongoDbClusterFactory();
    } else {
      mongoClient = mongoClient();
    }

    logUtil.info("mongodb 配置结束");
    return new SimpleMongoClientDatabaseFactory(mongoClient, mongoConfig.getDataSource());
  }

  /**
   * 构建集群连接
   *
   * @return 返回mongo客户端
   */
  public MongoClient mongoDbClusterFactory() {
    String uri;
    if (StringUtils.isNotNullAndEmpty(mongoConfig.getPassword()) &&
            StringUtils.isNotNullAndEmpty(mongoConfig.getUserName())) {

      if (mongoConfig.isEncryption()) {
        AesUtils aes = new AesUtils(customConfig);
        uri = String.format("mongodb://%s:%s@%s:%d/%s",
                AesUtils.base64Decrypt(mongoConfig.getUserName()),
                AesUtils.base64Decrypt(mongoConfig.getPassword()),
                AesUtils.base64Decrypt(mongoConfig.getUri()),
                mongoConfig.getPort(), mongoConfig.getAdminSource());
      } else {
        uri = String.format("mongodb://%s:%s@%s:%d/%s",
                mongoConfig.getUserName(), mongoConfig.getPassword(), mongoConfig.getUri(),
                mongoConfig.getPort(), mongoConfig.getAdminSource());
      }
    } else {
      uri = String.format("mongodb://%s:%d/%s", mongoConfig.getUri(),
              mongoConfig.getPort(), mongoConfig.getDataSource());
    }
    //客户端配置（连接数、副本集群验证）
    MongoClientSettings.Builder builder = MongoClientSettings.builder();
    ClusterType clusterType = mongoConfig.isSharded() ? ClusterType.SHARDED : ClusterType.REPLICA_SET;
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
    if (StringUtils.isNotNullAndEmpty(mongoConfig.getUserName()) &&
            StringUtils.isNotNullAndEmpty(mongoConfig.getPassword())) {
      MongoCredential credential;
      if (mongoConfig.isEncryption()) {
        new AesUtils(customConfig);
        serverAddress = new ServerAddress(AesUtils.base64Decrypt(mongoConfig.getUri()), mongoConfig.getPort());
        credential = MongoCredential.createCredential(AesUtils.base64Decrypt(mongoConfig.getUserName()),
                mongoConfig.getDataSource(), AesUtils.base64Decrypt(mongoConfig.getPassword()).toCharArray());
      } else {
        credential = MongoCredential.createCredential(mongoConfig.getUserName(), mongoConfig.getDataSource(), mongoConfig.getPassword().toCharArray());
      }
      builder = builder.credential(credential);
    }
    if (serverAddress == null) {
      serverAddress = new ServerAddress(mongoConfig.getUri(), mongoConfig.getPort());
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
