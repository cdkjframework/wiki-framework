package com.cdkj.framework.entity.message.baidu;


import com.cdkj.framework.enums.QueueMessageTypeEnum;

/**
 * @ProjectName: ec-icm
 * @Package: com.cdkj.framework.core.entity.message.baidu
 * @ClassName: MqttCallbackEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class MqttCallbackEntity {

    /**
     * 消息ID
     */
    private int queueId;

    /**
     * topic
     */
    private String topic;

    /**
     * Qos
     */
    private int qos;

    /**
     * 是否成功
     */
    private boolean complete = true;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息类型
     */
    private QueueMessageTypeEnum messageType;

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public QueueMessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(QueueMessageTypeEnum messageType) {
        this.messageType = messageType;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
