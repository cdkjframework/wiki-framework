package com.cdkjframework.datasource.jpa.annotation;

import com.cdkjframework.datasource.jpa.config.JpaMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.datasource.jpa.annotation
 * @ClassName: EnableAutoJpa
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/12/5 22:18
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({JpaMarkerConfiguration.class})
public @interface EnableAutoJpa {
}
