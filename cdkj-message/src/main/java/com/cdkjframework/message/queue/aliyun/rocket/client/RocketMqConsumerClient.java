package com.cdkjframework.message.queue.aliyun.rocket.client;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.cdkjframework.config.AliCloudRocketMqClientConfig;
import com.cdkjframework.util.log.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkj.framework.core.message.queue.aliyun.rocket.server
 * @ClassName: AliCloudRocketMqClientListener
 * @Description: 注册 server 服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
@Order(1)
public class RocketMqConsumerClient implements ApplicationRunner {

    /**
     * 日志
     */
    private LogUtil logUtil = LogUtil.getLogger(RocketMqConsumerClient.class);

    /**
     * 回调
     */
    @Autowired
    private RocketMqMessageListener rocketMqMessageListener;

    /**
     * 读取配置信息
     */
    @Autowired
    private AliCloudRocketMqClientConfig aliCloudRocketMqConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        logUtil.info("进入 RocketMq 配置：" + new Date());

        //集合属性
        Properties properties = setProperties();

        //创建客服端消息监听
        Consumer consumer = ONSFactory.createConsumer(properties);
        //订阅消息
        this.configureMessageQueueClient(consumer);
        logUtil.info("进入 RocketMq 配置完成：" + new Date());
    }

    /**
     * 集合属性
     */
    private Properties setProperties() {
        Properties properties = new Properties();
        // 设置 GROUP_ID 提换 MQ ConsumerId ID
        properties.put(PropertyKeyConst.GROUP_ID, aliCloudRocketMqConfig.getGroupId());
        // 鉴权用 ConsumerId
        properties.put(PropertyKeyConst.ConsumerId, aliCloudRocketMqConfig.getGroupId());
        // 鉴权用 AccessKey
        properties.put(PropertyKeyConst.AccessKey, aliCloudRocketMqConfig.getAccessKey());
        // 鉴权用 SecretKey
        properties.put(PropertyKeyConst.SecretKey, aliCloudRocketMqConfig.getSecretKey());

        //返回集合
        return properties;
    }

    /**
     * 订阅消息
     */
    private void configureMessageQueueClient(Consumer consumer) {
        String[] topicList = aliCloudRocketMqConfig.getTopicNames().split(",");
        for (int i = 0; i < topicList.length; i++) {
            //设置监听信息
            final String subExpression = "*";
            consumer.subscribe(topicList[i], subExpression, rocketMqMessageListener);

            //启动监听
            consumer.start();
        }
    }
}
