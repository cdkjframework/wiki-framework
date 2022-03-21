package com.cdkjframework.message.queue.rocket.client;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.cdkjframework.entity.message.aliyun.RocketMqCallbackEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.message.queue.rocket.client
 * @ClassName: AliCloudRocketMqClientBean
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class RocketMessageListener implements MessageOrderListener {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(RocketMessageListener.class);

    /**
     * 订阅消息回传
     *
     * @param message        消息内容
     * @param consumeContext
     * @return 返回结果
     */
    @Override
    public final OrderAction consume(Message message, ConsumeOrderContext consumeContext) {
        //调用参数
        try {
            RocketMqCallbackEntity callbackEntity = new RocketMqCallbackEntity();
            callbackEntity.setBornTimestamp(message.getBornTimestamp());
            callbackEntity.setMessage(new String(message.getBody()));
            callbackEntity.setMessageId(message.getMsgID());
            callbackEntity.setTag(message.getTag());
            callbackEntity.setTopic(message.getTopic());
            callbackEntity.setShardingKey(message.getShardingKey());
            callbackEntity.setStartDeliverTime(message.getStartDeliverTime());

            consume(callbackEntity);

            // 消息成功
            return OrderAction.Success;
        } catch (GlobalException e) {
            logUtil.error(e.getMessage());
        }
        // 消费失败
        return OrderAction.Suspend;
    }

    /**
     * consume
     *
     * @param callback 返回数据结果
     */
    public void consume(RocketMqCallbackEntity callback) throws GlobalException {
        logUtil.info(callback.toString());
    }
}
