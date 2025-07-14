package com.cdkjframework.ai.model.openai;

import com.cdkjframework.ai.constant.AiCommon;
import com.cdkjframework.ai.core.Message;
import com.cdkjframework.builder.ResponseBuilder;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.cdkjframework.ai.constant.AiConstant.SYSTEM;
import static com.cdkjframework.ai.constant.AiConstant.USER;

/**
 * Openai服务接口
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.openai
 * @ClassName: OpenaiService
 * @Description: Openai服务接口
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface OpenaiService {

  /**
   * 理解
   */
  String REASONING_EFFORT = "reasoning_effort";

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
    return chatVision(prompt, images, AiCommon.OpenaiVision.AUTO.getValue());
  }

  /**
   * 图像理解-SSE流式输出
   *
   * @param prompt   题词
   * @param images   传入的图片列表地址/或者图片Base64编码图片列表(URI形式)
   * @param callback 流式数据回调函数
   */
  default void chatVision(String prompt, final List<String> images, final Consumer<ResponseBuilder> callback) {
    chatVision(prompt, images, AiCommon.OpenaiVision.AUTO.getValue(), callback);
  }

  /**
   * 文生图 请设置config中model为支持图片功能的模型 DALL·E系列
   *
   * @param prompt 题词
   * @return 包含生成图片的url
   */
  String imagesGenerations(String prompt);

  /**
   * 图片编辑 该方法仅支持 DALL·E 2 model
   *
   * @param prompt 题词
   * @param image  需要编辑的图像必须是 PNG 格式
   * @param mask   如果提供，则是一个与编辑图像大小相同的遮罩图像应该是灰度图，白色表示需要编辑的区域，黑色表示不需要编辑的区域。
   * @return 包含生成图片的url
   */
  String imagesEdits(String prompt, final File image, final File mask);

  /**
   * 图片编辑 该方法仅支持 DALL·E 2 model
   *
   * @param prompt 题词
   * @param image  需要编辑的图像必须是 PNG 格式
   * @return 包含生成图片的url
   */
  default String imagesEdits(String prompt, final File image) {
    return imagesEdits(prompt, image, null);
  }

  /**
   * 图片变形 该方法仅支持 DALL·E 2 model
   *
   * @param image 需要变形的图像必须是 PNG 格式
   * @return 包含生成图片的url
   */
  String imagesVariations(final File image);

  /**
   * TTS文本转语音 请设置config中model为支持TTS功能的模型 TTS系列
   *
   * @param input 需要转成语音的文本
   * @param voice AI的音色
   * @return 返回的音频mp3文件流
   */
  InputStream textToSpeech(String input, final AiCommon.OpenaiSpeech voice);

  /**
   * TTS文本转语音 请设置config中model为支持TTS功能的模型 TTS系列
   *
   * @param input 需要转成语音的文本
   * @return 返回的音频mp3文件流
   */
  default InputStream textToSpeech(String input) {
    return textToSpeech(input, AiCommon.OpenaiSpeech.ALLOY);
  }

  /**
   * STT音频转文本 请设置config中model为支持STT功能的模型 whisper
   *
   * @param file 需要转成文本的音频文件
   * @return 返回的文本内容
   */
  String speechToText(final File file);

  /**
   * 文本向量化 请设置config中model为支持文本向量化功能的模型 text-embedding系列
   *
   * @param input 需要向量化的内容
   * @return 处理后的向量信息
   */
  String embeddingText(String input);

  /**
   * 检查文本或图像是否具有潜在的危害性
   * 仅支持omni-moderation-latest和text-moderation-latest模型
   *
   * @param text   需要检查的文本
   * @param imgUrl 需要检查的图片地址
   * @return AI回复消息
   */
  String moderations(String text, String imgUrl);

  /**
   * 检查文本是否具有潜在的危害性
   * 仅支持omni-moderation-latest和text-moderation-latest模型
   *
   * @param text 需要检查的文本
   * @return AI回复消息
   */
  default String moderations(String text) {
    return moderations(text, null);
  }

  /**
   * 推理chat
   * 支持o3-mini和o1
   *
   * @param prompt          对话题词
   * @param reasoningEffort 推理程度
   * @return AI回复消息
   */
  default String chatReasoning(String prompt, String reasoningEffort) {
    final List<Message> messages = new ArrayList<>();
    messages.add(new Message("system", "You are a helpful assistant"));
    messages.add(new Message("user", prompt));
    return chatReasoning(messages, reasoningEffort);
  }

  /**
   * 推理chat-SSE流式输出
   * 支持o3-mini和o1
   *
   * @param prompt          对话题词
   * @param reasoningEffort 推理程度
   * @param callback        流式数据回调函数
   */
  default void chatReasoning(String prompt, String reasoningEffort, final Consumer<ResponseBuilder> callback) {
    final List<Message> messages = new ArrayList<>();
    messages.add(new Message(SYSTEM, "You are a helpful assistant"));
    messages.add(new Message(USER, prompt));
    chatReasoning(messages, reasoningEffort, callback);
  }

  /**
   * 推理chat
   * 支持o3-mini和o1
   *
   * @param prompt 对话题词
   * @return AI回复消息
   */
  default String chatReasoning(String prompt) {
    return chatReasoning(prompt, AiCommon.OpenaiReasoning.MEDIUM.getEffort());
  }

  /**
   * 推理chat-SSE流式输出
   * 支持o3-mini和o1
   *
   * @param prompt   对话题词
   * @param callback 流式数据回调函数
   */
  default void chatReasoning(String prompt, final Consumer<ResponseBuilder> callback) {
    chatReasoning(prompt, AiCommon.OpenaiReasoning.MEDIUM.getEffort(), callback);
  }

  /**
   * 推理chat
   * 支持o3-mini和o1
   *
   * @param messages        消息列表
   * @param reasoningEffort 推理程度
   * @return AI回复消息
   */
  String chatReasoning(final List<Message> messages, String reasoningEffort);

  /**
   * 推理chat-SSE流式输出
   * 支持o3-mini和o1
   *
   * @param messages        消息列表
   * @param reasoningEffort 推理程度
   * @param callback        流式数据回调函数
   */
  void chatReasoning(final List<Message> messages, String reasoningEffort, final Consumer<ResponseBuilder> callback);

  /**
   * 推理chat
   * 支持o3-mini和o1
   *
   * @param messages 消息列表
   * @return AI回复消息
   */
  default String chatReasoning(final List<Message> messages) {
    return chatReasoning(messages, AiCommon.OpenaiReasoning.MEDIUM.getEffort());
  }

  /**
   * 推理chat-SSE流式输出
   * 支持o3-mini和o1
   *
   * @param messages 消息列表
   * @param callback 流式数据回调函数
   */
  default void chatReasoning(final List<Message> messages, final Consumer<ResponseBuilder> callback) {
    chatReasoning(messages, AiCommon.OpenaiReasoning.MEDIUM.getEffort(), callback);
  }
}
