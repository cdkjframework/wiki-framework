package com.cdkjframework.web;

import com.cdkjframework.redis.subscribe.SubscribeConsumer;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.web
 * @ClassName: SubscribeMessage
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
// */
//@Component
//@Order(1)
//public class SubscribeMessage extends SubscribeConsumer implements ApplicationRunner {
//
//    private LogUtils logUtils = LogUtils.getLogger(SubscribeMessage.class);
//
//    /**
//     * 订阅消息回调
//     *
//     * @param channel 渠道.
//     * @param message 消息.
//     */
//    @Override
//    public void subscribe(String channel, String message) {
//        logUtils.debug(String.format("channel=%s,message=$s", channel, message));
//    }
//
//    /**
//     * 从模式订阅接收的消息
//     *
//     * @param pattern 模式
//     * @param channel 渠道.
//     * @param message 消息.
//     */
//    @Override
//    public void subscribe(String pattern, String channel, String message) {
//        logUtils.debug(String.format("pattern=%s,channel=%s,message=$s", pattern, channel, message));
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        consumerMessage();
//    }
//}
