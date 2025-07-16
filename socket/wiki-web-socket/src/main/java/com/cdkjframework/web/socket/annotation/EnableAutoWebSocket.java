package com.cdkjframework.web.socket.annotation;

import com.cdkjframework.web.socket.config.WebSocketMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * WebSocket 启动类注解
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.swagger.annotation
 * @ClassName: EnableAutoWebSocket
 * @Description: WebSocket 启动类注解
 * @Author: xiaLin
 * @Date: 2023/7/18 9:20
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({WebSocketMarkerConfiguration.class})
public @interface EnableAutoWebSocket {
}
