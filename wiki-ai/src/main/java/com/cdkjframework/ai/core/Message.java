package com.cdkjframework.ai.core;

import com.cdkjframework.ai.constant.AiConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息类
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.core
 * @ClassName: Message
 * @Description: 消息类
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class Message {

  /**
   * 角色 注意：如果设置系统消息，必须放在消息列表的第一位
   */
  private String role;
  /**
   * 内容
   */
  private Object content;

  /**
   * 构建消息
   *
   * @param role    角色
   * @param content 内容
   */
  public Message(String role, Object content) {
    this.role = role;
    this.content = content;
  }

  /**
   * 构建消息
   *
   * @param role    角色
   * @param content 内容
   * @return 消息
   */
  public static Message build(String role, String content) {
    return new Message(role, content);
  }

  /**
   * 构建消息列表
   *
   * @return 默认消息列表
   */
  public static List<Message> build() {
    List<Message> messages = new ArrayList<>();
    messages.add(build(AiConstant.SYSTEM, AiConstant.ASSISTANT_CONTENT));
    // 添加系统消息
    return messages;
  }
}
