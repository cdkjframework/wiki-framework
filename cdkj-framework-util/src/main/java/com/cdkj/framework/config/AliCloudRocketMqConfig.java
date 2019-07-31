package com.cdkj.framework.config;

import com.cdkj.framework.util.tool.StringUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkj.framework.core.message.queue.aliyun.rocket.server.config
 * @ClassName: AliCloudRocketMqServerConfig
 * @Description: 配置读取
 * @Author: xiaLin
 * @Version: 1.0
 */

@Configuration
@ConfigurationProperties(prefix = "spring.queue.rocket.aliyun")
public class AliCloudRocketMqConfig {

    /**
     * MQ 创建的 Producer ID
     */
    private String producerId = "";
    /**
     * MQ 创建的 Consumer ID
     */
    private String consumerId = "";

    /**
     * GROUP ID
     */
    private String groupId;

    /**
     * 鉴权用 AccessKey
     */
    private String accessKey = "";

    /**
     * 鉴权用 SecretKey
     */
    private String secretKey = "";

    /**
     * 设置 TCP 接入域名，进入 MQ 控制台的生产者管理页面，在右侧操作列单击获取接入点获取
     */
    private String onsAddress = "";

    /**
     * 消息所属的 Topic 名称
     */
    private String topicNames = "";

    /**
     * Message Tag
     */
    private String tag = "";

    /**
     * 监听消息类型
     */
    private String messageType = "";

    /**
     * 调用类名
     */
    private String className = "";

    /**
     * 方法名称
     */
    private String methodName = "";

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getOnsAddress() {
        return onsAddress;
    }

    public void setOnsAddress(String onsAddress) {
        this.onsAddress = onsAddress;
    }

    public String getTopicNames() {
        if (StringUtil.isNullAndSpaceOrEmpty(topicNames)) {
            this.topicNames = "";
        }
        return topicNames;
    }

    public void setTopicNames(String topicNames) {
        this.topicNames = topicNames;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
