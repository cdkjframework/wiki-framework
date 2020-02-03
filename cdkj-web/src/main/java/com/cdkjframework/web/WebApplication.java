package com.cdkjframework.web;

import com.cdkjframework.center.annotation.EnableAutoGenerate;
import com.cdkjframework.core.spring.CdkjApplication;
import com.cdkjframework.util.files.images.code.BarCodeUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
@EnableAutoGenerate(update = true, basePackage = "com.cdkjframework.web", projectName = "com.cdkjframework.web")
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
