package com.cdkjframework.mybatispro.core.override;

import lombok.Getter;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.override
 * @ClassName: MybatisMapperProxyFactory
 * @Description: Mybatis Mapper 代理工厂
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
public class MybatisMapperProxyFactory<T> {

  /**
   * Mapper 接口
   */
  private final Class<T> mapperInterface;

  /**
   * 方法缓存
   */
  private final Map<Method, MybatisMapperProxy.MapperMethodInvoker> methodCache = new ConcurrentHashMap<>();

  public MybatisMapperProxyFactory(Class<T> mapperInterface) {
    this.mapperInterface = mapperInterface;
  }

  @SuppressWarnings("unchecked")
  protected T newInstance(MybatisMapperProxy<T> mapperProxy) {
    return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
  }

  public T newInstance(SqlSession sqlSession) {
    final MybatisMapperProxy<T> mapperProxy = new MybatisMapperProxy<>(sqlSession, mapperInterface, methodCache);
    return newInstance(mapperProxy);
  }
}
