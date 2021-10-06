package com.cdkjframework.message.queue.kafka.consumer;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.message.queue.kafka.KafkaConfig;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.BatchErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ConsumerAwareErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.message.queue.kafka
 * @ClassName: ProducerConfiguration
 * @Description: 设置@Configuration、@EnableKafka两个注解，声明Config并且打开KafkaTemplate能力。
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@EnableKafka
public class ConsumerConfiguration {

    /**
     * 日志
     */
    private final LogUtils logUtils = LogUtils.getLogger(ConsumerConfiguration.class);

    /**
     * 配置
     */
    private final KafkaConfig kafkaConfig;

    /**
     * 构造函数
     *
     * @param kafkaConfig 读取配置
     */
    public ConsumerConfiguration(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    /**
     * 监听容器工厂
     *
     * @return 返回结果
     */
    @Bean(name = "kafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        // 设置消费者工厂
        factory.setConsumerFactory(consumerFactory());
        // 消费者组中线程数量
        factory.setConcurrency(kafkaConfig.getConcurrency());
        // 拉取超时时间
        factory.getContainerProperties().setPollTimeout(kafkaConfig.getPollTimeout());

        // 当使用批量监听器时需要设置为true
        factory.setBatchListener(kafkaConfig.isBatchListener());
        // 将单条消息异常处理器添加到参数中
        factory.setErrorHandler(new ConsumerAwareErrorHandler() {
            @Override
            public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
                logUtils.error("// 将单条消息异常：" + thrownException.getMessage());
                logUtils.error("// 将单条消息：" + data.toString() + "，" + consumer.toString());
                Iterator<TopicPartition> iterator = consumer.assignment().iterator();
                if (iterator.hasNext()) {
                    // 提交重新消费
                    consumer.seek(iterator.next(), data.offset());
                }
            }
        });
        if (kafkaConfig.isBatchListener()) {
            // 将批量消息异常处理器添加到参数中
            factory.setBatchErrorHandler(new BatchErrorHandler() {
                @Override
                public void handle(Exception thrownException, ConsumerRecords<?, ?> data) {
                    logUtils.error("// 将批量消息异常：" + thrownException.getMessage());
                    logUtils.error(thrownException);
                    logUtils.error(JsonUtils.objectToJsonString(data));
                }
            });
        }

        return factory;
    }

    /**
     * 消费者工厂
     *
     * @return 返回消费工厂
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    /**
     * 消费者配置
     *
     * @return 返回结果
     */
    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> propsMap = new HashMap<>();
        // Kafka地址
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        //配置默认分组，这里没有配置+在监听的地方没有设置groupId，多个服务会出现收到相同消息情况
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getGroupId());
        // 是否自动提交offset偏移量(默认true)
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConfig.isEnableAutoCommit());
        if (kafkaConfig.isEnableAutoCommit()) {
            // 自动提交的频率(ms)
            propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaConfig.getAutoCommitInterval());
        }
        // 键的反序列化方式
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 值的反序列化方式
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // offset偏移量规则设置：
        // (1)、earliest：当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        // (2)、latest：当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
        // (3)、none：topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConfig.getAutoOffsetReset());
        return propsMap;
    }
}
