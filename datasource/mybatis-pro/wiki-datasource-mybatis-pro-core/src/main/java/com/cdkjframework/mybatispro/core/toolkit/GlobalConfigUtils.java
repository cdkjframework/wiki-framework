package com.cdkjframework.mybatispro.core.toolkit;

import com.cdkjframework.mybatispro.core.annotation.IdType;
import com.cdkjframework.mybatispro.core.config.GlobalConfig;
import com.cdkjframework.mybatispro.core.handlers.AnnotationHandler;
import com.cdkjframework.mybatispro.core.handlers.MetaObjectHandler;
import com.cdkjframework.mybatispro.core.incrementer.IKeyGenerator;
import com.cdkjframework.mybatispro.core.injector.ISqlInjector;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import com.cdkjframework.mybatispro.core.metadata.TableInfoHelper;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.CollectUtils;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.ClassUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mybatis全局缓存工具类
 *
 * @author Caratacus
 * @since 2017-06-15
 */
public class GlobalConfigUtils {

  /**
   * 缓存全局信息
   */
  private static final Map<String, GlobalConfig> GLOBAL_CONFIG = new ConcurrentHashMap<>();

  /**
   * 获取当前的SqlSessionFactory
   *
   * @param clazz 实体类
   */
  @Deprecated
  public static SqlSessionFactory currentSessionFactory(Class<?> clazz) {
    AssertUtils.isEmptyMessage(clazz, "Class must not be null");
    TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
    AssertUtils.isEmptyMessage(tableInfo, ClassUtils.getUserClass(clazz).getName() + " Not Found TableInfoCache.");
    return getGlobalConfig(tableInfo.getConfiguration()).getSqlSessionFactory();
  }

  /**
   * 获取默认 MybatisGlobalConfig
   */
  public static GlobalConfig defaults() {
    return new GlobalConfig().setDbConfig(new GlobalConfig.DbConfig());
  }

  /**
   * <p>
   * 设置全局设置(以configuration地址值作为Key)
   * <p/>
   *
   * @param configuration Mybatis 容器配置对象
   * @param globalConfig  全局配置
   */
  public static void setGlobalConfig(Configuration configuration, GlobalConfig globalConfig) {
    AssertUtils.isTrue(configuration != null && globalConfig != null, "Error: Could not setGlobalConfig");
    // 设置全局设置
    GLOBAL_CONFIG.putIfAbsent(Integer.toHexString(configuration.hashCode()), globalConfig);
  }

  /**
   * 获取MybatisGlobalConfig (统一所有入口)
   *
   * @param configuration Mybatis 容器配置对象
   */
  public static GlobalConfig getGlobalConfig(Configuration configuration) {
    AssertUtils.isEmptyMessage(configuration, "Error: You need Initialize MybatisConfiguration !");
    final String key = Integer.toHexString(configuration.hashCode());
    return CollectUtils.computeIfAbsent(GLOBAL_CONFIG, key, k -> defaults());
  }

  public static List<IKeyGenerator> getKeyGenerators(Configuration configuration) {
    return getGlobalConfig(configuration).getDbConfig().getKeyGenerators();
  }

  public static IdType getIdType(Configuration configuration) {
    return getGlobalConfig(configuration).getDbConfig().getIdType();
  }

  public static GlobalConfig.DbConfig getDbConfig(Configuration configuration) {
    return getGlobalConfig(configuration).getDbConfig();
  }

  public static ISqlInjector getSqlInjector(Configuration configuration) {
    return getGlobalConfig(configuration).getSqlInjector();
  }

  public static Optional<MetaObjectHandler> getMetaObjectHandler(Configuration configuration) {
    return Optional.ofNullable(getGlobalConfig(configuration).getMetaObjectHandler());
  }

  public static Optional<AnnotationHandler> getAnnotationHandler(Configuration configuration) {
    return Optional.ofNullable(getGlobalConfig(configuration).getAnnotationHandler());
  }

  public static Class<?> getSuperMapperClass(Configuration configuration) {
    return getGlobalConfig(configuration).getSuperMapperClass();
  }

  public static boolean isSupperMapperChildren(Configuration configuration, Class<?> mapperClass) {
    return getSuperMapperClass(configuration).isAssignableFrom(mapperClass);
  }

  public static Set<String> getMapperRegistryCache(Configuration configuration) {
    return getGlobalConfig(configuration).getMapperRegistryCache();
  }
}
