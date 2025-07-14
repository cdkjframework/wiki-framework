package com.cdkjframework.ai.model.doubao.impl;

import com.cdkjframework.ai.constant.AiCommon;
import com.cdkjframework.ai.core.AiConfig;
import com.cdkjframework.ai.core.Message;
import com.cdkjframework.ai.core.impl.BaseAiService;
import com.cdkjframework.ai.model.doubao.DouBaoService;
import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.ThreadUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.cdkjframework.ai.constant.AiConstant.DouBao.*;
import static com.cdkjframework.ai.constant.AiConstant.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.doubao.impl
 * @ClassName: DouBaoServiceImpl
 * @Description: 豆包服务实现类
 * @Author: xiaLin
 * @Version: 1.0
 */
public class DouBaoServiceImpl extends BaseAiService implements DouBaoService {
  /**
   * 构造函数
   *
   * @param config AI 服务接口
   */
  public DouBaoServiceImpl(AiConfig config) {
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
    ThreadUtils.newThread(() -> postStream(CHAT_ENDPOINT, paramMap, callback), DOUBAO_CHAT_SSE).start();
  }

  /**
   * 图像理解：模型会依据传入的图片信息以及问题，给出回复。
   *
   * @param prompt 提问
   * @param images 图片列表/或者图片Base64编码图片列表(URI形式)
   * @param value  手动设置图片的质量，取值范围high、low、auto,默认为auto
   * @return AI回答内容
   */
  @Override
  public String chatVision(String prompt, List<String> images, String value) {
    // 构建请求体
    String paramJson = buildChatVisionRequestBody(prompt, images, value);
    return post(CHAT_ENDPOINT, paramJson).toString();
  }

  /**
   * 图像理解-SSE流式输出
   *
   * @param prompt   提问
   * @param images   传入的图片列表地址/或者图片Base64编码图片列表(URI形式)
   * @param value    手动设置图片的质量，取值范围high、low、auto,默认为auto
   * @param callback 流式数据回调函数
   */
  @Override
  public void chatVision(String prompt, List<String> images, String value, Consumer<ResponseBuilder> callback) {
    // 构建请求体
    Map<String, Object> paramMap = buildChatVisionStreamRequestBody(prompt, images, value);
    ThreadUtils.newThread(() -> postStream(CHAT_ENDPOINT, paramMap, callback), DOUBAO_CHAT_VISION_SSE).start();
  }

  /**
   * 创建视频生成任务
   * 注意：调用该方法时，配置config中的model为您创建的推理接入点（Endpoint）ID。详细参考官方文档
   *
   * @param text        文本提示词
   * @param image       图片/或者图片Base64编码图片(URI形式)
   * @param videoParams 视频参数列表
   * @return 生成任务id
   */
  @Override
  public String videoTasks(String text, String image, List<AiCommon.DouBaoVideo> videoParams) {
    String paramJson = buildGenerationsTasksRequestBody(text, image, videoParams);
    return post(CREATE_VIDEO, paramJson).toString();
  }

  /**
   * 查询视频生成任务信息
   *
   * @param taskId 通过创建生成视频任务返回的生成任务id
   * @return 生成任务信息
   */
  @Override
  public String findVideoTasksInfo(String taskId) {
    return get(CREATE_VIDEO + taskId).toString();
  }

  /**
   * 文本向量化
   *
   * @param input 需要向量化的内容列表，支持中文、英文
   * @return 处理后的向量信息
   */
  @Override
  public String embeddingText(String[] input) {
    String paramJson = buildEmbeddingTextRequestBody(input);
    return post(EMBEDDING_TEXT, paramJson).toString();
  }

  /**
   * 图文向量化：仅支持单一文本、单张图片或文本与图片的组合输入（即一段文本 + 一张图片），暂不支持批量文本 / 图片的同时处理
   *
   * @param text  需要向量化的内容
   * @param image 需要向量化的图片地址/或者图片Base64编码图片(URI形式)
   * @return 处理后的向量信息
   */
  @Override
  public String embeddingVision(String text, String image) {
    String paramJson = buildEmbeddingVisionRequestBody(text, image);
    return post(EMBEDDING_VISION, paramJson).toString();
  }

  /**
   * 应用(Bot) config中model设置为您创建的应用ID
   *
   * @param messages 由对话组成的消息列表。如系统人设，背景信息等，用户自定义的信息
   * @return AI回答内容
   */
  @Override
  public String botsChat(List<Message> messages) {
    String paramJson = buildChatRequestBody(messages);
    return post(BOTS_CHAT, paramJson).toString();
  }

  /**
   * 应用(Bot)-SSE流式输出 config中model设置为您创建的应用ID
   *
   * @param messages 由对话组成的消息列表。如系统人设，背景信息等，用户自定义的信息
   * @param callback 流式数据回调函数
   */
  @Override
  public void botsChat(List<Message> messages, Consumer<ResponseBuilder> callback) {
    Map<String, Object> paramMap = buildChatStreamRequestBody(messages);
    ThreadUtils.newThread(() -> postStream(BOTS_CHAT, paramMap, callback), DOUBAO_BOTS_CHAT_SSE).start();
  }

  /**
   * 分词：可以将文本转换为模型可理解的 token id，并返回文本的 tokens 数量、token id、 token 在原始文本中的偏移量等信息
   *
   * @param text 需要分词的内容列表
   * @return 分词结果
   */
  @Override
  public String tokenization(String[] text) {
    String paramJson = buildTokenizationRequestBody(text);
    return post(TOKENIZATION, paramJson).toString();
  }

  /**
   * 批量推理 Chat
   * 注意：调用该方法时，配置config中的model为您创建的批量推理接入点（Endpoint）ID。详细参考官方文档
   * 该方法不支持流式
   *
   * @param messages 由对话组成的消息列表。如系统人设，背景信息等，用户自定义的信息
   * @return AI回答内容
   */
  @Override
  public String batchChat(List<Message> messages) {
    String paramJson = buildChatRequestBody(messages);
    return post(BATCH_CHAT, paramJson).toString();
  }

  /**
   * 创建上下文缓存： 创建上下文缓存，获得缓存 id字段后，在上下文缓存对话 API中使用。
   * 注意：调用该方法时，配置config中的model为您创建的推理接入点（Endpoint）ID,
   * 推理接入点中使用的模型需要在模型管理中开启缓存功能。详细参考官方文档
   *
   * @param messages 由对话组成的消息列表。如系统人设，背景信息等，用户自定义的信息
   * @param mode     上下文缓存的类型,详细参考官方文档 默认为session
   * @return 返回的缓存id
   */
  @Override
  public String createContext(List<Message> messages, String mode) {
    String paramJson = buildCreateContextRequest(messages, mode);
    return post(CREATE_CONTEXT, paramJson).toString();
  }

  /**
   * 上下文缓存对话： 向大模型发起带上下文缓存的请求
   * 注意：配置config中的model可以为您创建的推理接入点（Endpoint）ID，也可以是支持chat的model
   *
   * @param messages  对话的信息  不支持最后一个元素的role设置为assistant。如使用session 缓存（mode设置为session）传入最新一轮对话的信息，无需传入历史信息
   * @param contextId 创建上下文缓存后获取的缓存id
   * @return AI回答内容
   */
  @Override
  public String chatContext(List<Message> messages, String contextId) {
    String paramJson = buildChatContentRequestBody(messages, contextId);
    return post(CHAT_CONTEXT, paramJson).toString();
  }

  /**
   * 上下文缓存对话-SSE流式输出
   * 注意：配置config中的model可以为您创建的推理接入点（Endpoint）ID，也可以是支持chat的model
   *
   * @param messages  对话的信息  不支持最后一个元素的role设置为assistant。如使用session 缓存（mode设置为session）传入最新一轮对话的信息，无需传入历史信息
   * @param contextId 创建上下文缓存后获取的缓存id
   * @param callback  流式数据回调函数
   */
  @Override
  public void chatContext(List<Message> messages, String contextId, Consumer<ResponseBuilder> callback) {
    Map<String, Object> paramMap = buildChatContentStreamRequestBody(messages, contextId);
    ThreadUtils.newThread(() -> postStream(CHAT_CONTEXT, paramMap, callback), DOUBAO_CHAT_CONTEXT_SSE).start();
  }

  /**
   * 文生图
   * 请设置config中model为支持图片功能的模型，目前支持 Doubao-Seedream-3.0-t2i
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
   * 构建chatVision请求体
   *
   * @param prompt 提词
   * @param images 图片列表
   * @param detail 图片质量
   * @return 请求体字符串
   */
  private String buildChatVisionRequestBody(String prompt, final List<String> images, String detail) {
    // 使用JSON工具
    Map<String, Object> paramMap = buildRequestBody(Boolean.FALSE, prompt, images, detail);
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建chatVisionStream请求体
   *
   * @param prompt 提词
   * @param images 图片列表
   * @param detail 图片质量
   * @return 请求体集合
   */
  private Map<String, Object> buildChatVisionStreamRequestBody(String prompt, final List<String> images, String detail) {
    //使用JSON工具
    return buildRequestBody(Boolean.TRUE, prompt, images, detail);
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
      paramMap.put(STREAM, stream);
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
   * 构建创建视频任务请求体
   *
   * @param text        文本
   * @param image       图片
   * @param videoParams 视频参数列表
   * @return 请求体字符串
   */
  private String buildGenerationsTasksRequestBody(String text, String image, final List<AiCommon.DouBaoVideo> videoParams) {
    // 使用JSON工具
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());

    final List<Object> content = new ArrayList<>();
    // 添加文本参数
    final Map<String, String> textMap = new HashMap<>();
    if (StringUtils.isNotNullAndEmpty(text)) {
      textMap.put(TYPE, TEXT);
      textMap.put(TEXT, text);
      content.add(textMap);
    }
    // 添加图片参数
    buildImages(content, text, image);

    // 添加视频参数
    if (videoParams != null && !videoParams.isEmpty()) {
      // 如果有文本参数就加在后面
      if (!textMap.isEmpty()) {
        int textIndex = content.indexOf(textMap);
        StringBuilder textBuilder = new StringBuilder(text);
        for (AiCommon.DouBaoVideo videoParam : videoParams) {
          textBuilder.append(StringUtils.SPACE)
              .append(videoParam.getType())
              .append(StringUtils.SPACE)
              .append(videoParam.getValue());
        }
        textMap.put(TYPE, TEXT);
        textMap.put(TEXT, textBuilder.toString());
        // 替换原来文本参数
        if (textIndex != IntegerConsts.MINUS_ONE) {
          content.set(textIndex, textMap);
        } else {
          content.add(textMap);
        }
      } else {
        // 如果没有文本参数就重新增加
        StringBuilder textBuilder = new StringBuilder();
        for (AiCommon.DouBaoVideo videoParam : videoParams) {
          textBuilder.append(videoParam.getType()).append(videoParam.getValue()).append(" ");
        }
        textMap.put(TYPE, TEXT);
        textMap.put(TEXT, textBuilder.toString());
        content.add(textMap);
      }
    }

    paramMap.put(CONTENT, content);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());
    return JsonUtils.objectToJsonString(paramMap);
  }


  /**
   * 构建文本向量化请求体
   *
   * @param input 文本向量化
   * @return 请求体字符串
   */
  private String buildEmbeddingTextRequestBody(String[] input) {
    // 使用JSON工具
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(INPUT, input);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建图文向量化请求体
   *
   * @param text  文本
   * @param image 图片
   * @return 请求体字符串
   */
  private String buildEmbeddingVisionRequestBody(String text, String image) {
    //使用JSON工具
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());

    final List<Object> input = new ArrayList<>();
    // 添加文本参数
    if (StringUtils.isNotNullAndEmpty(text)) {
      final Map<String, String> textMap = new HashMap<>();
      textMap.put(TYPE, TEXT);
      textMap.put(TEXT, text);
      input.add(textMap);
    }
    // 添加图片参数
    buildImages(input, text, image);

    paramMap.put(INPUT, input);
    //合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // 返回结果
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建图片内容
   *
   * @param text  文本
   * @param image 图片
   */
  private void buildImages(final List<Object> content, String text, String image) {
    if (StringUtils.isNullAndSpaceOrEmpty(image)) {
      return;
    }
    // 添加图片参数
    final Map<String, Object> imgUrlMap = new HashMap<>();
    imgUrlMap.put(TYPE, IMAGE_URL);
    final Map<String, String> urlMap = new HashMap<>();
    urlMap.put(URL, image);
    imgUrlMap.put(IMAGE_URL, urlMap);
    content.add(imgUrlMap);
  }

  /**
   * 构建分词请求体
   *
   * @param text 文本
   * @return 请求体字符串
   */
  private String buildTokenizationRequestBody(String[] text) {
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(TEXT, text);
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建创建上下文缓存请求体
   *
   * @param messages 消息
   * @param mode     模式
   * @return 请求体字符串
   */
  private String buildCreateContextRequest(final List<Message> messages, String mode) {
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MESSAGES, messages);
    paramMap.put(MODEL, config.getModel());
    paramMap.put(MODE, mode);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // JSON 序列化
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建上下文缓存对话请求体
   *
   * @param messages  对话消息
   * @param contextId 上下文ID
   * @return 请求体
   */
  private String buildChatContentRequestBody(final List<Message> messages, String contextId) {
    // 使用JSON工具
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(MESSAGES, messages);
    paramMap.put(CONTEXT_ID, contextId);
    //合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建请求体
   *
   * @param messages  消息集合
   * @param contextId 上下文ID
   * @return 请求体
   */
  private Map<String, Object> buildChatContentStreamRequestBody(final List<Message> messages, String contextId) {
    // 使用JSON工具
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(STREAM, true);
    paramMap.put(MODEL, config.getModel());
    paramMap.put(MESSAGES, messages);
    paramMap.put(CONTEXT_ID, contextId);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // JSON 序列化
    return paramMap;
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
