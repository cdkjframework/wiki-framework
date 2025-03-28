package com.cdkjframework.mybatispro.core.mapper;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.mapper
 * @ClassName: MybatisMapperRegistry
 * @Description: Mybatis Mapper 注册
 * @Author: xiaLin
 * @Version: 1.0
 */
public class MybatisMapperRegistry extends MapperRegistry {

  private final Configuration config;

  private final Map<Class<?>, MybatisMapperProxyFactory<?>> knownMappers = new ConcurrentHashMap<>();

  public MybatisMapperRegistry(Configuration config) {
    super(config);
    this.config = config;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
    MybatisMapperProxyFactory<T> mapperProxyFactory = (MybatisMapperProxyFactory<T>) knownMappers.get(type);
    if (mapperProxyFactory == null) {
      mapperProxyFactory = (MybatisMapperProxyFactory<T>) knownMappers.entrySet().stream()
          .filter(t -> t.getKey().getName().equals(type.getName())).findFirst().map(Map.Entry::getValue)
          .orElseThrow(() -> new BindingException("Type " + type + " is not known to the MybatisPlusMapperRegistry."));
    }
    try {
      return mapperProxyFactory.newInstance(sqlSession);
    } catch (Exception e) {
      throw new BindingException("Error getting mapper instance. Cause: " + e, e);
    }
  }

  @Override
  public <T> boolean hasMapper(Class<T> type) {
    return knownMappers.containsKey(type);
  }

  /**
   * 清空 Mapper 缓存信息
   */
  public <T> void removeMapper(Class<T> type) {
    knownMappers.entrySet().stream().filter(t -> t.getKey().getName().equals(type.getName()))
        .findFirst().ifPresent(t -> knownMappers.remove(t.getKey()));
  }

  @Override
  public <T> void addMapper(Class<T> type) {
    if (type.isInterface()) {
      if (hasMapper(type)) {
        return;
//                throw new BindingException("Type " + type + " is already known to the MapperRegistry.");
      }
      boolean loadCompleted = false;
      try {
        knownMappers.put(type, new MybatisMapperProxyFactory<>(type));
        // It's important that the type is added before the parser is run
        // otherwise the binding may automatically be attempted by the
        // mapper parser. If the type is already known, it won't try.
        MybatisMapperAnnotationBuilder parser = new MybatisMapperAnnotationBuilder(config, type);
        parser.parse();
        loadCompleted = true;
      } finally {
        if (!loadCompleted) {
          knownMappers.remove(type);
        }
      }
    }
  }

  /**
   * 使用自己的 knownMappers
   */
  @Override
  public Collection<Class<?>> getMappers() {
    return Collections.unmodifiableCollection(knownMappers.keySet());
  }

}
