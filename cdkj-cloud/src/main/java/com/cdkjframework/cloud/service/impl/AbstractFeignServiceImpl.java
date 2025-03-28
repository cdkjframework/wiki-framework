package com.cdkjframework.cloud.service.impl;

import com.cdkjframework.cloud.service.FeignService;
import com.cdkjframework.entity.log.feign.FeignLogDto;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.cloud.service.impl
 * @ClassName: AbstractFeignServiceImpl
 * @Description: Feign服务抽象接口
 * @Author: xiaLin
 * @Date: 2024/5/16 11:09
 * @Version: 1.0
 */
@Component
public abstract class AbstractFeignServiceImpl implements FeignService {

  /**
   * 日志
   */
  private LogUtils logUtils = LogUtils.getLogger(AbstractFeignServiceImpl.class);

  /**
   * 日志回调
   *
   * @param logDto 日志信息
   */
  @Override
  public void feign(FeignLogDto logDto) {
    logUtils.info(JsonUtils.objectToJsonString(logDto));
  }
}
