package com.cdkjframework.swagger;

import com.cdkjframework.constant.Application;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.swagger.SwaggerApiInfoEntity;
import com.cdkjframework.entity.swagger.SwaggerHeaderEntity;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.fasterxml.classmate.TypeResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: hongtu.slps.bms
 * @Package: com.cdkjframework.core.base.swagger
 * @ClassName: Swagger2Config
 * @Description: swagger2配置
 * @Author: xiaLin
 * @Version: 1.0
 */
//@Component
@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfiguration {

    /**
     * 读取配置
     */
    private final SwaggerConfig swaggerConfig;

    /**
     * 类型解析程序
     */
    private final TypeResolver typeResolver;

    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public void runSwagger() {
        if (StringUtils.isNullAndSpaceOrEmpty(swaggerConfig.getBasePackage())) {
            return;
        }
        //接口信息
        List<SwaggerApiInfoEntity> apiInfoEntityList = JsonUtils
                .jsonStringToList(swaggerConfig.getBasePackage(), SwaggerApiInfoEntity.class);
        List<SwaggerHeaderEntity> headerEntityList;
        if (StringUtils.isNotNullAndEmpty(swaggerConfig.getHeaders())) {
            headerEntityList = JsonUtils
                    .jsonStringToList(swaggerConfig.getHeaders(), SwaggerHeaderEntity.class);
        } else {
            headerEntityList = new ArrayList<>();
        }
        List<String> resolve = swaggerConfig.getResolve();
        AlternateTypeRule[] alternateTypeRules = null;
        if (resolve != null && resolve.size() > IntegerConsts.ZERO) {
            alternateTypeRules = new AlternateTypeRule[resolve.size()];
            for (String key :
                    resolve) {
                try {
                    Type type = Class.forName(key);
                    alternateTypeRules[resolve.indexOf(key)] = AlternateTypeRules.newRule(
                            typeResolver.resolve(Map.class, String.class, typeResolver.resolve(List.class, type)),
                            typeResolver.resolve(Map.class, String.class, WildcardType.class), Ordered.HIGHEST_PRECEDENCE);
                } catch (ClassNotFoundException e) {

                }
            }
        }
        final boolean hidden = swaggerConfig.getHidden();
        for (SwaggerApiInfoEntity entity :
                apiInfoEntityList) {
            AlternateTypeRule[] finalAlternateTypeRules = alternateTypeRules;
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Docket.class,
                    () -> {
                        //设置 header
                        List<Parameter> pars = new ArrayList<>();
                        for (SwaggerHeaderEntity header :
                                headerEntityList) {
                            ParameterBuilder builderPar = new ParameterBuilder();
                            builderPar.name(header.getHeaderName())
                                    .description(header.getDescription())
                                    .modelRef(new ModelRef(header.getHeaderType())).parameterType("header")
                                    .required(false).build();
                            pars.add(builderPar.build());
                        }
                        Docket result;
                        ApiSelectorBuilder builder;
                        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                                .groupName(entity.getGroupName());
                        if (finalAlternateTypeRules != null) {
                            docket = docket.alternateTypeRules(finalAlternateTypeRules);
                        }
                        builder = docket.select().apis(RequestHandlerSelectors
                                .basePackage(entity.getBasePackage()));
                        if (hidden) {
                            result = builder.paths(PathSelectors.none()).build().apiInfo(apiInfo());
                        } else {
                            result = builder.build().globalOperationParameters(pars).apiInfo(apiInfo());
                        }
                        return result;

                    });

            BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
            BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) Application
                    .applicationContext.getBeanFactory();
            beanFactory.registerBeanDefinition(entity.getBeanName(), beanDefinition);
        }
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     *
     * @return
     */
    private ApiInfo apiInfo() {
        //联系人信息
        Contact contact = new Contact(swaggerConfig.getContact(),
                swaggerConfig.getTermsOfServiceUrl(), swaggerConfig.getEmail());

        return new ApiInfoBuilder()
                .title(swaggerConfig.getTitle())
                .description(swaggerConfig.getDescription())
                .termsOfServiceUrl(swaggerConfig.getTermsOfServiceUrl())
                .contact(contact)
                .version(swaggerConfig.getVersion())
                .build();
    }
}
