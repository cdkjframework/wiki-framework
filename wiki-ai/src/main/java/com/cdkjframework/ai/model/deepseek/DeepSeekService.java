package com.cdkjframework.ai.model.deepseek;

import com.cdkjframework.builder.ResponseBuilder;

import java.util.function.Consumer;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.model.deepseek
 * @ClassName: DeepSeekService
 * @Description: DeepSeek接口服务
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface DeepSeekService {

  /**
   * 模型beta功能
   *
   * @param prompt 题词
   * @return AI回复消息
   */
  String beta(String prompt);

  /**
   * 模型beta功能-SSE流式输出
   *
   * @param prompt   题词
   * @param callback 流式数据回调函数
   */
  void beta(String prompt, final Consumer<ResponseBuilder> callback);

  /**
   * 列出所有模型列表
   *
   * @return model列表
   */
  String models();

  /**
   * 查询余额
   *
   * @return 余额
   */
  String balance();
}
