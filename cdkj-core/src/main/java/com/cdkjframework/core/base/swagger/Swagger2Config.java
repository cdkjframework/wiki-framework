package com.cdkjframework.core.base.swagger;

import com.cdkjframework.config.SwaggerConfig;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ProjectName: hongtu.slps.bms
 * @Package: com.cdkjframework.core.base.swagger
 * @ClassName: Swagger2Config
 * @Description: swagger2配置
 * @Author: xiaLin
 * @Version: 1.0
 */

@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 读取配置
     */
    @Autowired
    private SwaggerConfig swaggerConfig;

    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        Predicate<RequestHandler> predicate = RequestHandlerSelectors.basePackage(swaggerConfig.getBasePackage());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(predicate)
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     *
     * @return
     */
    private ApiInfo apiInfo() {
        //联系人信息
        Contact contact = new Contact(swaggerConfig.getContact(), swaggerConfig.getTermsOfServiceUrl(), swaggerConfig.getEmail());

        return new ApiInfoBuilder()
                .title(swaggerConfig.getTitle())
                .description(swaggerConfig.getDescription())
                .termsOfServiceUrl(swaggerConfig.getTermsOfServiceUrl())
                .contact(contact)
                .version(swaggerConfig.getVersion())
                .build();
    }
}
