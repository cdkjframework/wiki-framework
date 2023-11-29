package com.cdkjframework.socket.annotation;

import com.cdkjframework.socket.config.SocketConfigurationSelector;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.socket.annotation
 * @ClassName: EnableAutoSocket
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/7/18 9:24
 * @Version: 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import({SocketConfigurationSelector.class})
public @interface EnableAutoSocket {
}
