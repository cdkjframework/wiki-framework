package com.cdkjframework.cloud.service;

import com.cdkjframework.entity.log.feign.FeignLogDto;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.cloud.service
 * @ClassName: FeignService
 * @Description: Feign服务接口
 * @Author: xiaLin
 * @Date: 2024/5/16 11:07
 * @Version: 1.0
 */
public interface FeignService {

  /**
   * 日志回调
   *
   * @param logDto 日志信息
   */
  void feign(FeignLogDto logDto);
}
