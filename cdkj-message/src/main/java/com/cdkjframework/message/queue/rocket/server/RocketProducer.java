package com.cdkjframework.message.queue.rocket.server;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.bean.OrderProducerBean;
import com.cdkjframework.config.AliCloudRocketMqConfig;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.message.queue.rocket.server
 * @ClassName: AliCloudRocketMqServerSend
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@Configuration
public class RocketProducer {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(RocketProducer.class);

    /**
     * 读取配置信息
     */
    private final AliCloudRocketMqConfig aliCloudRocketMqConfig;

    /**
     * 构造函数
     */
    public RocketProducer(AliCloudRocketMqConfig aliCloudRocketMqConfig) {
        this.aliCloudRocketMqConfig = aliCloudRocketMqConfig;
    }

    /**
     * 建立订单生产商
     * 返回结果
     */
    @Bean(name = "buildOrderProducer", initMethod = "start", destroyMethod = "shutdown")
    public OrderProducerBean buildOrderProducer() {
        OrderProducerBean orderProducerBean = new OrderProducerBean();
        orderProducerBean.setProperties(buildProperties());
        return orderProducerBean;
    }

    /**
     * 集合属性
     */
    private Properties buildProperties() {
        Properties properties = new Properties();
        // 鉴权用 AccessKey
        properties.setProperty(PropertyKeyConst.AccessKey, aliCloudRocketMqConfig.getAccessKey());
        // 鉴权用 SecretKey
        properties.setProperty(PropertyKeyConst.SecretKey, aliCloudRocketMqConfig.getSecretKey());
        // 地址
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, aliCloudRocketMqConfig.getOnsAddress());

        //返回集合
        return properties;
    }
}
