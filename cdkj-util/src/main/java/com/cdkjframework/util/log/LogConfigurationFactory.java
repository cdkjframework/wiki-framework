package com.cdkjframework.util.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.net.URI;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.log
 * @ClassName: CustomConfigurationFactory
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Order(50)
@Plugin(name = "LogConfigurationFactory", category = ConfigurationFactory.CATEGORY)
public class LogConfigurationFactory extends ConfigurationFactory {

  /**
   * 创建配置
   *
   * @param name    名称
   * @param builder 构建
   * @return 返回配置信息
   */
  static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
    builder.setConfigurationName(name);
    // 只会记录带有级别错误的内部Log4J2消息
    builder.setStatusLevel(Level.ERROR);
    // 创建记录到的 appender System.out
    builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL).
        addAttribute("level", Level.DEBUG));

    // 为日志消息创建模式
    AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE").
        addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
    appenderBuilder.add(builder.newLayout("PatternLayout").
        addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"));
    // addAttribute("pattern", "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%file:%line] %-5level %logger{35} - %msg %n"));
    appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY,
        Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"));
    builder.add(appenderBuilder);

    // 创建使用STDOUT appender的记录器
    builder.add(builder.newLogger("org.apache.logging.log4j", Level.DEBUG).
        add(builder.newAppenderRef("Stdout")).
        addAttribute("additivity", false));
    builder.add(builder.newRootLogger(Level.ERROR).add(builder.newAppenderRef("Stdout")));
    return builder.build();
  }

  /**
   * 获取配置
   *
   * @param loggerContext 当前日志
   * @param source        源
   * @return 返回配置
   */
  @Override
  public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
    return getConfiguration(loggerContext, source.toString(), null);
  }

  /**
   * 获取配置
   *
   * @param loggerContext  当前日志
   * @param name           源
   * @param configLocation 配置地址
   * @return 返回配置
   */
  @Override
  public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
    ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
    return createConfiguration(name, builder);
  }

  /**
   * 获取支持的类型
   *
   * @return 返回数据
   */
  @Override
  protected String[] getSupportedTypes() {
    return new String[]{"*"};
  }
}
