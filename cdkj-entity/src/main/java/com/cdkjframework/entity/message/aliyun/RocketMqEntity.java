package com.cdkjframework.entity.message.aliyun;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.entity.message.aliyun
 * @ClassName: RocketMqEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class RocketMqEntity {

    /**
     * 主题
     */
    private String topic;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 设置信息唯一标识
     */
    private String key;

    /**
     * 标签
     */
    private String tag;

    /**
     * 启动键
     */
    private String shardingKey;
}
