package com.cdkjframework.kafka.producer.annotation;

import com.cdkjframework.kafka.producer.config.KafkaMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.swagger.annotation
 * @ClassName: EnableAutoSocket
 * @Description: java类作用描述
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
