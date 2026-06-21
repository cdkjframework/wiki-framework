package com.cdkjframework.datasource.mybatis.connectivity;

import com.cdkjframework.datasource.mybatis.config.MybatisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperScannerConfigurationTest {

  @Test
  void shouldSkipScanWhenSlaveConfigIsMissing() {
    MybatisConfig mybatisConfig = new MybatisConfig();
    MapperScannerConfiguration configuration = new MapperScannerConfiguration(mybatisConfig);
    DefaultListableBeanFactory registry = new DefaultListableBeanFactory();

    assertDoesNotThrow(() -> configuration.postProcessBeanDefinitionRegistry(registry));
    assertEquals(0, registry.getBeanDefinitionCount());
  }

  @Test
  void shouldSkipScanWhenSlaveMapperListIsEmpty() {
    MybatisConfig mybatisConfig = new MybatisConfig();
    MybatisConfig.Slave slave = new MybatisConfig.Slave();
    slave.setMybatisMapper(Collections.emptyList());
    mybatisConfig.setSlave(slave);

    MapperScannerConfiguration configuration = new MapperScannerConfiguration(mybatisConfig);
    DefaultListableBeanFactory registry = new DefaultListableBeanFactory();

    assertDoesNotThrow(() -> configuration.postProcessBeanDefinitionRegistry(registry));
    assertEquals(0, registry.getBeanDefinitionCount());
  }
}