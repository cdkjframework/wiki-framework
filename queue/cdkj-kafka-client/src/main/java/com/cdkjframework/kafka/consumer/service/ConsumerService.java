package com.cdkjframework.kafka.consumer.service;

import com.cdkjframework.exceptions.GlobalException;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.kafka.consumer
 * @ClassName: com.cdkjframework.kafka.consumer.service.ConsumerService
 * @Description: 消费者服务
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface ConsumerService {

    /**
     * 消息内容
     *
     * @param topics  主题
     * @param message 内容
     * @throws GlobalException 异常信息
     */
    void onMessage(String topics, String message) throws GlobalException;
}
