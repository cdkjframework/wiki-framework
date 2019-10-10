package com.cdkjframework.util.network.https;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.entity.http.HttpRequestEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.GzipUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        String result = null;
        try {
            httpClient = TlsPool.createSslContext();
            httpPost = new HttpPost(requestEntity.getRequestAddress());
            //设置 http 请头
            Header[] headers = setHeader(requestEntity);
            httpPost.setHeaders(headers);
            //将参数转换为 json 对象
            String param;
            if (requestEntity.getObjectList().size() > 0) {
                param = JSONArray.toJSONString(requestEntity.getObjectList());
            } else {
                param = JSONObject.toJSONString(requestEntity.getParamsMap());
            }

            String basicHeader = requestEntity.getHeaderMap().get(com.cdkjframework.consts.HttpHeaderConsts.contentType);
            if (StringUtils.isNullAndSpaceOrEmpty(basicHeader)) {
                basicHeader = "application/json";
            }
            StringEntity stringEntity;
            //是否启用 gzip 加密
            if (requestEntity.isCompress()) {
                basicHeader = "gzip";
                String gzipParams = GzipUtils.gZip(param, requestEntity.getCharset());
                stringEntity = new StringEntity(gzipParams);
            } else {
                stringEntity = new StringEntity(param);
            }

            //设置请求类型
            stringEntity.setContentType(requestEntity.getContentType());

            stringEntity.setContentEncoding(new BasicHeader(com.cdkjframework.consts.HttpHeaderConsts.contentType, basicHeader));
            httpPost.setEntity(stringEntity);
            //请求并获取结果
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                //读取返回结果
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //读取返回内容
                    result = EntityUtils.toString(resEntity, requestEntity.getCharset());
                }
            }
        } catch (Exception ex) {
            logUtil.error(ex.getMessage());
        }

        //返回结果
        return result;
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
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(5000).setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000).build();
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
            HttpResponse response = httpClient.execute(httpGet);
            if (response != null) {
                //读取返回结果
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //读取返回内容
                    result.append(EntityUtils.toString(resEntity, requestEntity.getCharset()));
                }
            }
        } catch (Exception ex) {
            logUtil.error(ex.getMessage());
        }

        //返回结果
        return result;
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
            headerList.add(new BasicHeader(com.cdkjframework.consts.HttpHeaderConsts.contentEncoding, "gzip"));
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
