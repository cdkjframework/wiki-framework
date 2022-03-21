package com.cdkjframework.message.queue.rocket.server;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.OrderProducerBean;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.message.queue.rocket.server
 * @ClassName: ProducerUtils
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Order()
@Component
public class ProducerUtils implements ApplicationRunner {

    /**
     * 订单消息生产者
     */
    @Resource(name = "buildOrderProducer")
    private OrderProducerBean buildOrderProducer;

    /**
     * 订单消息生产者
     */
    private static OrderProducer orderProducer;

    /**
     * 创建初始化数据
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        orderProducer = buildOrderProducer;
    }

    /**
     * 发送消息
     *
     * @param topic 主题
     * @param tag   tag
     * @param body  消息内容
     * @return 返回结果
     */
    public static String send(String topic, String tag, String body) throws GlobalException {
        return send(topic, tag, IntegerConsts.NULL_LONG, body);
    }

    /**
     * 发送消息
     *
     * @param topic            主题
     * @param tag              tag
     * @param startDeliverTime 定时消息
     * @param body             消息内容
     * @return 返回结果
     */
    public static String send(String topic, String tag, Long startDeliverTime, String body) throws GlobalException {
        AssertUtils.isEmptyMessage(topic, "主题不能为空");
        AssertUtils.isEmptyMessage(tag, "TAG不能为空");
        AssertUtils.isEmptyMessage(body, "消息内容不能为空");
        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        String shardingKey = StringUtils.DEFAULT_VALUE + GeneratedValueUtils.getOrderlyShortUuid();
        Message message = new Message(topic, tag, body.getBytes(Charset.defaultCharset()));
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
