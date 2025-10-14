
package com.cdkjframework.datasource.mybatis.connectivity;

import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import com.cdkjframework.datasource.mybatis.config.MybatisConfig;
import com.cdkjframework.util.tool.CollectUtils;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.database.mybatis.connectivity
 * @ClassName: MapperScannerConfiguration
 * @Description: Mapper扫描配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
public class MapperScannerConfiguration implements BeanDefinitionRegistryPostProcessor {

  /**
   * 读取配置
   */
  private final MybatisConfig mybatisConfig;

  /**
   * 构造函数
   */
  public MapperScannerConfiguration(MybatisConfig mybatisConfig) {
    this.mybatisConfig = mybatisConfig;
  }

  /**
   * 注册 mapper
   */
  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    // 创建自定义的Mapper扫描器
    ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);

    // 设置扫描过滤器：只扫描接口
    scanner.addIncludeFilter((MetadataReader metadataReader,
                              MetadataReaderFactory metadataReaderFactory) -> {
      boolean isInterface = metadataReader.getClassMetadata().isInterface();
      return isInterface;
    });
    MybatisConfig.Slave slave = mybatisConfig.getSlave();
    if (slave == null && CollectUtils.isEmpty(slave.getMybatisMapper())) {
      return;
    }
    scanner.setSqlSessionFactoryBeanName("sessionFactory");
    // 扫描所有指定的包
    scanner.scan(slave.getMybatisMapper().toArray(new String[0]));
  }

}
