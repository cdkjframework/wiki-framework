package com.cdkjframework.config;

import com.cdkjframework.util.tool.StringUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.message.queue.aliyun.rocket.server.config
 * @ClassName: AliCloudRocketMqServerConfig
 * @Description: 配置读取
 * @Author: xiaLin
 * @Version: 1.0
 */

@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "spring.rocket")
public class AliCloudRocketMqConfig {

    /**
     * MQ 创建的 Producer ID
     */
    private String producerId;
    /**
     * MQ 创建的 Consumer ID
     */
    private String consumerId;

    /**
     * GROUP ID
     */
    private String groupId;

    /**
     * 鉴权用 AccessKey
     */
    private String accessKey;

    /**
     * 鉴权用 SecretKey
     */
    private String secretKey;

    /**
     * 设置 TCP 接入域名，进入 MQ 控制台的生产者管理页面，在右侧操作列单击获取接入点获取
     */
    private String onsAddress;

    /**
     * 订阅模式
     */
    private boolean broadcasting;

    /**
     * 消息所属的 Topic 名称
     */
    private List<String> topic;

    /**
     * Message Tag
     */
    private List<String> tag;
}
