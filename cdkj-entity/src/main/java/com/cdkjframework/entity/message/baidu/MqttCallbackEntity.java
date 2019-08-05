package com.cdkjframework.entity.message.baidu;


import com.cdkjframework.enums.QueueMessageTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: ec-icm
 * @Package: com.cdkjframework.core.entity.message.baidu
 * @ClassName: MqttCallbackEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
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
}
