package com.cdkjframework.mybatispro.core.plugins;

import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.mybatispro.core.annotation.InterceptorIgnore;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.plugins
 * @ClassName: InterceptorIgnoreHelper
 * @Description: 拦截器忽略帮助程序
 * @Author: xiaLin
 * @Version: 1.0
 */
public class InterceptorIgnoreHelper {


  /**
   * SQL 解析缓存
   * key 可能是 mappedStatement 的 ID,也可能是 class 的 name
   */
  private static final Map<String, IgnoreStrategy> IGNORE_STRATEGY_CACHE = new ConcurrentHashMap<>();
  /**
   * 本地线程拦截器忽略策略缓存
   */
  private static final ThreadLocal<IgnoreStrategy> IGNORE_STRATEGY_LOCAL = new ThreadLocal<>();

  /**
   * 手动设置拦截器忽略执行策略，权限大于注解权限
   * <p>
   * InterceptorIgnoreHelper.handle(IgnoreStrategy.builder().tenantLine(true).build());
   * </p>
   * <p>
   * 注意，需要手动关闭调用方法 {@link #clearIgnoreStrategy()}
   * </p>
   * <p>简化操作可请使用{@link #execute(IgnoreStrategy, Supplier)}</p>
   *
   * @param ignoreStrategy {@link IgnoreStrategy}
   */
  public static void handle(IgnoreStrategy ignoreStrategy) {
    IGNORE_STRATEGY_LOCAL.set(ignoreStrategy);
  }

  /**
   * 清空本地忽略策略
   */
  public static void clearIgnoreStrategy() {
    IGNORE_STRATEGY_LOCAL.remove();
  }

  /**
   * 判断当前线程是否有忽略策略
   *
   * @return 是否有忽略策略
   * @since 3.5.10
   */
  public static boolean hasIgnoreStrategy() {
    return IGNORE_STRATEGY_LOCAL.get() != null;
  }

  /**
   * 初始化缓存
   * <p>
   * Mapper 上 InterceptorIgnore 注解信息
   *
   * @param mapperClass Mapper Class
   */
  public synchronized static IgnoreStrategy initSqlParserInfoCache(Class<?> mapperClass) {
    InterceptorIgnore ignore = mapperClass.getAnnotation(InterceptorIgnore.class);
    if (ignore != null) {
      String key = mapperClass.getName();
      IgnoreStrategy cache = buildIgnoreStrategy(key, ignore);
      IGNORE_STRATEGY_CACHE.put(key, cache);
      return cache;
    }
    return null;
  }

  /**
   * 获取忽略策略缓存信息
   *
   * @param key key
   * @return 策略信息
   * @since 3.5.10
   */
  public static IgnoreStrategy getIgnoreStrategy(String key) {
    return IGNORE_STRATEGY_CACHE.get(key);
  }

  /**
   * 按指定策略执行指定方法 (忽略线程级别,参数执行级使用最高)
   * 方法执行完成后后释放掉当前线程上的忽略策略.
   * <p>
   * 注意:
   * <li>1.不要和{@link #handle(IgnoreStrategy)}一起混合使用,此方法只是简化操作,防止未释放掉资源造成的错误<li/>
   * <li>2.不要和{@link InterceptorIgnore} 注解一起搭配使用,例如在mapper上的default方法里再调用此方法,最终优先级还是以此方法为准<li/>
   * <li>3.记住,一旦调用了此方法,开始会覆盖你当前执行线程上的策略,结束必定会释放掉当前线程上的策略</>
   * </p>
   *
   * @param ignoreStrategy 忽略策略
   * @param supplier       执行方法
   * @param <T>            T
   * @return 返回值
   * @since 3.5.10
   */
  public static <T> T execute(IgnoreStrategy ignoreStrategy, Supplier<T> supplier) {
    try {
      handle(ignoreStrategy);
      return supplier.get();
    } finally {
      clearIgnoreStrategy();
    }
  }

  /**
   * 按指定策略执行指定方法 (忽略线程级别,参数执行级使用最高)
   * 方法执行完成后后释放掉当前线程上的忽略策略.
   * <p>
   * 注意:
   * <li>1.不要和{@link #handle(IgnoreStrategy)}一起混合使用,此方法只是简化操作,防止未释放掉资源造成的错误<li/>
   * <li>2.不要和{@link InterceptorIgnore} 注解一起搭配使用,例如在mapper上的default方法里再调用此方法,最终优先级还是以此方法为准<li/>
   * <li>3.记住,一旦调用了此方法,开始会覆盖你当前执行线程上的策略,结束必定会释放掉当前线程上的策略</>
   * </p>
   *
   * @param ignoreStrategy 忽略策略
   * @param runnable       执行方法
   * @since 3.5.10
   */
  public static void execute(IgnoreStrategy ignoreStrategy, Runnable runnable) {
    try {
      handle(ignoreStrategy);
      runnable.run();
    } finally {
      clearIgnoreStrategy();
    }
  }

  /**
   * 通过方法获取策略信息(优先级方法注解>当前类注解)
   *
   * @param method 方法信息
   * @return 忽略策略信息
   * @see #initSqlParserInfoCache(Class)
   * @see #initSqlParserInfoCache(IgnoreStrategy, String, Method)
   * @since 3.5.10
   */
  public static IgnoreStrategy findIgnoreStrategy(Class<?> clz, Method method) {
    String className = clz.getName();
    IgnoreStrategy ignoreStrategy = getIgnoreStrategy(method.getDeclaringClass().getName() + StringUtils.DOT + method.getName());
    if (ignoreStrategy == null) {
      ignoreStrategy = getIgnoreStrategy(className);
    }
    return ignoreStrategy;
  }

  /**
   * 初始化缓存
   * <p>
   * Mapper#method 上 InterceptorIgnore 注解信息
   *
   * @param mapperAnnotation Mapper Class Name
   * @param method           Method
   */
  public static void initSqlParserInfoCache(IgnoreStrategy mapperAnnotation, String mapperClassName, Method method) {
    InterceptorIgnore ignoreStrategy = method.getAnnotation(InterceptorIgnore.class);
    String key = mapperClassName.concat(StringUtils.DOT).concat(method.getName());
    String name = mapperClassName.concat(StringUtils.HASH).concat(method.getName());
    if (ignoreStrategy != null) {
      IgnoreStrategy methodCache = buildIgnoreStrategy(name, ignoreStrategy);
      if (mapperAnnotation == null) {
        IGNORE_STRATEGY_CACHE.put(key, methodCache);
        return;
      }
      IGNORE_STRATEGY_CACHE.put(key, chooseCache(mapperAnnotation, methodCache));
    }
  }

  /**
   * 判断是否忽略
   *
   * @param id id
   * @return boolean
   */
  public static boolean willIgnoreTenantLine(String id) {
    return willIgnore(id, IgnoreStrategy::getTenantLine);
  }

  /**
   * 判断是否忽略
   *
   * @param id id
   * @return boolean
   */
  public static boolean willIgnoreDynamicTableName(String id) {
    return willIgnore(id, IgnoreStrategy::getDynamicTableName);
  }

  /**
   * 判断是否忽略
   *
   * @param id id
   * @return boolean
   */
  public static boolean willIgnoreBlockAttack(String id) {
    return willIgnore(id, IgnoreStrategy::getBlockAttack);
  }

  /**
   * 判断是否忽略
   *
   * @param id id
   * @return boolean
   */
  public static boolean willIgnoreIllegalSql(String id) {
    return willIgnore(id, IgnoreStrategy::getIllegalSql);
  }

  /**
   * 判断是否忽略
   *
   * @param id id
   * @return boolean
   */
  public static boolean willIgnoreDataPermission(String id) {
    return willIgnore(id, IgnoreStrategy::getDataPermission);
  }

  /**
   * 判断是否忽略
   *
   * @param id  id
   * @param key key
   * @return boolean
   */
  public static boolean willIgnoreOthersByKey(String id, String key) {
    return willIgnore(id, i -> CollectUtils.isNotEmpty(i.getOthers()) && i.getOthers().getOrDefault(key, false));
  }

  /**
   * 判断是否忽略
   *
   * @param id       id
   * @param function function
   * @return boolean
   */
  public static boolean willIgnore(String id, Function<IgnoreStrategy, Boolean> function) {
    // 1，优化获取本地忽略策略
    IgnoreStrategy ignoreStrategy = IGNORE_STRATEGY_LOCAL.get();
    if (null == ignoreStrategy) {
      // 2，不存在取注解策略
      ignoreStrategy = IGNORE_STRATEGY_CACHE.get(id);
    }
    if (ignoreStrategy == null && id.endsWith(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
      // 支持一下 selectKey
      ignoreStrategy = IGNORE_STRATEGY_CACHE.get(id.substring(0, id.length() - SelectKeyGenerator.SELECT_KEY_SUFFIX.length()));
    }
    if (ignoreStrategy == null) {
      int index = id.lastIndexOf(StringUtils.DOT);
      ignoreStrategy = IGNORE_STRATEGY_CACHE.get(index > 0 ? id.substring(0, index) : id);
    }
    if (ignoreStrategy != null) {
      Boolean apply = function.apply(ignoreStrategy);
      return apply != null && apply;
    }
    return false;
  }

  /**
   * 选择缓存
   *
   * @param mapper mapper
   * @param method method
   * @return IgnoreStrategy
   */
  private static IgnoreStrategy chooseCache(IgnoreStrategy mapper, IgnoreStrategy method) {
    return IgnoreStrategy.builder()
        .tenantLine(chooseBoolean(mapper.getTenantLine(), method.getTenantLine()))
        .dynamicTableName(chooseBoolean(mapper.getDynamicTableName(), method.getDynamicTableName()))
        .blockAttack(chooseBoolean(mapper.getBlockAttack(), method.getBlockAttack()))
        .illegalSql(chooseBoolean(mapper.getIllegalSql(), method.getIllegalSql()))
        .dataPermission(chooseBoolean(mapper.getDataPermission(), method.getDataPermission()))
        .others(chooseOthers(mapper.getOthers(), method.getOthers()))
        .build();
  }

  /**
   * 构建忽略策略
   *
   * @param name   名称
   * @param ignore 注解
   * @return 忽略策略
   */
  private static IgnoreStrategy buildIgnoreStrategy(String name, InterceptorIgnore ignore) {
    return IgnoreStrategy.builder()
        .tenantLine(getBoolean("tenantLine", name, ignore.tenantLine()))
        .dynamicTableName(getBoolean("dynamicTableName", name, ignore.dynamicTableName()))
        .blockAttack(getBoolean("blockAttack", name, ignore.blockAttack()))
        .illegalSql(getBoolean("illegalSql", name, ignore.illegalSql()))
        .dataPermission(getBoolean("dataPermission", name, ignore.dataPermission()))
        .others(getOthers(name, ignore.others()))
        .build();
  }

  /**
   * 获取Boolean
   *
   * @param node  节点
   * @param name  名称
   * @param value 值
   * @return 返回结果
   */
  private static Boolean getBoolean(String node, String name, String value) {
    if (StringUtils.isNullAndSpaceOrEmpty(value)) {
      return null;
    }
    if (StringUtils.ONE.equals(value) || StringUtils.TRUE.equals(value) || StringUtils.ON.equals(value)) {
      return true;
    }
    if (StringUtils.ZERO.equals(value) || StringUtils.FALSE.equals(value) || StringUtils.OFF.equals(value)) {
      return false;
    }
    throw new GlobalRuntimeException(String.format("unsupported value \"%s\" by `@InterceptorIgnore#%s` on top of \"%s\"", value, node, name));
  }

  /**
   * 其它类型
   *
   * @param name   名称
   * @param values 值
   * @return 返回结果
   */
  private static Map<String, Boolean> getOthers(String name, String[] values) {
    if (CollectUtils.isEmpty(values)) {
      return null;
    }
    Map<String, Boolean> map = CollectUtils.newHashMapWithExpectedSize(values.length);
    for (String s : values) {
      int index = s.indexOf(StringUtils.AT);
      AssertUtils.isTrue(index > 0, "unsupported value \"%s\" by `@InterceptorIgnore#others` on top of \"%s\"", s, name);
      String key = s.substring(0, index);
      Boolean value = getBoolean("others", name, s.substring(index + 1));
      map.put(key, value);
    }
    return map;
  }

  /**
   * 选择布尔值
   *
   * @param mapper 是否为 mapper
   * @param method 方法
   * @return 返回布尔值
   */
  private static Boolean chooseBoolean(Boolean mapper, Boolean method) {
    if (mapper == null && method == null) {
      return null;
    }
    if (method != null) {
      return method;
    }
    return mapper;
  }

  /**
   * mapper#method 上的注解 优先级大于 mapper 上的注解
   *
   * @param mapper 注解
   * @param method 方法
   * @return map
   */
  private static Map<String, Boolean> chooseOthers(Map<String, Boolean> mapper, Map<String, Boolean> method) {
    boolean emptyMapper = CollectUtils.isEmpty(mapper);
    boolean emptyMethod = CollectUtils.isEmpty(method);
    if (emptyMapper && emptyMethod) {
      return null;
    }
    if (emptyMapper) {
      return method;
    }
    if (emptyMethod) {
      return mapper;
    }
    Set<String> mapperKeys = mapper.keySet();
    Set<String> methodKeys = method.keySet();
    Set<String> keys = new HashSet<>(mapperKeys.size() + methodKeys.size());
    keys.addAll(methodKeys);
    keys.addAll(mapperKeys);
    Map<String, Boolean> map = CollectUtils.newHashMapWithExpectedSize(keys.size());
    methodKeys.forEach(k -> map.put(k, chooseBoolean(mapper.get(k), method.get(k))));
    return map;
  }
}
