package com.cdkjframework.rocket.consumer;

import com.cdkjframework.entity.message.aliyun.RocketMqCallbackEntity;
import com.cdkjframework.exceptions.GlobalException;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.message.queue.rocket.client
 * @ClassName: RocketMessageListener
 * @Description: 消息监听接口
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface RocketMessageListener {

    /**
     * consume
     *
     * @param callback 返回数据结果
     * @throws GlobalException 异常信息
     */
    void consume(RocketMqCallbackEntity callback) throws GlobalException;
}
