package com.cdkjframework.web;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.cdkjframework.center.annotation.EnableAutoGenerate;
import com.cdkjframework.core.spring.CdkjApplication;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web
 * @ClassName: WebApplaction
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.cdkjframework.web",
        "com.cdkjframework.center",
        "com.cdkjframework.config",
        "com.cdkjframework.core",
        "com.cdkjframework.util",
        "com.cdkjframework.entity",
        "com.cdkjframework.constant",
        "com.cdkjframework.datasource.mybatis",
        "com.cdkjframework.datasource.mongodb",
        "com.cdkjframework.core.base.swagger"
})
@MapperScan(basePackages = "com.cdkjframework.core.business.mapper")
@EnableAutoGenerate(update = true)
@EnableSwagger2
//@EnableApolloConfig
public class WebApplication {

    /**
     * 启动方法
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        CdkjApplication.run(WebApplication.class, args);
    }
}
