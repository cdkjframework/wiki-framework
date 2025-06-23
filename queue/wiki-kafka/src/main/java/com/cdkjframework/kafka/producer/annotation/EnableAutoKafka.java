package com.cdkjframework.kafka.producer.annotation;

import com.cdkjframework.kafka.producer.config.KafkaMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.kafka.producer.annotation
 * @ClassName: EnableAutoKafka
 * @Description: Kafka自动启动类
 * @Author: xiaLin
 * @Date: 2023/7/18 9:20
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({KafkaMarkerConfiguration.class})
public @interface EnableAutoKafka {
}
