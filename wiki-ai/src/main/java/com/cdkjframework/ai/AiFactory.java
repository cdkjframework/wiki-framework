package com.cdkjframework.ai;

import com.cdkjframework.ai.core.AiConfig;
import com.cdkjframework.ai.core.AiProvider;
import com.cdkjframework.ai.core.AiService;
import com.cdkjframework.exceptions.GlobalRuntimeException;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建 AiFactory 工厂类
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai
 * @ClassName: AiFactory
 * @Description: 创建 AiFactory 工厂类
 * @Author: xiaLin
 * @Version: 1.0
 */
public class AiFactory {
  /**
   * 创建 AiFactory 工厂类
   */
  private static final Map<String, AiProvider> PROVIDERS = new ConcurrentHashMap<>();

  // 加载所有 AiProvider 实现类
  static {
    final ServiceLoader<AiProvider> loader = ServiceLoader.load(AiProvider.class);
    for (final AiProvider provider : loader) {
      PROVIDERS.put(provider.getServiceName().toLowerCase(), provider);
    }
  }


  /**
   * 获取AI服务
   *
   * @param config AI 配置
   * @return AI服务实例
   */
  public static AiService findAiService(final AiConfig config) {
    return findAiService(config, AiService.class);
  }

  /**
   * 获取AI服务
   *
   * @param config Ai 配置
   * @param clazz  AI服务类
   * @param <T>    AI服务类
   * @return clazz对应的AI服务类实例
   */
  @SuppressWarnings("unchecked")
  public static <T extends AiService> T findAiService(final AiConfig config, final Class<T> clazz) {
    final AiProvider provider = PROVIDERS.get(config.getModelName().toLowerCase());
    if (provider == null) {
      throw new IllegalArgumentException("不支持的模型: " + config.getModelName());
    }

    final AiService service = provider.create(config);
    if (!clazz.isInstance(service)) {
      throw new GlobalRuntimeException("模型服务不属于指定类型: " + clazz.getSimpleName());
    }

    // 返回服务
    return (T) service;
  }
}
