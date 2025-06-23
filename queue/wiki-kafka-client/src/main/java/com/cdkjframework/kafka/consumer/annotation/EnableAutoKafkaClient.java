package com.cdkjframework.kafka.consumer.annotation;

import com.cdkjframework.kafka.consumer.config.KafkaClientMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.kafka.consumer.annotation
 * @ClassName: EnableAutoKafkaClient
 * @Description: Kafka客户端自动启动类
 * @Author: xiaLin
 * @Date: 2023/7/18 9:20
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({KafkaClientMarkerConfiguration.class})
public @interface EnableAutoKafkaClient {
}
