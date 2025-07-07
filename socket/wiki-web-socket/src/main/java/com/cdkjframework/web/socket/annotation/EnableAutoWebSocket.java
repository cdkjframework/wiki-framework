package com.cdkjframework.web.socket.annotation;

import com.cdkjframework.web.socket.config.WebSocketMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.swagger.annotation
 * @ClassName: EnableAutoWebSocket
 * @Description: java类作用描述
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
