package com.cdkjframework.datasource.mybatis.annotation;

import com.cdkjframework.datasource.mybatis.config.MybatisMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.datasource.mybatis.annotation
 * @ClassName: EnableAutoMybatis
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/12/5 17:27
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MybatisMarkerConfiguration.class})
public @interface EnableAutoMybatis {
}
