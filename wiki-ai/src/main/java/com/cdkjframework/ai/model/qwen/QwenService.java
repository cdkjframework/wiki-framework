package com.cdkjframework.ai.model.qwen;

import java.util.List;
import java.util.function.Consumer;

import com.cdkjframework.ai.constant.AiCommon;
import com.cdkjframework.ai.core.AiService;
import com.cdkjframework.ai.core.Message;
import com.cdkjframework.builder.ResponseBuilder;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.qwen
 * @ClassName: QwenService
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/8/3 9:08
 * @Version: 1.0
 */
public interface QwenService extends AiService {

  /**
   * 搜索
   */
  String EXTRA_BODY = "extra_body";

  /**
   * 搜索值
   */
  String EXTRA_BODY_VALUE = "{\"enable_search\": True\"}";

  /**
   * 流式参数
   */
  String STREAM_OPTIONS = "stream_options";

  /**
   * 流式参数值
   */
  String STREAM_OPTIONS_VALUE = "{\"include_usage\": True}";

  /**
   * 对话
   * messages 可以由当前对话组成的消息列表，可以设置role，content。详细参考官方文档
   *
   * @param messages 消息列表
   * @return 返回AI回复的消息
   */
  String chatSearch(final List<Message> messages);

  /**
   * 对话-SSE 流式输出
   * messages 可以由当前对话组成的消息列表，可以设置role，content。详细参考官方文档
   *
   * @param messages 消息列表
   * @param callback 流式数据回调函数
   */
  void chatSearch(final List<Message> messages, final Consumer<ResponseBuilder> callback);

  /**
   * 图像理解：模型会依据传入的图片信息以及问题，给出回复。
   *
   * @param prompts 提问
   * @param images 图片列表/或者图片Base64编码图片列表(URI形式)
   * @return AI回答内容
   */
  String chatVision(List<String> prompts, final List<String> images);

  /**
   * 图像理解-SSE流式输出
   *
   * @param prompts   提问
   * @param images   传入的图片列表地址/或者图片Base64编码图片列表(URI形式)
   * @param callback 流式数据回调函数
   */
  void chatVision(List<String> prompts, final List<String> images, final Consumer<ResponseBuilder> callback);

  /**
   * 图像理解：模型会依据传入的图片信息以及问题，给出回复。
   *
   * @param prompts 提问
   * @param videos 视频列表(URI形式)
   * @return AI回答内容
   */
  String chatVideoVision(List<String> prompts, final List<List<String>> videos);

  /**
   * 图像理解-SSE流式输出
   *
   * @param prompts   提问
   * @param videos   视频列表(URI形式)
   * @param callback 流式数据回调函数
   */
  void chatVideoVision(List<String> prompts, final List<List<String>> videos, final Consumer<ResponseBuilder> callback);
}
