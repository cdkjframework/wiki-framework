package com.cdkjframework.entity.log.feign;

import lombok.Data;

import java.util.Map;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.entity.log.feign
 * @ClassName: FeignLogEntity
 * @Description: Feign日志
 * @Author: xiaLin
 * @Date: 2024/5/16 10:27
 * @Version: 1.0
 */
@Data
public class FeignLogEntity {

  /**
   * 日志ID
   */
  private String id;

  /**
   * 请求地址
   */
  private String requestUrl;
  /**
   * 请求发送方式：GET/POST/DELETE/PUT/HEADER/...
   */
  private String requestMethod;
  /**
   * 请求header信息
   */
  private Map<String, String> requestHeader;
  /**
   * 请求body
   */
  private String requestBody;
  /**
   * 请求返回状态
   */
  private Integer status;
  /**
   * 请求返回异常信息
   */
  private String exception;
  /**
   * 响应header信息
   */
  private Map<String, String> responseHeader;
  /**
   * 响应body
   */
  private String responseBody;
  /**
   * 请求到接收到响应数据耗费时长单位ms
   */
  private long responseTime;
}
