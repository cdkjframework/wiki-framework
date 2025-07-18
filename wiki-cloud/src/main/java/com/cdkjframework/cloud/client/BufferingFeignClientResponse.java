package com.cdkjframework.cloud.client;

import com.cdkjframework.constant.IntegerConsts;
import feign.Response;
import lombok.Getter;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.util.Collection;
import java.util.Map;

/**
 * 缓存FeignClient响应
 *
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.cloud.client
 * @ClassName: BufferingFeignClientResponse
 * @Description: 缓存FeignClient响应
 * @Author: xiaLin
 * @Date: 2024/5/16 10:55
 * @Version: 1.0
 */
public class BufferingFeignClientResponse implements Closeable {

  /**
   * 响应
   */
  @Getter
  private final Response response;

  /**
   * 内容
   */
  private byte[] body;

  /**
   * 构造方法
   *
   * @param response 响应
   */
  public BufferingFeignClientResponse(Response response) {
    this.response = response;
  }

  /**
   * 状态码
   *
   * @return 返回状态码
   */
  public int status() {
    return this.response.status();
  }

  /**
   * 响应头
   *
   * @return 响应头
   */
  public Map<String, Collection<String>> headers() {
    return this.response.headers();
  }

  /**
   * 响应体
   *
   * @return 响应体
   * @throws IOException IO异常
   */
  public String body() throws IOException {
    StringBuilder buffer = new StringBuilder();
    try (InputStreamReader reader = new InputStreamReader(getBody())) {
      char[] tmp = new char[IntegerConsts.BYTE_LENGTH];
      int len;
      while ((len = reader.read(tmp, IntegerConsts.ZERO, tmp.length)) != IntegerConsts.MINUS_ONE) {
        buffer.append(new String(tmp, IntegerConsts.ZERO, len));
      }
    }
    return buffer.toString();
  }

  /**
   * 获取响应体
   *
   * @return 响应体流
   * @throws IOException IO异常
   */
  public InputStream getBody() throws IOException {
    if (this.body == null) {
      this.body = StreamUtils.copyToByteArray(this.response.body().asInputStream());
    }
    return new ByteArrayInputStream(this.body);
  }

  /**
   * 关闭
   */
  @Override
  public void close() {
    this.response.close();
  }
}
