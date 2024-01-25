package com.cdkjframework.kafka.producer;

import com.cdkjframework.kafka.producer.config.KafkaConfig;
import com.cdkjframework.kafka.producer.util.ProducerUtils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.kafka.producer
 * @ClassName: ProducerConfiguration
 * @Description: 设置@Configuration、@EnableKafka两个注解，声明Config并且打开KafkaTemplate能力。
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@RequiredArgsConstructor
public class ProducerConfiguration {

  /**
   * 配置
   */
  private final KafkaConfig kafkaConfig;

  /**
   * JAAS配置
   */
  private String JAAS_CONFIG = "org.apache.kafka.common.security.plain.PlainLoginModule required username=%s password=%s;";

  /**
   * Producer Template 配置
   */
  @Bean(name = "kafkaTemplate")
  public KafkaTemplate<String, String> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  /**
   * Producer 工厂配置
   */
  @Bean(name = "producerFactory")
  public ProducerFactory<String, String> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  /**
   * Producer 参数配置
   */
  public Map<String, Object> producerConfigs() {
    Map<String, Object> props = new HashMap<>();
    // 指定多个kafka集群多个地址
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());

    // 重试次数，0为不启用重试机制
    props.put(ProducerConfig.RETRIES_CONFIG, kafkaConfig.getRetries());
    //同步到副本, 默认为1
    // acks=0 把消息发送到kafka就认为发送成功
    // acks=1 把消息发送到kafka leader分区，并且写入磁盘就认为发送成功
    // acks=all 把消息发送到kafka leader分区，并且leader分区的副本follower对消息进行了同步就任务发送成功
    props.put(ProducerConfig.ACKS_CONFIG, kafkaConfig.getAcks());

    // 生产者空间不足时，send()被阻塞的时间，默认60s
    props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, kafkaConfig.getMaxBlock());
    // security.providers
    props.put(ProducerConfig.SECURITY_PROVIDERS_CONFIG, kafkaConfig.getSecurityProviders());
    // 控制批处理大小，单位为字节
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaConfig.getBatchSize());
    // 批量发送，延迟为1毫秒，启用该功能能有效减少生产者发送消息次数，从而提高并发量
    props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaConfig.getLinger());
    // 生产者可以使用的总内存字节来缓冲等待发送到服务器的记录
    props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaConfig.getBufferMemory());
    // 消息的最大大小限制,也就是说send的消息大小不能超过这个限制, 默认1048576(1MB)
    props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, kafkaConfig.getMaxRequestSize());
    // 键的序列化方式
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    // 值的序列化方式
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    // 压缩消息，支持四种类型，分别为：none、lz4、gzip、snappy，默认为none。
    // 消费者默认支持解压，所以压缩设置在生产者，消费者无需设置。
    props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaConfig.getCompressionType());
    props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, kafkaConfig.getPartitions());

    // 账号密码
    if (StringUtils.isNotNullAndEmpty(kafkaConfig.getUsername()) &&
            StringUtils.isNotNullAndEmpty(kafkaConfig.getPassword())) {
      props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name);
      String SASL_MECHANISM = "PLAIN";
      props.put(SaslConfigs.SASL_MECHANISM, SASL_MECHANISM);
      props.put(SaslConfigs.SASL_JAAS_CONFIG, String.format(JAAS_CONFIG, kafkaConfig.getUsername(), kafkaConfig.getPassword()));
    }

    return props;
  }

}
