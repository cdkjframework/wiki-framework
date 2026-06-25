
package com.cdkjframework.datasource.mybatis.connectivity;

import com.cdkjframework.datasource.mybatis.config.MybatisConfig;
import com.cdkjframework.util.tool.CollectUtils;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.util.List;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.database.mybatis.connectivity
 * @ClassName: MapperScannerConfiguration
 * @Description: Mapper扫描配置
 * @Author: xiaLin
 * @Version: 1.0
 */
public class MapperScannerConfiguration implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

  private Environment environment;

  @Override
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }

  /**
   * 注册 mapper
   */
  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    MybatisConfig mybatisConfig = MybatisMapperScanSupport.bindMybatisConfig(environment);
    List<String> mapperPackages = MybatisMapperScanSupport.resolveSlaveMapperPackages(mybatisConfig);
    if (CollectUtils.isEmpty(mapperPackages)) {
      return;
    }

    ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
    scanner.addIncludeFilter((MetadataReader metadataReader,
                              MetadataReaderFactory metadataReaderFactory) ->
        metadataReader.getClassMetadata().isInterface());
    scanner.setSqlSessionFactoryBeanName("sessionFactory");
    scanner.scan(mapperPackages.toArray(new String[0]));
  }

}
