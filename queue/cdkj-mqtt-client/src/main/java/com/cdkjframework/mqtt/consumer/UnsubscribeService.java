package com.cdkjframework.mqtt.consumer;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.message.queue.eclipse.client
 * @ClassName: UnsubscribeService
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/11/2 15:17
 * @Version: 1.0
 */
public interface UnsubscribeService {

  /**
   * 取消订阅主题列表
   *
   * @return 返回主题列表
   */
  List<String> unsubscribeTopics();
}
