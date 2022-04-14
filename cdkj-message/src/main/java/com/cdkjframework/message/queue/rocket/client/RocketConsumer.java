package com.cdkjframework.message.queue.rocket.client;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.OrderConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.cdkjframework.config.AliCloudRocketMqConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.message.queue.rocket.server
 * @ClassName: AliCloudRocketMqClientListener
 * @Description: 注册 server 服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
@Configuration
public class RocketConsumer {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(RocketConsumer.class);

    /**
     * 回调
     */
    private final AbstractMessageListener abstractMessageListener;

    /**
     * 读取配置信息
     */
    private final AliCloudRocketMqConfig aliCloudRocketMqConfig;

    /**
     * 构造函数
     */
    public RocketConsumer(AbstractMessageListener abstractMessageListener, AliCloudRocketMqConfig aliCloudRocketMqConfig) {
        this.abstractMessageListener = abstractMessageListener;
        this.aliCloudRocketMqConfig = aliCloudRocketMqConfig;
    }

    /**
     * 集合属性
     */
    private Properties buildProperties() {
        Properties properties = new Properties();
        // 设置 GROUP_ID 提换 MQ ConsumerId ID
        properties.setProperty(PropertyKeyConst.GROUP_ID, aliCloudRocketMqConfig.getGroupId());
        // 鉴权用 AccessKey
        properties.setProperty(PropertyKeyConst.AccessKey, aliCloudRocketMqConfig.getAccessKey());
        // 鉴权用 SecretKey
        properties.setProperty(PropertyKeyConst.SecretKey, aliCloudRocketMqConfig.getSecretKey());
        // 地址
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, aliCloudRocketMqConfig.getOnsAddress());

        //返回集合
        return properties;
    }

    /**
     * 构造订单客户端
     *
     * @return 返回结果
     */
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public OrderConsumerBean buildOrderConsumer() {
        OrderConsumerBean orderConsumerBean = new OrderConsumerBean();
        // 配置文件
        orderConsumerBean.setProperties(buildProperties());
        // 订阅关系
        Map<Subscription, MessageOrderListener> subscriptionTable = new HashMap<>();
        List<String> topicList = aliCloudRocketMqConfig.getTopic();
        List<String> tagList = aliCloudRocketMqConfig.getTag();
        for (int i = IntegerConsts.ZERO; i < topicList.size(); i++) {
            Subscription subscription = new Subscription();
            subscription.setTopic(topicList.get(i));
            subscription.setExpression(tagList.get(i));
            subscriptionTable.put(subscription, abstractMessageListener);
        }

        orderConsumerBean.setSubscriptionTable(subscriptionTable);
        return orderConsumerBean;
    }
}
