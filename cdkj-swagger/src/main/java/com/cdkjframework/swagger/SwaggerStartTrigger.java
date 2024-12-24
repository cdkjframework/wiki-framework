package com.cdkjframework.swagger;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.constant.Application;
import com.cdkjframework.entity.swagger.SwaggerApiInfoEntity;
import com.cdkjframework.entity.swagger.SwaggerHeaderEntity;
import com.cdkjframework.swagger.config.SwaggerConfig;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import jakarta.xml.ws.Response;
import jdk.javadoc.doclet.Doclet;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: hongtu.slps.bms
 * @Package: com.cdkjframework.core.base.swagger
 * @ClassName: SwaggerStartTrigger
 * @Description: swagger2配置
 * @Author: xiaLin
 * @Version: 1.0
 */
public class SwaggerStartTrigger {

  /**
   * 日志
   */
  private final LogUtils LOG_UTILS = LogUtils.getLogger(SwaggerStartTrigger.class);

  /**
   * 读取配置
   */
  private final SwaggerConfig swaggerConfig;

  /**
   * 构建函数
   */
  public SwaggerStartTrigger(SwaggerConfig swaggerConfig) {
    this.swaggerConfig = swaggerConfig;

  }

  /**
   * 创建API应用
   * apiInfo() 增加API相关信息
   * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
   * 本例采用指定扫描的包路径来定义指定要建立API的目录。
   */
  public void start() {
    if (StringUtils.isNullAndSpaceOrEmpty(swaggerConfig.getBasePackage())) {
      return;
    }
    LOG_UTILS.info("Swagger 3 开始配置：{}", LocalDateUtils.dateTimeCurrentFormatter());
    //接口信息
    List<SwaggerApiInfoEntity> apiInfoEntityList = JsonUtils
        .jsonStringToList(swaggerConfig.getBasePackage(), SwaggerApiInfoEntity.class);
    final boolean hidden = swaggerConfig.getHidden();
    final String header = swaggerConfig.getHeaders();
    for (SwaggerApiInfoEntity entity :
        apiInfoEntityList) {
      LOG_UTILS.info("Swagger 3 分组：{}", entity.getGroupName());

      BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(GroupedOpenApi.class,
          () -> {
            GroupedOpenApi openApi = GroupedOpenApi.builder()
                .group(entity.getGroupName())
                .packagesToScan(entity.getBasePackage().toArray(new String[entity.getBasePackage().size()]))
                .pathsToMatch()
                .addOpenApiCustomizer(customizer -> {
                  customizer.setInfo(apiInfo());
                })
                .addRouterOperationCustomizer((routerOperation, handlerMethod) -> {
                  routerOperation.setHeaders(new String[]{header});
                  return routerOperation;
                })
                .headersToMatch(header)
                .build();
            // 返回结果
            return openApi;
          });
      DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) Application
          .applicationContext.getBeanFactory();
      BeanDefinition definition = definitionBuilder.getBeanDefinition();
      beanFactory.registerBeanDefinition(entity.getBeanName(), definition);
    }
    LOG_UTILS.info("Swagger 3 配置成功：{}", LocalDateUtils.dateTimeCurrentFormatter());
  }

  /**
   * 创建API
   *
   * @return 返回结果
   */
  @Bean(name = "openApi")
  public OpenAPI openApi() {
    // 开启配置
    start();

    return new OpenAPI()
        .info(apiInfo());
  }

  /**
   * 创建该API的基本信息（这些基本信息会展现在文档页面中）
   * 访问地址：http://项目实际地址/swagger-ui.html
   *
   * @return 返回接口信息
   */
  private Info apiInfo() {
    //联系人信息
    Contact contact = new Contact()
        .name(swaggerConfig.getContact())
        .url(swaggerConfig.getTermsOfServiceUrl())
        .email(swaggerConfig.getEmail());

    // 构建结果
    return new Info()
        .title(swaggerConfig.getTitle())
        .description(swaggerConfig.getDescription())
        .termsOfService(swaggerConfig.getTermsOfServiceUrl())
        .contact(contact)
        .version(swaggerConfig.getVersion());
  }
}
