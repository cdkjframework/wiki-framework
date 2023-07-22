package com.cdkjframework.web.socket.annotation;

import com.cdkjframework.web.socket.config.WebsocketConfigurationSelector;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web.socket.annotation
 * @ClassName: EnableAutoWebsocket
 * @Description: 启动 webSocket
 * @Author: xiaLin
 * @Date: 2023/6/17 19:23
 * @Version: 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import({WebsocketConfigurationSelector.class})
public @interface EnableAutoWebsocket {
}
