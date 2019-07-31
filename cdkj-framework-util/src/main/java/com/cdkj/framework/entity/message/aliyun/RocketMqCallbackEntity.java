package com.cdkj.framework.entity.message.aliyun;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkj.framework.entity.message.aliyun
 * @ClassName: RocketMqCallbackEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getBornTimestamp() {
        return bornTimestamp;
    }

    public void setBornTimestamp(long bornTimestamp) {
        this.bornTimestamp = bornTimestamp;
    }
}
