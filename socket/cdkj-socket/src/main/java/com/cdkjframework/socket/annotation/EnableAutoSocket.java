package com.cdkjframework.socket.annotation;

import com.cdkjframework.socket.config.SocketMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.swagger.annotation
 * @ClassName: EnableAutoSocket
 * @Description: socket 启动注解
 * @Author: xiaLin
 * @Date: 2023/7/18 9:20
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SocketMarkerConfiguration.class})
public @interface EnableAutoSocket {
}
