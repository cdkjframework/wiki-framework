package com.cdkjframework.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.swagger.config
 * @ClassName: SwaggerMarkerConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/7/18 9:21
 * @Version: 1.0
 */
@EnableOpenApi
@EnableWebMvc
//@EnableSwaggerBootstrapUI
@Configuration(proxyBeanMethods = false)
public class SwaggerMarkerConfiguration {

  @Bean
  public Marker swaggerMarker() {
    return new Marker();
  }

  public static class Marker {

  }
}
