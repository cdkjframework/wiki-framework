package com.cdkjframework.ai.model.openai.impl;

import com.cdkjframework.ai.constant.AiCommon;
import com.cdkjframework.ai.core.AiConfig;
import com.cdkjframework.ai.core.Message;
import com.cdkjframework.ai.core.impl.BaseAiService;
import com.cdkjframework.ai.model.openai.OpenaiService;
import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.ThreadUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.cdkjframework.ai.constant.AiConstant.*;
import static com.cdkjframework.ai.constant.AiConstant.Openai.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.openai.impl
 * @ClassName: OpenaiServiceImpl
 * @Description: Openai服务
 * @Author: xiaLin
 * @Version: 1.0
 */
public class OpenaiServiceImpl extends BaseAiService implements OpenaiService {

  /**
   * 构造函数
   *
   * @param config 配置信息
   */
  public OpenaiServiceImpl(AiConfig config) {
    super(config);
  }

  /**
   * 聊天
   *
   * @param messages 消息列表
   * @return 聊天结果
   */
  @Override
  public String chat(final List<Message> messages) {
    String paramJson = buildChatRequestBody(messages);
    return post(CHAT_ENDPOINT, paramJson).toString();
  }

  /**
   * 聊天
   *
   * @param messages 聊天消息列表
   * @param callback 回调函数
   */
  @Override
  public void chat(List<Message> messages, Consumer<ResponseBuilder> callback) {
    Map<String, Object> paramMap = buildChatStreamRequestBody(messages);
    ThreadUtils.newThread(() -> postStream(CHAT_ENDPOINT, paramMap, callback), OPENAI_CHAT_SSE).start();
  }

  /**
   * 聊天
   *
   * @param prompt 题词
   * @param images 图片列表/或者图片Base64编码图片列表(URI形式)
   * @param detail 手动设置图片的质量，取值范围high、low、auto,默认为auto
   * @return 聊天结果
   */
  @Override
  public String chatVision(String prompt, final List<String> images, String detail) {
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
    ThreadUtils.newThread(() -> postStream(CHAT_ENDPOINT, paramMap, callback), OPENAI_CHAT_VISION_SSE).start();
  }

  /**
   * 文生图 请设置config中model为支持图片功能的模型 DALL·E系列
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
   * 图片编辑 该方法仅支持 DALL·E 2 model
   *
   * @param prompt 题词
   * @param image  需要编辑的图像必须是 PNG 格式
   * @param mask   如果提供，则是一个与编辑图像大小相同的遮罩图像应该是灰度图，白色表示需要编辑的区域，黑色表示不需要编辑的区域。
   * @return 包含生成图片的url
   */
  @Override
  public String imagesEdits(String prompt, File image, File mask) {
    final Map<String, Object> paramMap = buildImagesEditsRequestBody(prompt, image, mask);
    return postFormData(IMAGES_EDITS, paramMap).toString();
  }

  /**
   * 图片变形 该方法仅支持 DALL·E 2 model
   *
   * @param image 需要变形的图像必须是 PNG 格式
   * @return 包含生成图片的url
   */
  @Override
  public String imagesVariations(File image) {
    final Map<String, Object> paramMap = buildImagesVariationsRequestBody(image);
    return postFormData(IMAGES_VARIATIONS, paramMap).toString();
  }

  /**
   * TTS文本转语音 请设置config中model为支持TTS功能的模型 TTS系列
   *
   * @param input 需要转成语音的文本
   * @param voice AI的音色
   * @return 返回的音频mp3文件流
   */
  @Override
  public InputStream textToSpeech(String input, AiCommon.OpenaiSpeech voice) {
    String paramJson = buildTTSRequestBody(input, voice.getVoice());
    return postStream(TTS, paramJson);
  }

  /**
   * STT音频转文本 请设置config中model为支持STT功能的模型 whisper
   *
   * @param file 需要转成文本的音频文件
   * @return 返回的文本内容
   */
  @Override
  public String speechToText(File file) {
    final Map<String, Object> paramMap = buildSTTRequestBody(file);
    return postFormData(STT, paramMap).toString();
  }

  /**
   * 文本向量化 请设置config中model为支持文本向量化功能的模型 text-embedding系列
   *
   * @param input 需要向量化的内容
   * @return 处理后的向量信息
   */
  @Override
  public String embeddingText(String input) {
    String paramJson = buildEmbeddingTextRequestBody(input);
    return post(EMBEDDINGS, paramJson).toString();
  }

  /**
   * 检查文本或图像是否具有潜在的危害性
   * 仅支持omni-moderation-latest和text-moderation-latest模型
   *
   * @param text   需要检查的文本
   * @param imgUrl 需要检查的图片地址
   * @return AI回复消息
   */
  @Override
  public String moderations(String text, String imgUrl) {
    String paramJson = buildModerationsRequestBody(text, imgUrl);
    return post(MODERATIONS, paramJson).toString();
  }

  /**
   * 推理chat
   * 支持o3-mini和o1
   *
   * @param messages        消息列表
   * @param reasoningEffort 推理程度
   * @return AI回复消息
   */
  @Override
  public String chatReasoning(List<Message> messages, String reasoningEffort) {
    String paramJson = buildChatReasoningRequestBody(messages, reasoningEffort);
    return post(CHAT_ENDPOINT, paramJson).toString();
  }

  /**
   * 推理chat-SSE流式输出
   * 支持o3-mini和o1
   *
   * @param messages        消息列表
   * @param reasoningEffort 推理程度
   * @param callback        流式数据回调函数
   */
  @Override
  public void chatReasoning(List<Message> messages, String reasoningEffort, Consumer<ResponseBuilder> callback) {
    Map<String, Object> paramMap = buildChatReasoningStreamRequestBody(messages, reasoningEffort);
    ThreadUtils.newThread(() -> postStream(CHAT_ENDPOINT, paramMap, callback), OPENAI_CHAT_REASONING_SSE).start();
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
   * @param detail 详细程度
   * @return 请求体
   */
  private String buildChatVisionRequestBody(String prompt, final List<String> images, String detail) {
    // 使用JSON工具
    final Map<String, Object> paramMap = buildRequestBody(Boolean.FALSE, prompt, images, detail);
    // JSON  转字符串
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建图片生成请求体
   *
   * @param prompt 描述
   * @param images 图片
   * @param detail 详细程度
   * @return 请求体
   */
  private Map<String, Object> buildChatVisionStreamRequestBody(String prompt, final List<String> images, String detail) {
    // 返回参数
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
   * 构建文生图请求体
   *
   * @param prompt 提词
   * @return JSON
   */
  private String buildImagesGenerationsRequestBody(String prompt) {
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(PROMPT, prompt);
    //合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // JSON 序列化
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建图片编辑请求体
   *
   * @param prompt 提词
   * @param image  图片
   * @param mask   蒙版
   * @return JSON
   */
  private Map<String, Object> buildImagesEditsRequestBody(String prompt, final File image, final File mask) {
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(PROMPT, prompt);
    paramMap.put(IMAGE, image);
    if (mask != null) {
      paramMap.put(MASK, mask);
    }
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // 返回结果
    return paramMap;
  }


  /**
   * 构建图片变形请求体
   *
   * @param image 图片
   * @return JSON
   */
  private Map<String, Object> buildImagesVariationsRequestBody(final File image) {
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(IMAGE, image);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // JSON 序列化
    return paramMap;
  }

  /**
   * 构建TTS请求体
   *
   * @param input 文本
   * @param voice 语音文字
   * @return JSON
   */
  private String buildTTSRequestBody(String input, String voice) {
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(INPUT, input);
    paramMap.put(VOICE, voice);
    //合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // 返回JSON
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建STT请求体
   *
   * @param file 文件
   * @return JSON
   */
  private Map<String, Object> buildSTTRequestBody(final File file) {
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(FILE, file);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // JSON 序列化
    return paramMap;
  }

  /**
   * 构建文本向量化请求体
   *
   * @param input 文本向量化
   * @return 请求体字符串
   */
  private String buildEmbeddingTextRequestBody(String input) {
    //使用JSON工具
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(INPUT, input);
    //合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // JSON序列化
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建检查图片或文字请求体
   *
   * @param text   文本
   * @param imgUrl 图片地址
   * @return JSON
   */
  private String buildModerationsRequestBody(String text, String imgUrl) {
    // 使用JSON工具
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
    if (StringUtils.isNotNullAndEmpty(imgUrl)) {
      final Map<String, Object> imgUrlMap = new HashMap<>();
      imgUrlMap.put(TYPE, IMAGE_URL);
      final Map<String, String> urlMap = new HashMap<>();
      urlMap.put(URL, imgUrl);
      imgUrlMap.put(IMAGE_URL, urlMap);
      input.add(imgUrlMap);
    }

    paramMap.put(INPUT, input);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // 返回结果
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建推理请求体
   *
   * @param messages        消息
   * @param reasoningEffort 理解努力
   * @return 请求体字符串
   */
  private String buildChatReasoningRequestBody(final List<Message> messages, String reasoningEffort) {
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(MODEL, config.getModel());
    paramMap.put(MESSAGES, messages);
    paramMap.put(REASONING_EFFORT, reasoningEffort);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // 返回请求体字符串
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建请求体
   *
   * @param messages        聊天消息
   * @param reasoningEffort 理解程度
   * @return 请求体字符串
   */
  private Map<String, Object> buildChatReasoningStreamRequestBody(final List<Message> messages, String reasoningEffort) {
    final Map<String, Object> paramMap = new HashMap<>();
    paramMap.put(STREAM, true);
    paramMap.put(MODEL, config.getModel());
    paramMap.put(MESSAGES, messages);
    paramMap.put(REASONING_EFFORT, reasoningEffort);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // 构建请求体
    return paramMap;
  }
}
