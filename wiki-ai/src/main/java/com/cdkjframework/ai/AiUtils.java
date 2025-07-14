package com.cdkjframework.ai;

import com.cdkjframework.ai.core.AiConfig;
import com.cdkjframework.ai.core.AiService;
import com.cdkjframework.ai.core.Message;
import com.cdkjframework.ai.model.deepseek.DeepSeekService;
import com.cdkjframework.ai.model.doubao.DouBaoService;
import com.cdkjframework.ai.model.grok.GrokService;
import com.cdkjframework.ai.model.openai.OpenaiService;

import java.util.List;

/**
 * AI 工具类
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai
 * @ClassName: AiUtils
 * @Description: AI 工具类
 * @Author: xiaLin
 * @Version: 1.0
 */
public class AiUtils {


  /**
   * 获取AI模型服务，每个大模型提供的功能会不一样，可以调用此方法指定不同AI服务类，调用不同的功能
   *
   * @param config 创建的AI服务模型的配置
   * @param clazz  AI模型服务类
   * @param <T>    AiService实现类
   * @return 返回 AI 模型服务类 的实现类实例
   */
  public static <T extends AiService> T findAiService(final AiConfig config, final Class<T> clazz) {
    return AiFactory.findAiService(config, clazz);
  }

  /**
   * 获取AI模型服务
   *
   * @param config 创建的AI服务模型的配置
   * @return 返回 AI 服务模型 其中只有公共方法
   */
  public static AiService findAiService(final AiConfig config) {
    return findAiService(config, AiService.class);
  }

  /**
   * 获取DeepSeek模型服务
   *
   * @param config 创建的AI服务模型的配置
   * @return DeepSeekService
   */
  public static DeepSeekService findDeepSeekService(final AiConfig config) {
    return findAiService(config, DeepSeekService.class);
  }

  /**
   * 获取DouBao模型服务
   *
   * @param config 创建的AI服务模型的配置
   * @return DouBaoService
   */
  public static DouBaoService findDouBaoService(final AiConfig config) {
    return findAiService(config, DouBaoService.class);
  }

  /**
   * 获取Grok模型服务
   *
   * @param config 创建的AI服务模型的配置
   * @return GrokService
   */
  public static GrokService findGrokService(final AiConfig config) {
    return findAiService(config, GrokService.class);
  }

  /**
   * 获取Openai模型服务
   *
   * @param config 创建的AI服务模型的配置
   * @return OpenAiService
   */
  public static OpenaiService findOpenAiService(final AiConfig config) {
    return findAiService(config, OpenaiService.class);
  }

  /**
   * AI大模型对话功能（公共）
   *
   * @param config 创建的AI服务模型的配置
   * @param prompt 需要对话的内容
   * @return 返回 AI模型返回消息内容
   */
  public static String chat(final AiConfig config, final String prompt) {
    return findAiService(config).chat(prompt);
  }

  /**
   * AI大模型对话功能（公共）
   *
   * @param config   创建的AI服务模型的配置
   * @param messages 由目前为止的对话组成的消息列表，可以设置role，content。详细参考官方文档
   * @return 返回 AI模型返回消息内容
   */
  public static String chat(final AiConfig config, final List<Message> messages) {
    return findAiService(config).chat(messages);
  }
}
