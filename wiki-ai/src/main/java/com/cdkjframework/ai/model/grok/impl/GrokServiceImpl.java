package com.cdkjframework.ai.model.grok.impl;

import com.cdkjframework.ai.core.AiConfig;
import com.cdkjframework.ai.core.Message;
import com.cdkjframework.ai.core.impl.BaseAiService;
import com.cdkjframework.ai.model.grok.GrokService;
import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.ThreadUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.cdkjframework.ai.constant.AiConstant.*;
import static com.cdkjframework.ai.constant.AiConstant.Grok.*;
import static com.cdkjframework.ai.constant.AiConstant.MESSAGES;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.grok.impl
 * @ClassName: GrokServiceImpl
 * @Description: Grok服务
 * @Author: xiaLin
 * @Version: 1.0
 */
public class GrokServiceImpl extends BaseAiService implements GrokService {

  /**
   * 构造函数
   *
   * @param config AI 服务接口
   */
  public GrokServiceImpl(AiConfig config) {
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
    String paramJson = buildChatRequestBody(messages);
    return post(CHAT_ENDPOINT, paramJson).toString();
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
    ThreadUtils.newThread(() -> postStream(CHAT_ENDPOINT, paramMap, callback), GROK_CHAT_SSE).start();
  }

  /**
   * 创建消息回复
   *
   * @param messages messages 由对话组成的消息列表。如系统人设，背景信息等，用户自定义的信息
   * @param maxToken 最大token
   * @return AI回复消息
   */
  @Override
  public String message(List<Message> messages, int maxToken) {
    String paramJson = buildMessageRequestBody(messages, maxToken);
    return post(MESSAGES, paramJson).toString();
  }

  /**
   * 创建消息回复-SSE流式输出
   *
   * @param messages messages 由对话组成的消息列表。如系统人设，背景信息等，用户自定义的信息
   * @param maxToken 最大token
   * @param callback 流式数据回调函数
   */
  @Override
  public void message(List<Message> messages, int maxToken, Consumer<ResponseBuilder> callback) {
    Map<String, Object> paramMap = buildMessageStreamRequestBody(messages, maxToken);
    ThreadUtils.newThread(() -> postStream(MESSAGES, paramMap, callback), GROK_MESSAGE_SSE).start();
  }

  /**
   * 图像理解：模型会依据传入的图片信息以及问题，给出回复。
   *
   * @param prompt 题词
   * @param images 图片列表/或者图片Base64编码图片列表(URI形式)
   * @param detail 手动设置图片的质量，取值范围high、low、auto,默认为auto
   * @return AI回复消息
   */
  @Override
  public String chatVision(String prompt, List<String> images, String detail) {
    String paramJson = buildChatVisionRequestBody(prompt, images, detail);
    return post(CHAT_ENDPOINT, paramJson).toString();
  }

  /**
   * 图像理解-SSE流式输出
   *
   * @param prompt   题词
   * @param images   图片列表/或者图片Base64编码图片列表(URI形式)
   * @param detail   手动设置图片的质量，取值范围high、low、auto,默认为auto
   * @param callback 流式数据回调函数
   */
  @Override
  public void chatVision(String prompt, List<String> images, String detail, Consumer<ResponseBuilder> callback) {
    Map<String, Object> paramMap = buildChatVisionStreamRequestBody(prompt, images, detail);
    ThreadUtils.newThread(() -> postStream(CHAT_ENDPOINT, paramMap, callback), GROK_CHAT_VISION_SSE).start();
  }

  /**
   * 列出所有model列表
   *
   * @return model列表
   */
  @Override
  public String models() {
    return get(MODELS_ENDPOINT).toString();
  }

  /**
   * 获取模型信息
   *
   * @param modelId model ID
   * @return model信息
   */
  @Override
  public String findModel(String modelId) {
    return get(MODELS_ENDPOINT + StringUtils.BACKSLASH + modelId).toString();
  }

  /**
   * 列出所有语言model
   *
   * @return languageModel列表
   */
  @Override
  public String languageModels() {
    return get(LANGUAGE_MODELS).toString();
  }

  /**
   * 获取语言模型信息
   *
   * @param modelId model ID
   * @return model信息
   */
  @Override
  public String findLanguageModel(String modelId) {
    return get(LANGUAGE_MODELS + StringUtils.BACKSLASH + modelId).toString();
  }

  /**
   * 分词：可以将文本转换为模型可理解的 token 信息
   *
   * @param text 需要分词的内容
   * @return 分词结果
   */
  @Override
  public String tokenizeText(String text) {
    String paramJson = buildTokenizeRequestBody(text);
    return post(TOKENIZE_TEXT, paramJson).toString();
  }

  /**
   * 从延迟对话中获取结果
   *
   * @param requestId 延迟对话中的延迟请求ID
   * @return AI回复消息
   */
  @Override
  public String deferredCompletion(String requestId) {
    return get(DEFERRED_COMPLETION + StringUtils.BACKSLASH + requestId).toString();
  }

  /**
   * 文生图
   * 请设置config中model为支持图片功能的模型，目前支持GROK_2_IMAGE
   *
   * @param prompt 题词
   * @return 包含生成图片的url
   */
  @Override
  public String imagesGenerations(String prompt) {
    String paramJson = buildImagesGenerationsRequestBody(prompt);
    return post(IMAGES_GENERATIONS, paramJson).toString();
  }

  /**
   * 构建chat请求体
   *
   * @param messages 消息列表
   * @return 返回消息字符串
   */
  private String buildChatRequestBody(final List<Message> messages) {
    final Map<String, Object> paramMap = new HashMap<>();
    buildRequestBody(Boolean.FALSE, paramMap, messages);
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
    buildRequestBody(Boolean.TRUE, paramMap, messages);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    return paramMap;
  }

  /**
   * 构建消息回复请求体
   *
   * @param messages 消息
   * @param maxToken 最大token
   * @return 请求体字符串
   */
  private String buildMessageRequestBody(final List<Message> messages, int maxToken) {
    final Map<String, Object> paramMap = new HashMap<>();
    buildRequestBody(Boolean.FALSE, paramMap, messages);
    paramMap.put(MAX_TOKENS, maxToken);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // 转换成json字符串
    return JsonUtils.objectToJsonString(paramMap);
  }

  private Map<String, Object> buildMessageStreamRequestBody(final List<Message> messages, int maxToken) {
    final Map<String, Object> paramMap = new HashMap<>();
    buildRequestBody(Boolean.TRUE, paramMap, messages);
    paramMap.put(MAX_TOKENS, maxToken);
    //合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    return paramMap;
  }

  /**
   * 构建请求体
   *
   * @param stream   是否流式
   * @param paramMap 参数集合
   * @param messages 聊天消息
   */
  private void buildRequestBody(boolean stream, final Map<String, Object> paramMap, final List<Message> messages) {
    if (stream) {
      paramMap.put(STREAM, Boolean.TRUE);
    }
    paramMap.put(MODEL, config.getModel());
    paramMap.put(MESSAGES, messages);
  }


  /**
   * 构建chatVision请求体
   *
   * @param prompt 提示语
   * @param images 图片
   * @param detail 详情
   * @return 请求体
   */
  private String buildChatVisionRequestBody(String prompt, final List<String> images, String detail) {
    // 使用JSON工具
    final Map<String, Object> paramMap = buildRequestBody(Boolean.FALSE, prompt, images, detail);

    // JSON序列化
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建chatVisionStream请求体
   *
   * @param prompt 提词
   * @param images 图片
   * @param detail 描述
   * @return 请求体集合
   */
  private Map<String, Object> buildChatVisionStreamRequestBody(String prompt, final List<String> images, String detail) {
    // 使用JSON工具
    final Map<String, Object> paramMap = buildRequestBody(Boolean.TRUE, prompt, images, detail);
    ;
    return paramMap;
  }


  /**
   * 构建请求体
   *
   * @param stream 是否流式
   * @param prompt 提词
   * @param images 图片列表
   * @param value  图片质量
   * @return 请求体集合
   */
  private Map<String, Object> buildRequestBody(boolean stream, String prompt, final List<String> images, String value) {
    // 定义消息结构
    final List<Message> messages = new ArrayList<>();
    final List<Object> content = getObjects(prompt, images, value);

    messages.add(new Message(USER, content));

    // 使用JSON工具
    final Map<String, Object> paramMap = new HashMap<>();
    if (stream) {
      paramMap.put(STREAM, true);
    }
    paramMap.put(MODEL, config.getModel());
    paramMap.put(MESSAGES, messages);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());
    return paramMap;
  }

  /**
   * 获取图片内容
   *
   * @param prompt 提词
   * @param images 图片列表
   * @param detail 图片质量
   * @return 图片内容
   */
  private static List<Object> getObjects(String prompt, List<String> images, String detail) {
    final List<Object> content = new ArrayList<>();
    final Map<String, String> contentMap = new HashMap<>();
    contentMap.put(TYPE, TEXT);
    contentMap.put(TEXT, prompt);
    content.add(contentMap);
    for (String img : images) {
      HashMap<String, Object> imgUrlMap = new HashMap<>();
      imgUrlMap.put(TYPE, IMAGE_URL);
      HashMap<String, String> urlMap = new HashMap<>();
      urlMap.put(URL, img);
      urlMap.put(DETAIL, detail);
      imgUrlMap.put(IMAGE_URL, urlMap);
      content.add(imgUrlMap);
    }
    return content;
  }

  /**
   * 构建分词请求体
   *
   * @param text 文本
   * @return 请求体字符串
   */
  private String buildTokenizeRequestBody(String text) {
    // 使用JSON工具
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(TEXT, text);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // JSON 序列化
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建文生图请求体
   *
   * @param prompt 描述
   * @return JSON
   */
  private String buildImagesGenerationsRequestBody(String prompt) {
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(PROMPT, prompt);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // JSON 序列化
    return JsonUtils.objectToJsonString(paramMap);
  }
}
