package com.cdkjframework.swagger.annotation;

import com.cdkjframework.swagger.config.SwaggerMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.swagger.annotation
 * @ClassName: EnableAutoSwagger
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/7/18 9:20
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SwaggerMarkerConfiguration.class})
public @interface EnableAutoSwagger {
}
