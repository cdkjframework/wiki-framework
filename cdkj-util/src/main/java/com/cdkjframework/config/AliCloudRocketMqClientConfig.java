package com.cdkjframework.config;

import com.cdkjframework.util.tool.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.message.queue.aliyun.rocket.server.config
 * @ClassName: AliCloudRocketMqServerConfig
 * @Description: 配置读取
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.queue.rocket.aliyun.cleint")
public class AliCloudRocketMqClientConfig {

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
}
