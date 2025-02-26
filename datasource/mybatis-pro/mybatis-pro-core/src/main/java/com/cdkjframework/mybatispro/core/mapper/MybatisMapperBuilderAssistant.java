package com.cdkjframework.mybatispro.core.mapper;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.mapper
 * @ClassName: MybatisMapperBuilderAssistant
 * @Description: Mybatis绘图生成器助手
 * @Author: xiaLin
 * @Version: 1.0
 */
public class MybatisMapperBuilderAssistant extends MapperBuilderAssistant {
  public MybatisMapperBuilderAssistant(Configuration configuration, String resource) {
    super(configuration, resource);
  }
}
