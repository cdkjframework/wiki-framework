package com.cdkjframework.ai.core;

import com.cdkjframework.ai.constant.AiConstant;
import com.cdkjframework.builder.ResponseBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * AI 服务接口
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.core
 * @ClassName: AiService
 * @Description: AI 服务接口
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface AiService {

  /**
   * 对话
   *
   * @param prompt user题词
   * @return 返回AI回复的消息
   */
  default String chat(String prompt) {
    final List<Message> messages = Message.build();
    messages.add(Message.build(AiConstant.USER, prompt));
    return chat(messages);
  }

  /**
   * 对话-SSE流式输出
   *
   * @param prompt   user 题词
   * @param callback 流式数据回调函数
   */
  default void chat(String prompt, final Consumer<ResponseBuilder> callback) {
    final List<Message> messages = Message.build();
    messages.add(new Message(AiConstant.USER, prompt));
    chat(messages, callback);
  }

  /**
   * 对话
   * messages 可以由当前对话组成的消息列表，可以设置role，content。详细参考官方文档
   *
   * @param messages 消息列表
   * @return 返回AI回复的消息
   */
  String chat(final List<Message> messages);

  /**
   * 对话-SSE 流式输出
   * messages 可以由当前对话组成的消息列表，可以设置role，content。详细参考官方文档
   *
   * @param messages 消息列表
   * @param callback 流式数据回调函数
   */
  void chat(final List<Message> messages, final Consumer<ResponseBuilder> callback);
}
