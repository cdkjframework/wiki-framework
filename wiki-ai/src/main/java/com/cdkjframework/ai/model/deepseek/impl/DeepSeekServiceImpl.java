package com.cdkjframework.ai.model.deepseek.impl;

import com.cdkjframework.ai.core.AiConfig;
import com.cdkjframework.ai.core.Message;
import com.cdkjframework.ai.core.impl.BaseAiService;
import com.cdkjframework.ai.model.deepseek.DeepSeekService;
import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.ThreadUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.cdkjframework.ai.constant.AiConstant.*;
import static com.cdkjframework.ai.constant.AiConstant.DeepSeek.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.deepseek.impl
 * @ClassName: DeepSeekServiceImpl
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public class DeepSeekServiceImpl extends BaseAiService implements DeepSeekService {
  /**
   * 构造函数
   *
   * @param config AI 服务接口
   */
  public DeepSeekServiceImpl(AiConfig config) {
    super(config);
  }

  /**
   * 对话
   * messages 可以由当前对话组成的消息列表，可以设置role，content。详细参考官方文档
   *
   * @param messages 消息列表
   * @return 返回AI回复的消息
   */
  @Override
  public String chat(List<Message> messages) {
    final String paramJson = buildChatRequestBody(messages);
    // 发送POST请求
    final StringBuilder response = post(CHAT_ENDPOINT, paramJson);
    // 返回结果
    return response.toString();
  }

  /**
   * 对话-SSE 流式输出
   * messages 可以由当前对话组成的消息列表，可以设置role，content。详细参考官方文档
   *
   * @param messages 消息列表
   * @param callback 流式数据回调函数
   */
  @Override
  public void chat(List<Message> messages, Consumer<ResponseBuilder> callback) {
    Map<String, Object> paramMap = buildChatStreamRequestBody(messages);
    ThreadUtils.newThread(() -> postStream(CHAT_ENDPOINT, paramMap, callback), DEEPSEEK_CHAT_SSE).start();
  }

  /**
   * 模型beta功能
   *
   * @param prompt 题词
   * @return AI回复消息
   */
  @Override
  public String beta(String prompt) {
    final String paramJson = buildBetaRequestBody(prompt);
    return post(CHAT_ENDPOINT, paramJson).toString();
  }

  /**
   * 模型beta功能-SSE流式输出
   *
   * @param prompt   题词
   * @param callback 流式数据回调函数
   */
  @Override
  public void beta(String prompt, Consumer<ResponseBuilder> callback) {
    Map<String, Object> paramMap = buildBetaStreamRequestBody(prompt);
    ThreadUtils.newThread(() -> postStream(BETA_ENDPOINT, paramMap, callback), DEEPSEEK_BETA_SSE).start();
  }

  /**
   * 列出所有模型列表
   *
   * @return model列表
   */
  @Override
  public String models() {
    return get(MODELS_ENDPOINT).toString();
  }

  /**
   * 查询余额
   *
   * @return 余额
   */
  @Override
  public String balance() {
    return get(BALANCE_ENDPOINT).toString();
  }


  /**
   * 构建chat请求体
   *
   * @param messages 消息列表
   * @return 返回消息字符串
   */
  private String buildChatRequestBody(final List<Message> messages) {
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(MESSAGES, messages);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // JSON 序列化
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建chatStream请求体
   *
   * @param messages 消息列表
   * @return 返回消息集合
   */
  private Map<String, Object> buildChatStreamRequestBody(final List<Message> messages) {
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(STREAM, Boolean.TRUE);
    paramMap.put(MODEL, config.getModel());
    paramMap.put(MESSAGES, messages);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    return paramMap;
  }

  /**
   * 构建beta请求体
   *
   * @param prompt 提词
   * @return 请求体字符串
   */
  private String buildBetaRequestBody(final String prompt) {
    // 定义消息结构
    //使用JSON工具
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(PROMPT, prompt);
    //合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建betaStream请求体
   *
   * @param prompt 提词
   * @return 请求体集合
   */
  private Map<String, Object> buildBetaStreamRequestBody(final String prompt) {
    // 使用JSON工具
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(STREAM, Boolean.TRUE);
    paramMap.put(MODEL, config.getModel());
    paramMap.put(PROMPT, prompt);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // 返回结果
    return paramMap;
  }

}
