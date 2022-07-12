package com.cdkjframework.message.queue.rocket.client;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;
import com.aliyun.openservices.ons.api.bean.OrderConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderConsumer;
import com.cdkjframework.config.AliCloudRocketMqConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
        // 设置模式
        if (aliCloudRocketMqConfig.isBroadcasting()) {
            properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
        }

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
        // 创建并客户端并架构配置
        OrderConsumerBean orderConsumerBean = new OrderConsumerBean();
        orderConsumerBean.setProperties(buildProperties());
        // 订阅关系
        Map<Subscription, MessageOrderListener> subscriptionTable = new HashMap<>();
        List<String> topicList = aliCloudRocketMqConfig.getTopic();
        List<String> tagList = aliCloudRocketMqConfig.getTag();
        if (CollectionUtils.isEmpty(tagList)) {
            tagList = new ArrayList<>();
            tagList.add("*");
        }
        for (String topic :
                topicList) {
            for (String tag :
                    tagList) {
                Subscription subscription = new Subscription();
                subscription.setTopic(topic);
                subscription.setExpression(tag);
                subscriptionTable.put(subscription, abstractMessageListener);
            }
        }

        orderConsumerBean.setSubscriptionTable(subscriptionTable);
        return orderConsumerBean;
    }
}
