package com.cdkjframework.datasource.mybatis.connectivity;

import com.cdkjframework.datasource.mybatis.config.MybatisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.mock.env.MockEnvironment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.datasource.mybatis.connectivity
 * @ClassName: MapperPrimaryScannerConfigurationTest
 * @Description: 主库 Mapper 扫描配置单元测试
 * @Author: xiaLin
 * @Version: 1.0
 */
class MapperPrimaryScannerConfigurationTest {

  /**
   * 主库和顶层 Mapper 均未配置时，应跳过扫描
   */
  @Test
  void shouldSkipScanWhenBothConfigsAreNull() {
    MapperPrimaryScannerConfiguration configuration = new MapperPrimaryScannerConfiguration();
    configuration.setEnvironment(new MockEnvironment());
    DefaultListableBeanFactory registry = new DefaultListableBeanFactory();

    assertDoesNotThrow(() -> configuration.postProcessBeanDefinitionRegistry(registry));
    assertEquals(0, registry.getBeanDefinitionCount());
  }

  /**
   * 主库和顶层 Mapper 均为空列表时，应跳过扫描
   */
  @Test
  void shouldSkipScanWhenBothListsAreEmpty() {
    MybatisConfig mybatisConfig = new MybatisConfig();
    mybatisConfig.setMybatisMapper(Collections.emptyList());
    MybatisConfig.Master master = new MybatisConfig.Master();
    master.setMybatisMapper(Collections.emptyList());
    mybatisConfig.setMaster(master);

    assertTrue(MybatisMapperScanSupport.resolvePrimaryMapperPackages(mybatisConfig).isEmpty());
  }

  /**
   * 仅配置顶层 mybatisMapper 时，应使用顶层配置正常完成扫描
   */
  @Test
  void shouldUseFallbackTopLevelMapperWhenMasterIsNull() {
    MybatisConfig mybatisConfig = new MybatisConfig();
    mybatisConfig.setMybatisMapper(Arrays.asList("com.non.existent.primary.mapper"));

    MockEnvironment environment = new MockEnvironment();
    environment.setProperty("spring.datasource.mybatis-mapper[0]", "com.non.existent.primary.mapper");

    MapperPrimaryScannerConfiguration configuration = new MapperPrimaryScannerConfiguration();
    configuration.setEnvironment(environment);
    DefaultListableBeanFactory registry = new DefaultListableBeanFactory();

    assertDoesNotThrow(() -> configuration.postProcessBeanDefinitionRegistry(registry));
  }

  /**
   * master.mybatisMapper 优先于顶层 mybatisMapper
   */
  @Test
  void shouldPreferMasterMapperWhenTopLevelIsEmpty() {
    MybatisConfig mybatisConfig = new MybatisConfig();
    mybatisConfig.setMybatisMapper(Collections.emptyList());
    MybatisConfig.Master master = new MybatisConfig.Master();
    master.setMybatisMapper(Arrays.asList("com.non.existent.master.mapper"));
    mybatisConfig.setMaster(master);

    List<String> mapperPackages = MybatisMapperScanSupport.resolvePrimaryMapperPackages(mybatisConfig);
    assertEquals(List.of("com.non.existent.master.mapper"), mapperPackages);
  }
}
