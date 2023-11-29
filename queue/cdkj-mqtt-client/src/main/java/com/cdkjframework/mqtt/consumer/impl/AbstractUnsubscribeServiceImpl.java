package com.cdkjframework.mqtt.consumer.impl;

import com.cdkjframework.mqtt.consumer.UnsubscribeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.message.queue.eclipse.client.impl
 * @ClassName: AbstractUnsubscribeServiceImpl
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/11/2 15:18
 * @Version: 1.0
 */
@Service
public abstract class AbstractUnsubscribeServiceImpl implements UnsubscribeService {

  /**
   * 取消订阅主题列表
   *
   * @return 返回主题列表
   */
  @Override
  public List<String> unsubscribeTopics() {
    return new ArrayList<>();
  }
}
