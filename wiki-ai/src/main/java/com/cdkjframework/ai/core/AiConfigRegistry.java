package com.cdkjframework.ai.core;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AI 配置注册
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.core
 * @ClassName: AiConfigRegistry
 * @Description: AI 配置注册
 * @Author: xiaLin
 * @Version: 1.0
 */
public class AiConfigRegistry {
  /**
   * AI 配置注册
   */
  private static final Map<String, Class<? extends AiConfig>> CONFIG_CLAZZ = new ConcurrentHashMap<>();

  static {
    // 加载所有 AiConfig 实现类
    final ServiceLoader<AiConfig> loader = ServiceLoader.load(AiConfig.class);
    for (final AiConfig config : loader) {
      CONFIG_CLAZZ.put(config.getModelName().toLowerCase(), config.getClass());
    }
  }

  /**
   * 通过模型名称获取 AI 配置类
   *
   * @param modelName 模型名称
   * @return 配置类
   */
  public static Class<? extends AiConfig> getConfigClass(final String modelName) {
    return CONFIG_CLAZZ.get(modelName.toLowerCase());
  }
}
