package com.cdkj.framework.entity.message.aliyun;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkj.framework.entity.message.aliyun
 * @ClassName: RocketMqEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getShardingKey() {
        return shardingKey;
    }

    public void setShardingKey(String shardingKey) {
        this.shardingKey = shardingKey;
    }
}
