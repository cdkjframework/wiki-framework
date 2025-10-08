package com.cdkjframework.util.network.https;

import com.alibaba.fastjson.JSON;
import com.cdkjframework.constant.HttpHeaderConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.http.HttpRequestEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.GzipUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.AbstractHttpClientResponseHandler;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.*;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkjframework.core.util.https
 * @ClassName: HttpsClientUtil
 * @Description: https 请求
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class HttpsClientUtils {

  /**
   * 日志
   */
  private static LogUtils logUtil = LogUtils.getLogger(HttpsClientUtils.class);

  /**
   * HTTPS post 请求
   *
   * @param requestEntity 请求实体
   * @return 返回结果
   */
  @SuppressWarnings("resource")
  public static String doPost(HttpRequestEntity requestEntity) {
    HttpClient httpClient = null;
    HttpPost httpPost = null;
    final StringBuffer result = new StringBuffer();
    try {
      httpClient = TlsPool.createSslContext();
      httpPost = new HttpPost(requestEntity.getRequestAddress());
      //设置 http 请头
      Header[] headers = setHeader(requestEntity);
      httpPost.setHeaders(headers);

      String basicHeader = requestEntity.getHeaderMap().get(HttpHeaderConsts.CONTENT_TYPE);
      final String contentType = MediaType.APPLICATION_JSON_VALUE;
      if (StringUtils.isNullAndSpaceOrEmpty(basicHeader)) {
        basicHeader = contentType;
      }

      //将参数转换为 json 对象
      String param = StringUtils.Empty;
      if (requestEntity.getObjectList().size() > 0) {
        param = JSON.toJSONString(requestEntity.getObjectList());
      } else {
        Map<String, Object> paramsMap = requestEntity.getParamsMap();
        if (paramsMap == null) {
          paramsMap = new HashMap<>(IntegerConsts.ONE);
        }
        if (contentType.equals(basicHeader)) {
          param = JSON.toJSONString(requestEntity.getParamsMap());
        } else {
          Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
          for (Map.Entry entry :
              entrySet) {
            if (StringUtils.isNotNullAndEmpty(param)) {
              param += StringUtils.CONNECTOR;
            }
            param += String.format("%s=%s", entry.getKey(), entry.getValue());
          }
        }
      }
      StringEntity stringEntity;
      ContentType content;
      //是否启用 gzip 加密
      if (requestEntity.isCompress()) {
        basicHeader = "gzip";
        content = ContentType.create(HttpHeaderConsts.CONTENT_TYPE, basicHeader);
        String gzipParams = GzipUtils.gZip(param, requestEntity.getCharset());
        stringEntity = new StringEntity(gzipParams, content);
      } else {
        content = ContentType.create(requestEntity.getContentType());
        stringEntity = new StringEntity(param, content);
      }

      httpPost.setEntity(stringEntity);
      //请求并获取结果
      var handler = new AbstractHttpClientResponseHandler() {
        @Override
        public Object handleEntity(HttpEntity entity) throws IOException {
          //读取返回结果
          if (entity != null) {
            //读取返回内容
            try {
              result.append(EntityUtils.toString(entity, requestEntity.getCharset()));
            } catch (ParseException e) {
              logUtil.error(e);
            }
          }
          return result.toString();
        }
      };
      httpClient.execute(httpPost, handler);
    } catch (Exception ex) {
      logUtil.error(ex.getMessage());
    }

    //返回结果
    return result.toString();
  }

  /**
   * request https 请求
   *
   * @param requestEntity 请求实例
   * @return 返回结果
   */
  @SuppressWarnings("resource")
  public static StringBuilder doGet(HttpRequestEntity requestEntity) {
    HttpClient httpClient = null;
    HttpGet httpGet = null;
    StringBuilder result = null;
    try {
      httpClient = TlsPool.createSslContext();
      // 设置 http 请头
      Header[] headers = setHeader(requestEntity);
      httpGet.setHeaders(headers);

      // 设置请求的配置
      final int seconds = 5000;
      Timeout timeout = Timeout.of(Duration.ofSeconds(seconds));
      RequestConfig requestConfig = RequestConfig.custom()
          .setResponseTimeout(timeout)
          .setConnectionRequestTimeout(timeout).build();
      httpGet.setConfig(requestConfig);

      //将参数转换为
      URIBuilder uriBuilder = new URIBuilder(requestEntity.getRequestAddress());

      // 添加请求参数
      Map<String, Object> paramMap = requestEntity.getParamsMap();
      if (paramMap != null) {
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
          uriBuilder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }
      }
      httpGet = new HttpGet(uriBuilder.build());

      //请求并获取结果
      var handler = new AbstractHttpClientResponseHandler() {
        @Override
        public Object handleEntity(HttpEntity entity) throws IOException {
          //读取返回结果
          if (entity != null) {
            //读取返回内容
            try {
              result.append(EntityUtils.toString(entity, requestEntity.getCharset()));
            } catch (ParseException e) {
              logUtil.error(e);
            }
          }
          return StringUtils.Empty;
        }
      };
      //请求并获取结果
      httpClient.execute(httpGet, handler);
    } catch (Exception ex) {
      logUtil.error(ex.getMessage());
    }

    //返回结果
    return null;
  }

  /**
   * 设置 http 请求头
   *
   * @param requestEntity 请求头参数
   */
  private static Header[] setHeader(HttpRequestEntity requestEntity) {
    Map<String, String> mapHeader = requestEntity.getHeaderMap();

    List<Header> headerList = new ArrayList<>();
    //验证是否开启数据压缩
    if (requestEntity.isCompress()) {
      headerList.add(new BasicHeader(HttpHeaderConsts.CONTENT_ENCODING, "gzip"));
    }

    Set<Map.Entry<String, String>> entrySet = mapHeader.entrySet();
    // 设置通用的请求属性
    for (Map.Entry<String, String> entry :
        entrySet) {
      headerList.add(new BasicHeader(entry.getKey(), entry.getValue()));
    }

    //返回 header
    return headerList.toArray(new Header[headerList.size()]);
  }
}
