package com.cdkjframework.swagger;

import com.cdkjframework.constant.Application;
import com.cdkjframework.swagger.config.SwaggerConfig;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.HostUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

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
   * 应用名称
   */
  static String CONTEXT_PATH = "server.servlet.context-path";

  /**
   * 端口
   */
  static String SERVER_PORT = "server.port";

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
   * 创建该API的基本信息（这些基本信息会展现在文档页面中）
   * 访问地址：http://项目实际地址/swagger-ui.html
   *
   * @return 返回接口信息
   */
  @Bean(name = "openApi")
  public OpenAPI openApi() {
    return new OpenAPI()
        //必须
        .info(apiInfo())
        .servers(buildServers())
        .externalDocs(new ExternalDocumentation())
//        .tags(buildTags())
        .components(languageHeader());
  }

  /**
   * 构建header
   *
   * @return 返回header
   */
  private Components languageHeader() {
    Components components = new Components();

    List<SwaggerConfig.SwaggerHeaderEntity> headers = swaggerConfig.getHeaders();
    if (CollectUtils.isEmpty(headers)) {
      return components;
    }
    for (SwaggerConfig.SwaggerHeaderEntity header : headers) {
      // 定义全局 Header 参数
      Parameter languageHeader = new Parameter()
          .in(ParameterIn.HEADER.toString())
          .name(header.getHeaderName())
          .description(header.getDescription());
      components.addParameters(header.getHeaderName(), languageHeader);
    }
    //添加header
    return components;
  }

  /**
   * 构建服务器列表
   *
   * @return 返回服务器列表
   */
  private List<Server> buildServers() {
    List<Server> servers = new ArrayList<>();
    ConfigurableApplicationContext context = Application.applicationContext;
    Environment env = context.getEnvironment();
    String contextPath = ConvertUtils.convertString(env.getProperty(CONTEXT_PATH));
    String serverPort = env.getProperty(SERVER_PORT);

    String localHost = HostUtils.getLocalHost();
    Server server = new Server();
    server.setUrl(String.format("http://%s:%s%s", localHost, serverPort, contextPath));
    server.setDescription("Production server");

    servers.add(server);
    // 添加更多服务器配置
    return servers;
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
