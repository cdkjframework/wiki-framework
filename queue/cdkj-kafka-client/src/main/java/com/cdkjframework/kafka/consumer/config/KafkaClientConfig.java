package com.cdkjframework.kafka.consumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.kafka.consumer.config
 * @ClassName: KafkaClientConfig
 * @Description: Kafka客户端配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "spring.custom.kafka")
public class KafkaClientConfig {

  /**
   * 服务列表
   */
  private List<String> bootstrapServers;

  /**
   * 主题
   */
  private List<String> topics;

  /**
   * 账号
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 延迟为1毫秒
   */
  private Integer linger = 1;

  /**
   * 批量大小
   */
  private Integer batchSize = 16384;

  /**
   * 重试次数，0为不启用重试机制
   */
  private Integer retries = 0;

  /**
   * 人锁
   */
  private Integer maxBlock = 60000;

  /**
   * acks
   */
  private String acks = "1";

  /**
   * security.providers
   */
  private String securityProviders;

  /**
   * 启用自动提交
   */
  private boolean enableAutoCommit = true;

  /**
   * 会话超时
   */
  private String sessionTimeout = "5000";

  /**
   * 会话超时
   */
  private Integer maxPollInterval = 10000;

  /**
   * 组ID
   */
  private String groupId = "defaultGroup";

  /**
   * 最大投票记录
   */
  private Integer maxPollRecords = 1;

  /**
   * 并发性
   */
  private Integer concurrency = 3;

  /**
   * 拉取超时时间
   */
  private Integer pollTimeout = 60000;

  /**
   * 批量监听
   */
  private boolean batchListener = false;

  /**
   * 副本数量
   */
  private String sort = "1";

  /**
   * 分区数
   */
  private Integer partitions = 3;

  /**
   * 消费者默认支持解压
   */
  private String compressionType = "none";

  /**
   * offset偏移量规则设置
   */
  private String autoOffsetReset = "earliest";

  /**
   * 自动提交的频率
   */
  private Integer autoCommitInterval = 100;

  /**
   * 生产者可以使用的总内存字节来缓冲等待发送到服务器的记录
   */
  private Integer bufferMemory = 33554432;

  /**
   * 消息的最大大小限制
   */
  private Integer maxRequestSize = 1048576;
}
