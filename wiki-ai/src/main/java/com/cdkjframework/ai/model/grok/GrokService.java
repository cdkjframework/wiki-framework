package com.cdkjframework.ai.model.grok;

import com.cdkjframework.ai.constant.AiCommon;
import com.cdkjframework.ai.core.AiService;
import com.cdkjframework.ai.core.Message;
import com.cdkjframework.builder.ResponseBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.cdkjframework.ai.constant.AiConstant.SYSTEM;
import static com.cdkjframework.ai.constant.AiConstant.USER;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.grok
 * @ClassName: GrokService
 * @Description: Grok服务接口
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface GrokService extends AiService {


  /**
   * 创建消息回复
   *
   * @param prompt   题词
   * @param maxToken 最大token
   * @return AI回复消息
   */
  default String message(String prompt, int maxToken) {
    // 定义消息结构
    final List<Message> messages = new ArrayList<>();
    messages.add(new Message(SYSTEM, "You are a helpful assistant"));
    messages.add(new Message(USER, prompt));
    return message(messages, maxToken);
  }

  /**
   * 创建消息回复-SSE流式输出
   *
   * @param prompt   题词
   * @param maxToken 最大token
   * @param callback 流式数据回调函数
   */
  default void message(String prompt, int maxToken, final Consumer<ResponseBuilder> callback) {
    final List<Message> messages = new ArrayList<>();
    messages.add(new Message(SYSTEM, "You are a helpful assistant"));
    messages.add(new Message(USER, prompt));
    message(messages, maxToken, callback);
  }

  /**
   * 创建消息回复
   *
   * @param messages messages 由对话组成的消息列表。如系统人设，背景信息等，用户自定义的信息
   * @param maxToken 最大token
   * @return AI回复消息
   */
  String message(List<Message> messages, int maxToken);

  /**
   * 创建消息回复-SSE流式输出
   *
   * @param messages messages 由对话组成的消息列表。如系统人设，背景信息等，用户自定义的信息
   * @param maxToken 最大token
   * @param callback 流式数据回调函数
   */
  void message(List<Message> messages, int maxToken, final Consumer<ResponseBuilder> callback);

  /**
   * 图像理解：模型会依据传入的图片信息以及问题，给出回复。
   *
   * @param prompt 题词
   * @param images 图片列表/或者图片Base64编码图片列表(URI形式)
   * @param detail 手动设置图片的质量，取值范围high、low、auto,默认为auto
   * @return AI回复消息
   */
  String chatVision(String prompt, final List<String> images, String detail);

  /**
   * 图像理解-SSE流式输出
   *
   * @param prompt   题词
   * @param images   图片列表/或者图片Base64编码图片列表(URI形式)
   * @param detail   手动设置图片的质量，取值范围high、low、auto,默认为auto
   * @param callback 流式数据回调函数
   */
  void chatVision(String prompt, final List<String> images, String detail, final Consumer<ResponseBuilder> callback);

  /**
   * 图像理解：模型会依据传入的图片信息以及问题，给出回复。
   *
   * @param prompt 题词
   * @param images 传入的图片列表地址/或者图片Base64编码图片列表(URI形式)
   * @return AI回复消息
   */
  default String chatVision(String prompt, final List<String> images) {
    return chatVision(prompt, images, AiCommon.GrokVision.AUTO.getValue());
  }

  /**
   * 图像理解：模型会依据传入的图片信息以及问题，给出回复。
   *
   * @param prompt   题词
   * @param images   传入｜的图片列表地址/或者图片Base64编码图片列表(URI形式)
   * @param callback 流式数据回调函数
   */
  default void chatVision(String prompt, final List<String> images, final Consumer<ResponseBuilder> callback) {
    chatVision(prompt, images, AiCommon.GrokVision.AUTO.getValue(), callback);
  }

  /**
   * 列出所有model列表
   *
   * @return model列表
   */
  String models();

  /**
   * 获取模型信息
   *
   * @param modelId model ID
   * @return model信息
   */
  String findModel(String modelId);

  /**
   * 列出所有语言model
   *
   * @return languageModel列表
   */
  String languageModels();

  /**
   * 获取语言模型信息
   *
   * @param modelId model ID
   * @return model信息
   */
  String findLanguageModel(String modelId);

  /**
   * 分词：可以将文本转换为模型可理解的 token 信息
   *
   * @param text 需要分词的内容
   * @return 分词结果
   */
  String tokenizeText(String text);

  /**
   * 从延迟对话中获取结果
   *
   * @param requestId 延迟对话中的延迟请求ID
   * @return AI回复消息
   */
  String deferredCompletion(String requestId);

  /**
   * 文生图
   * 请设置config中model为支持图片功能的模型，目前支持GROK_2_IMAGE
   *
   * @param prompt 题词
   * @return 包含生成图片的url
   */
  String imagesGenerations(String prompt);
}
