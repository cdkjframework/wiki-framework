package com.cdkjframework.datasource.mybatis.connectivity;

import com.cdkjframework.datasource.mybatis.config.MybatisConfig;
import com.cdkjframework.util.tool.CollectUtils;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import java.util.List;

/**
 * Mapper 扫描配置解析，供 BeanDefinitionRegistryPostProcessor 在属性绑定完成前从 Environment 读取。
 */
final class MybatisMapperScanSupport {

  private static final String PREFIX = "spring.datasource";

  private MybatisMapperScanSupport() {
  }

  static MybatisConfig bindMybatisConfig(Environment environment) {
    return Binder.get(environment)
        .bind(PREFIX, Bindable.of(MybatisConfig.class))
        .orElseGet(MybatisConfig::new);
  }

  static List<String> resolvePrimaryMapperPackages(MybatisConfig mybatisConfig) {
    MybatisConfig.Master master = mybatisConfig.getMaster();
    if (master != null && !CollectUtils.isEmpty(master.getMybatisMapper())) {
      return master.getMybatisMapper();
    }
    return mybatisConfig.getMybatisMapper();
  }

  static List<String> resolveSlaveMapperPackages(MybatisConfig mybatisConfig) {
    MybatisConfig.Slave slave = mybatisConfig.getSlave();
    if (slave == null || CollectUtils.isEmpty(slave.getMybatisMapper())) {
      return null;
    }
    return slave.getMybatisMapper();
  }
}
