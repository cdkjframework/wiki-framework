package com.cdkjframework.mybatispro.core.mapper;

import com.cdkjframework.mybatispro.core.override.MybatisMapperProxy;
import lombok.Getter;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.mapper
 * @ClassName: MybatisMapperProxyFactory
 * @Description: 从 {@link MapperProxyFactory} copy 过来 </p>
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
public class MybatisMapperProxyFactory<T> {

  private final Class<T> mapperInterface;
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