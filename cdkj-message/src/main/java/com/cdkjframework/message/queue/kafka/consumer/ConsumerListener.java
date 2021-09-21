package com.cdkjframework.message.queue.kafka.consumer;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

/**
 * @ProjectName: com.lesmarthome.interface
 * @Package: com.lesmarthome.interfaces.mq
 * @ClassName: ConsumerListener
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
    private final ConsumerService consumerService;

    /**
     * 构造函数
     *
     * @param consumerService 消费者服务接口
     */
    public ConsumerListener(ConsumerService consumerService) {
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
