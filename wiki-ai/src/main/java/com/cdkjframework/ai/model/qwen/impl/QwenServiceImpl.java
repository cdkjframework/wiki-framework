package com.cdkjframework.ai.model.qwen.impl;

import com.cdkjframework.ai.core.AiConfig;
import com.cdkjframework.ai.core.Message;
import com.cdkjframework.ai.core.impl.BaseAiService;
import com.cdkjframework.ai.model.qwen.QwenService;
import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.ThreadUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.cdkjframework.ai.constant.AiConstant.*;
import static com.cdkjframework.ai.constant.AiConstant.Qwen.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.qwen.impl
 * @ClassName: QwenServiceImpl
 * @Description: Qwen AI 服务实现类
 * @Author: xiaLin
 * @Date: 2025/8/3 9:08
 * @Version: 1.0
 */
public class QwenServiceImpl extends BaseAiService implements QwenService {

  /**
   * 构造函数
   *
   * @param config AI 服务接口
   */
  public QwenServiceImpl(AiConfig config) {
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
    final String paramJson = buildChatRequestBody(messages, Boolean.FALSE);
    // 发送POST请求
    final StringBuilder response = post(CHAT_ENDPOINT, paramJson);
    // 返回结果
    return response.toString();
  }

  /**
   * 对话-检索增强
   * messages 可以由当前对话组成的消息列表，可以设置role，content。详细参考官方文档
   *
   * @param messages 消息列表
   * @return 返回AI回复的消息
   */
  @Override
  public String chatSearch(List<Message> messages) {
    final String paramJson = buildChatRequestBody(messages, Boolean.TRUE);
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
    Map<String, Object> paramMap = buildChatStreamRequestBody(messages, Boolean.FALSE);
    ThreadUtils.newThread(() -> postStream(CHAT_ENDPOINT, paramMap, callback), QWEN_CHAT_SSE).start();
  }

  /**
   * 对话-SSE 流式输出
   * messages 可以由当前对话组成的消息列表，可以设置role，content。详细参考官方文档
   *
   * @param messages 消息列表
   * @param callback 流式数据回调函数
   */
  @Override
  public void chatSearch(List<Message> messages, Consumer<ResponseBuilder> callback) {
    Map<String, Object> paramMap = buildChatStreamRequestBody(messages, Boolean.TRUE);
    ThreadUtils.newThread(() -> postStream(CHAT_ENDPOINT, paramMap, callback), QWEN_CHAT_SSE).start();
  }

  /**
   * 对话-检索增强
   * messages 可以由当前对话组成的消息列表，可以设置role，content。详细参考官方文档
   *
   * @param prompts 消息列表
   * @return 返回AI回复的消息
   */

  @Override
  public String chatVision(List<String> prompts, List<String> images) {
    // 构建请求体
    String paramJson = buildChatVisionRequestBody(prompts, images);
    return post(CHAT_ENDPOINT, paramJson).toString();
  }

  /**
   * 对话 VISION SSE 流式输出
   * messages 可以由当前对话组成的消息列表，可以设置role，content。详细参考官方文档
   *
   * @param prompts   提词
   * @param images   图片列表
   * @param callback 流式数据回调函数
   */
  @Override
  public void chatVision(List<String> prompts, List<String> images, Consumer<ResponseBuilder> callback) {
    // 构建请求体
    Map<String, Object> paramMap = buildChatVisionStreamRequestBody(prompts, images);
    ThreadUtils.newThread(() -> postStream(CHAT_ENDPOINT, paramMap, callback), QWEN_CHAT_VISION_SSE).start();
  }

  /**
   * 图像理解：模型会依据传入的图片信息以及问题，给出回复。
   *
   * @param prompts 提问
   * @param videos  视频列表(URI形式)
   * @return AI回答内容
   */
  @Override
  public String chatVideoVision(List<String> prompts, List<List<String>> videos) {
    // 构建请求体
    String paramJson = buildChatVideoRequestBody(prompts, videos);
    return post(CHAT_ENDPOINT, paramJson).toString();
  }

  /**
   * 图像理解-SSE流式输出
   *
   * @param prompts  提问
   * @param videos   视频列表(URI形式)
   * @param callback 流式数据回调函数
   */
  @Override
  public void chatVideoVision(List<String> prompts, List<List<String>> videos, Consumer<ResponseBuilder> callback) {
    // 构建请求体
    Map<String, Object> paramMap = buildChatVideoStreamRequestBody(prompts, videos);
    ThreadUtils.newThread(() -> postStream(CHAT_ENDPOINT, paramMap, callback), QWEN_CHAT_VISION_SSE).start();
  }

  /**
   * 构建chat请求体
   *
   * @param messages 消息列表
   * @param search   是否搜索
   * @return 返回消息字符串
   */
  private String buildChatRequestBody(final List<Message> messages, boolean search) {
    final Map<String, Object> paramMap = new HashMap<>();
    buildRequestBody(Boolean.FALSE, search, paramMap, messages);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    // JSON 序列化
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建chatVision请求体
   *
   * @param prompts 提词
   * @param images 图片列表
   * @return 请求体字符串
   */
  private String buildChatVisionRequestBody(List<String> prompts, final List<String> images) {
    // 使用JSON工具
    Map<String, Object> paramMap = buildRequestBody(Boolean.FALSE, prompts, images);
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建chatStream请求体
   *
   * @param messages 消息列表
   * @return 返回消息集合
   */
  private Map<String, Object> buildChatStreamRequestBody(final List<Message> messages, boolean search) {
    final Map<String, Object> paramMap = new HashMap<>();
    buildRequestBody(Boolean.TRUE, search, paramMap, messages);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());

    return paramMap;
  }

  /**
   * 构建图片生成请求体
   *
   * @param prompts 描述
   * @param images 图片
   * @return 请求体
   */
  private Map<String, Object> buildChatVisionStreamRequestBody(List<String> prompts, final List<String> images) {
    // 返回参数
    return buildRequestBody(Boolean.TRUE, prompts, images);
  }

  /**
   * 构建视频生成请求体
   *
   * @param prompts 描述
   * @param videos 图片
   * @return 请求体
   */
  private String buildChatVideoRequestBody(List<String> prompts, final List<List<String>> videos) {
    // 使用JSON工具
    Map<String, Object> paramMap = buildVideoRequestBody(Boolean.FALSE, prompts, videos);
    return JsonUtils.objectToJsonString(paramMap);
  }

  /**
   * 构建视频生成请求体
   *
   * @param prompts 描述
   * @param videos 图片
   * @return 请求体
   */
  private Map<String, Object> buildChatVideoStreamRequestBody(List<String> prompts, final List<List<String>> videos) {
    // 返回参数
    return buildVideoRequestBody(Boolean.TRUE, prompts, videos);
  }

  /**
   * 构建请求体
   *
   * @param stream 是否流式
   * @param prompts 提词
   * @param videos 图片列表
   * 
   * @return 请求体集合
   */
  private Map<String, Object> buildVideoRequestBody(boolean stream, List<String> prompts,
      final List<List<String>> videos) {
    // 定义消息结构
    final List<Message> messages = new ArrayList<>();
    final List<Object> content = getVideoObjects(prompts, videos);

    messages.add(new Message(USER, content));

    // 使用JSON工具
    final Map<String, Object> paramMap = new HashMap<>();
    if (stream) {
      paramMap.put(STREAM, Boolean.TRUE);
    }
    paramMap.put(MODEL, config.getModel());
    paramMap.put(MESSAGES, messages);
    // 合并其他参数
    paramMap.putAll(config.getAddConfigMap());
    return paramMap;
  }

  /**
   * 构建请求体
   *
   * @param stream  是否流式
   * @param prompts 提词
   * @param images  图片列表
   * 
   * @return 请求体集合
   */
  private Map<String, Object> buildRequestBody(boolean stream, List<String> prompts, final List<String> images) {
    // 定义消息结构
    final List<Message> messages = new ArrayList<>();
    final List<Object> content = getObjects(prompts, images);

    messages.add(new Message(USER, content));

    // 使用JSON工具
    final Map<String, Object> paramMap = new HashMap<>();
    if (stream) {
      paramMap.put(STREAM, Boolean.TRUE);
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
   * @param prompts 提词
   * @param videos 图片列表
   * 
   * @return 图片内容
   */
  private static List<Object> getVideoObjects(List<String> prompts, List<List<String>> videos) {
    final List<Object> content = new ArrayList<>(videos.size());
    for (List<String> video : videos) {
      int idx = videos.indexOf(video);
      String prompt = prompts.get(idx);
      // 构建视频内容
      HashMap<String, Object> urlMap = new HashMap<>(IntegerConsts.TWO);
      urlMap.put(TYPE, VIDEO);
      urlMap.put(VIDEO, video);
      content.add(urlMap);

      // 构建文本内容
      final Map<String, String> contentMap = new HashMap<>(IntegerConsts.TWO);
      contentMap.put(TYPE, TEXT);
      contentMap.put(TEXT, prompt);
      content.add(contentMap);
    }
    return content;
  }

  /**
   * 获取视频内容
   *
   * @param prompts 提词
   * @param images  图片列表
   * 
   * @return 图片内容
   */
  private static List<Object> getObjects(List<String> prompts, List<String> images) {
    final List<Object> content = new ArrayList<>(IntegerConsts.ONE);
    for (String img : images) {
      int idx = img.indexOf(img);
      String prompt = prompts.get(idx);

      // 构建图片内容
      HashMap<String, Object> imgUrlMap = new HashMap<>(IntegerConsts.ONE);
      imgUrlMap.put(TYPE, IMAGE_URL);
      HashMap<String, Object> imgMap = new HashMap<>(IntegerConsts.ONE);
      imgMap.put(URL, img);

      imgUrlMap.put(IMAGE_URL, imgMap);
      content.add(imgUrlMap);

      HashMap<String, String> urlMap = new HashMap<>(IntegerConsts.TWO);
      urlMap.put(TYPE, TEXT);
      urlMap.put(TEXT, prompt);

      content.add(urlMap);
    }

    return content;
  }

  /**
   * 构建请求体
   *
   * @param stream   是否流式
   * @param search   是否搜索
   * @param paramMap 参数集合
   * @param messages 聊天消息
   */
  private void buildRequestBody(boolean stream, boolean search, final Map<String, Object> paramMap,
      final List<Message> messages) {
    paramMap.put(MODEL, config.getModel());
    paramMap.put(MESSAGES, messages);
    if (stream) {
      paramMap.put(STREAM, Boolean.TRUE);
      paramMap.put(STREAM_OPTIONS, STREAM_OPTIONS_VALUE);
    }
    if (search) {
      paramMap.put(EXTRA_BODY, EXTRA_BODY_VALUE);
    }
  }

}
