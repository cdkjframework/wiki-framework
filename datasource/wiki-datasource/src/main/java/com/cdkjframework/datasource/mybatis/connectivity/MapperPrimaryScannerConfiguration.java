
package com.cdkjframework.datasource.mybatis.connectivity;

import com.cdkjframework.datasource.mybatis.config.MybatisConfig;
import com.cdkjframework.util.tool.CollectUtils;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.database.mybatis.connectivity
 * @ClassName: MapperPrimaryScannerConfiguration
 * @Description: Mapper扫描配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
public class MapperPrimaryScannerConfiguration implements BeanDefinitionRegistryPostProcessor {

  /**
   * 读取配置
   */
  private final MybatisConfig mybatisConfig;

  /**
   * 构造函数
   */
  public MapperPrimaryScannerConfiguration(MybatisConfig mybatisConfig) {
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
    if (CollectUtils.isEmpty(mybatisConfig.getMybatisMapper())) {
      return;
    }
    scanner.setSqlSessionFactoryBeanName("primarySessionFactory");
    // 扫描所有指定的包
    scanner.scan(mybatisConfig.getMybatisMapper().toArray(new String[0]));
  }

}
