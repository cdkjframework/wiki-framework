package com.cdkjframework.entity.socket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.entity.socket
 * @ClassName: WebSocketEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class WebSocketEntity {

    /**
     * 会话进程
     */
    private String clientId;

    /**
     * 消息
     */
    private String message;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 权限值
     */
    private String token;

    /**
     * 三方权限
     */
    private String authorization;
}
