package com.cdkjframework.cloud.client;

import com.cdkjframework.cloud.service.FeignService;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.log.feign.FeignLogDto;
import feign.Client;
import feign.Request;
import feign.Response;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.cloud.client
 * @ClassName: FeignClient
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2024/5/16 10:53
 * @Version: 1.0
 */
public class FeignClient extends Client.Default {

  /**
   * Feign服务接口
   */
  private final FeignService feignServiceImpl;

  /**
   * 创建一个新的客户端，默认情况下会禁用请求缓冲。
   *
   * @param sslContextFactory SSLSocketFactory用于安全的https URL连接。
   * @param hostnameVerifier  主机名验证器。
   * @param feignService      Feign服务接口
   */
  public FeignClient(SSLSocketFactory sslContextFactory, HostnameVerifier hostnameVerifier, FeignService feignService) {
    super(sslContextFactory, hostnameVerifier);
    feignServiceImpl = feignService;
  }

  /**
   * 执行给定的请求。
   *
   * @param request 请求
   * @param options 应用于此请求的选项。
   * @return 返回响应
   * @throws IOException IO异常
   */
  @Override
  public Response execute(Request request, Request.Options options) throws IOException {
    FeignLogDto feignLog = new FeignLogDto();
    feignLog.setRequestUrl(request.url());
    feignLog.setRequestMethod(request.httpMethod().name());
    // 请求头
    if (request.headers() != null && !request.headers().isEmpty()) {
      feignLog.setRequestHeader(new HashMap<>(request.headers().size()));
      for (Map.Entry<String, Collection<String>> ob : request.headers().entrySet()) {
        for (String val : ob.getValue()) {
          feignLog.getRequestHeader().put(ob.getKey(), val);
        }
      }
    }
    // 请求体
    if (request.body() != null && request.body().length > IntegerConsts.ZERO) {
      feignLog.setRequestBody(new String(request.body()));
    }
    long responseTime = IntegerConsts.MINUS_ONE;
    Exception exception = null;
    BufferingFeignClientResponse response = null;
    long begin = System.currentTimeMillis();
    try {
      response = new BufferingFeignClientResponse(super.execute(request, options));
      responseTime = (System.currentTimeMillis() - begin);
    } catch (Exception exp) {
      responseTime = (System.currentTimeMillis() - begin);
      exception = exp;
      throw exp;
    } finally {
      feignLog.setResponseTime(responseTime);
      if (response != null) {
        feignLog.setStatus(response.status());
      }
      if (response != null) {
        if (!CollectionUtils.isEmpty(response.headers())) {
          feignLog.setResponseHeader(new HashMap<>(response.headers().size()));
          for (Map.Entry<String, Collection<String>> ob : response.headers().entrySet()) {
            for (String val : ob.getValue()) {
              feignLog.getResponseHeader().put(ob.getKey(), val);
            }
          }
        }
        if (request.body() != null && request.body().length > IntegerConsts.ZERO) {
          feignLog.setResponseBody(new String(response.body()));
        }
      }
      if (exception != null) {
        feignLog.setException(exception.getMessage());
      }
    }

    // 构建结果
    Response build = response.getResponse()
            .toBuilder()
            .body(response.getBody(), response.getResponse().body().length())
            .build();

    // 写出内容
    feignServiceImpl.feign(feignLog);
    // 关闭流
    response.close();

    // 返回结果
    return build;
  }
}
