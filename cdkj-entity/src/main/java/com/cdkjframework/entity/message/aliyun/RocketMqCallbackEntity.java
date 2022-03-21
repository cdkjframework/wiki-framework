package com.cdkjframework.entity.message.aliyun;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.entity.message.aliyun
 * @ClassName: RocketMqCallbackEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class RocketMqCallbackEntity {

    /**
     * 主题
     */
    private String topic;
    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 消息类型
     */
    private int messageType;

    /**
     * 标签
     */
    private String tag;

    /**
     * 出生时间戳
     */
    private long bornTimestamp;

    /**
     * 订单ID
     */
    private String shardingKey;

    /**
     * 获取定时消息开始投递时间
     */
    private long startDeliverTime;
}
