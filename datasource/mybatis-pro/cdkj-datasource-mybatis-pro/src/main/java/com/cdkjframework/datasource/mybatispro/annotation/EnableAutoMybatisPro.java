package com.cdkjframework.datasource.mybatispro.annotation;

import com.cdkjframework.datasource.mybatispro.config.MybatisMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.datasource.mybatis.annotation
 * @ClassName: EnableAutoMybatis
 * @Description: Mybatis标记配置
 * @Author: xiaLin
 * @Date: 2023/12/5 17:27
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MybatisMarkerConfiguration.class})
public @interface EnableAutoMybatisPro {
}
