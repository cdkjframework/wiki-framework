package com.cdkjframework.message.kafka.consumer;

import com.cdkjframework.util.log.LogUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: com.lesmarthome.interface
 * @Package: com.lesmarthome.interfaces.mq
 * @ClassName: com.cdkjframework.message.kafka.consumer.ConsumerListener
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class ConsumerListener {

    /**
     * 日志
     */
    private final LogUtils logUtils = LogUtils.getLogger(ConsumerListener.class);

    /**
     * 消费者服务接口
     */
    private final com.cdkjframework.message.kafka.consumer.ConsumerService consumerService;

    /**
     * 构造函数
     *
     * @param consumerService 消费者服务接口
     */
    public ConsumerListener(com.cdkjframework.message.kafka.consumer.ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    /**
     * 单条监听MQ消息
     *
     * @param data     消息内容
     * @param consumer 消息者
     */
    @KafkaListener(topics = "#{'${spring.custom.kafka.topics}'.split(',')}", groupId = "${spring.custom.kafka.groupId}")
    public void listener(ConsumerRecord<String, String> data, Consumer consumer) {
        try {
            consumerService.onMessage(data.topic(), data.value());
            consumer.commitAsync();
        } catch (Exception e) {
            logUtils.error(e);
            // 抛出异常，以重试消费
            throw new RuntimeException(e.getMessage());
        }
    }
}
