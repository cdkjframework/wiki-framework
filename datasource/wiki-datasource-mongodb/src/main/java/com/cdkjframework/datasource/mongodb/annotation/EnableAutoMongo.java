package com.cdkjframework.datasource.mongodb.annotation;

import com.cdkjframework.datasource.mongodb.config.MongoMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.datasource.mongodb.annotation
 * @ClassName: EnableAutoMongo
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/12/5 22:26
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MongoMarkerConfiguration.class})
public @interface EnableAutoMongo {
}
