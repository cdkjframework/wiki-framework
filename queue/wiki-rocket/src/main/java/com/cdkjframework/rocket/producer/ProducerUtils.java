package com.cdkjframework.rocket.producer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.OrderProducerBean;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.message.queue.rocket.server
 * @ClassName: ProducerUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class ProducerUtils {

    /**
     * 订单消息生产者
     */
    private static OrderProducer orderProducer;

    /**
     * 构造函数
     */
    public ProducerUtils(OrderProducerBean buildOrderProducer) {
        orderProducer = buildOrderProducer;
    }

    /**
     * 发送消息
     *
     * @param topic       主题
     * @param tag         tag
     * @param body        消息内容
     * @param shardingKey 设置代表消息的业务关键属性，请尽可能全局唯一。
     * @return 返回结果
     */
    public static String send(String topic, String tag, String body, String shardingKey) throws GlobalException {
        return send(topic, tag, body, shardingKey, IntegerConsts.NULL_LONG);
    }

    /**
     * 发送消息
     *
     * @param topic            主题
     * @param tag              tag
     * @param body             消息内容
     * @param shardingKey      设置代表消息的业务关键属性，请尽可能全局唯一。
     * @param startDeliverTime 定时消息
     * @return 返回结果
     */
    public static String send(String topic, String tag, String body, String shardingKey, Long startDeliverTime) throws GlobalException {
        AssertUtils.isEmptyMessage(topic, "主题不能为空");
        AssertUtils.isEmptyMessage(tag, "TAG不能为空");
        AssertUtils.isEmptyMessage(body, "消息内容不能为空");
        Message message = new Message(topic, tag, body.getBytes());
        if (startDeliverTime != null) {
            message.setStartDeliverTime(startDeliverTime);
        }
        // 发布消息
        SendResult result = orderProducer.send(message, shardingKey);
        if (result == null) {
            return StringUtils.Empty;
        }
        // 返回消息ID
        return result.getMessageId();
    }
}
