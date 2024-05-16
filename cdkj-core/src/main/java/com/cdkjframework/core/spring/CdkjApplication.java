package com.cdkjframework.core.spring;

import com.cdkjframework.constant.Application;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.HostUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.core.spring
 * @ClassName: CdkjApplication
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class CdkjApplication {

  /**
   * 日志
   */
  private static LogUtils logUtils = LogUtils.getLogger(CdkjApplication.class);
  /**
   * 端口
   */
  static String SERVER_PORT = "server.port";

  /**
   * 应用名称
   */
  static String SPRING_APPLICATION_NAME = "spring.application.name";

  /**
   * 应用名称
   */
  static String CONTEXT_PATH = "server.servlet.context-path";

  /**
   * 程序启动
   *
   * @param args 参数
   * @return 返回结果
   */
  public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
    // 启动程序
    ConfigurableApplicationContext context = SpringApplication.run(primarySource, args);
    Application.applicationContext = context;

    Environment env = context.getEnvironment();
    String contextPath = ConvertUtils.convertString(env.getProperty(CONTEXT_PATH));
    if (env != null) {
      logUtils.info("\n---------------------------------------------------------------------------\n\t" +
                      "Application '{}' is running! Access URLs:\n\t" +
                      "Local: \t\t\t\thttp://localhost:{}{}\n\t" +
                      "External: \t\t\thttp://{}:{}{}\n\t" +
                      "External-IPv6: http://{}:{}{}\n" +
                      "---------------------------------------------------------------------------",
              env.getProperty(SPRING_APPLICATION_NAME),
              env.getProperty(SERVER_PORT), contextPath,
              HostUtils.getLocalHost(), env.getProperty(SERVER_PORT), contextPath,
              HostUtils.getLocalIpv6(), env.getProperty(SERVER_PORT), contextPath);
    }

    return context;
  }
}
