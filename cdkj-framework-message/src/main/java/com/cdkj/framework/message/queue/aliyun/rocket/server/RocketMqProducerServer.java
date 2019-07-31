package com.cdkj.framework.message.queue.aliyun.rocket.server;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.cdkj.framework.config.AliCloudRocketMqConfig;
import com.cdkj.framework.entity.message.aliyun.RocketMqEntity;
import com.cdkj.framework.util.log.LogUtil;
import com.cdkj.framework.util.tool.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkj.framework.core.message.queue.aliyun.rocket.server
 * @ClassName: AliCloudRocketMqServerSend
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class RocketMqProducerServer {

    /**
     * 日志
     */
    private static LogUtil logUtil = LogUtil.getLogger(RocketMqProducerServer.class);

    /**
     * 读取配置信息
     */
    @Autowired
    private AliCloudRocketMqConfig aliCloudRocketMqConfig;

    /**
     * 服务生产者
     */
    private static Producer producer;

    /**
     * 订单信息
     */
    private static OrderProducer orderProducer;

    /**
     * 构造函数
     */
    @PostConstruct
    public void init() {

        //集合属性
        Properties properties = setProperties();
        //创建服务 producer
        this.configureMessageQueueServer(properties);
    }

    /**
     * 发送消息
     *
     * @param rocketMqEntity 信息内容
     * @return 返回结果（布尔值）
     */
    public static boolean send(RocketMqEntity rocketMqEntity) {

        //将数据转换为 byte
        byte[] body = rocketMqEntity.getMessage().getBytes();

        //创建消息
        Message message = new Message(rocketMqEntity.getTopic(), rocketMqEntity.getTag(), body);

        //设置信息唯一标识
        if (StringUtil.isNotNullAndEmpty(rocketMqEntity.getKey())) {
            message.setKey(rocketMqEntity.getKey());
        }

        boolean isSend = true;

        //发送消息
        try {
            SendResult sendResult = null;
            if (StringUtil.isNotNullAndEmpty(rocketMqEntity.getShardingKey())) {
                sendResult = orderProducer.send(message, rocketMqEntity.getShardingKey());
            } else {
                sendResult = producer.send(message);
            }
            if (sendResult == null || StringUtil.isNullAndSpaceOrEmpty(sendResult.getMessageId())) {
                isSend = false;
            }
        } catch (Exception ex) {
            //出现异常则说明消息发送失败
            logUtil.error(ex.getMessage());
            isSend = false;
        }

        //返回结果
        return isSend;
    }

    /**
     * 集合属性
     */
    private Properties setProperties() {
        Properties properties = new Properties();
        // MQ Producer ID
        properties.put(PropertyKeyConst.ProducerId, aliCloudRocketMqConfig.getGroupId());
        // 设置 GROUP_ID 提换 MQ Producer ID
        properties.put(PropertyKeyConst.GROUP_ID, aliCloudRocketMqConfig.getGroupId());
        // 鉴权用 AccessKey
        properties.put(PropertyKeyConst.AccessKey, aliCloudRocketMqConfig.getAccessKey());
        // 鉴权用 SecretKey
        properties.put(PropertyKeyConst.SecretKey, aliCloudRocketMqConfig.getSecretKey());
//        // 设置 TCP 接入域名
//        properties.put(PropertyKeyConst.NAMESRV_ADDR, aliCloudRocketMqConfig.getOnsAddress());

        //返回结果
        return properties;
    }

    /**
     * 配置服务端消息
     */
    private void configureMessageQueueServer(Properties properties) {

        //创建 Producer
        producer = ONSFactory.createProducer(properties);
        // 启动 Producer
        producer.start();

        //创建 Producer
        orderProducer = ONSFactory.createOrderProducer(properties);
        // 启动 Producer
        orderProducer.start();
    }
}
