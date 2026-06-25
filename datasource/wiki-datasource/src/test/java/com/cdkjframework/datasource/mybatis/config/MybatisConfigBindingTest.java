package com.cdkjframework.datasource.mybatis.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MybatisConfigBindingTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
      .withUserConfiguration(PropertiesTestConfig.class)
      .withPropertyValues(
          "spring.datasource.mybatis-mapper[0]=com.framewiki.example.mapper",
          "spring.datasource.mybatis-mapper-xml[0]=/mybatis/**/*.xml",
          "spring.datasource.master.url=jdbc:mysql://localhost:3306/test",
          "spring.datasource.master.username=root",
          "spring.datasource.master.password=secret",
          "spring.datasource.master.driver-class-name=com.mysql.cj.jdbc.Driver",
          "spring.datasource.slave.url=jdbc:mysql://localhost:3306/test",
          "spring.datasource.slave.username=root",
          "spring.datasource.slave.password=secret",
          "spring.datasource.slave.driver-class-name=com.mysql.cj.jdbc.Driver");

  @Test
  void shouldBindDatasourceProperties() {
    contextRunner.run(context -> {
      MybatisConfig mybatisConfig = context.getBean(MybatisConfig.class);

      assertNotNull(mybatisConfig.getMaster());
      assertEquals("jdbc:mysql://localhost:3306/test", mybatisConfig.getMaster().getUrl());
      assertEquals("root", mybatisConfig.getMaster().getUsername());
      assertEquals("secret", mybatisConfig.getMaster().getPassword());
      assertEquals("com.mysql.cj.jdbc.Driver", mybatisConfig.getMaster().getDriverClassName());

      assertNotNull(mybatisConfig.getSlave());
      assertEquals("jdbc:mysql://localhost:3306/test", mybatisConfig.getSlave().getUrl());

      assertNotNull(mybatisConfig.getMybatisMapper());
      assertFalse(mybatisConfig.getMybatisMapper().isEmpty());
      assertEquals("com.framewiki.example.mapper", mybatisConfig.getMybatisMapper().get(0));

      assertNotNull(mybatisConfig.getMybatisMapperXml());
      assertEquals("/mybatis/**/*.xml", mybatisConfig.getMybatisMapperXml().get(0));
    });
  }

  @Configuration
  @EnableConfigurationProperties(MybatisConfig.class)
  static class PropertiesTestConfig {
  }
}
