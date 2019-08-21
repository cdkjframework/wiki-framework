package com.cdkjframework.util.network.https;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.consts.HttpHeaderConstant;
import com.cdkjframework.entity.http.HttpRequestEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.http.HttpRequestUtils;
import com.cdkjframework.util.tool.GzipUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
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
            httpClient = new TlsPool();
            httpPost = new HttpPost(requestEntity.getRequestAddress());
            //设置 http 请头
            setHeader(httpPost, requestEntity);
            //将参数转换为 json 对象
            String param;
            if (requestEntity.getObjectList().size() > 0) {
                param = JSONArray.toJSONString(requestEntity.getObjectList());
            } else {
                param = JSONObject.toJSONString(requestEntity.getParamsMap());
            }

            String basicHeader = requestEntity.getHeaderMap().get(HttpHeaderConstant.contentType);
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

            stringEntity.setContentEncoding(new BasicHeader(HttpHeaderConstant.contentType, basicHeader));
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
    public static StringBuilder httpsRequest(HttpRequestEntity requestEntity) {
        PrintWriter printWriter = null;
        HttpClient httpClient = null;
        BufferedReader bufferedReader = null;
        StringBuilder result = new StringBuilder();
        try {
            httpClient = TlsPool.createSSLContext();
            URL realUrl = new URL(requestEntity.getRequestAddress());
            // 打开和URL之间的连接
            HttpsURLConnection connection = (HttpsURLConnection) realUrl.openConnection();

            httpClient.execute(connection,)

            // 设置通用的请求属性
            //设置 http 请求头
            HttpRequestUtils.setHeader(connection, requestEntity);
            connection.setRequestMethod(requestEntity.getMethod().getValue());
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            printWriter = new PrintWriter(connection.getOutputStream());

            //将参数转换为 json 对象
            String param;
            if (requestEntity.getObjectList().size() > 0) {
                param = JSONArray.toJSONString(requestEntity.getObjectList());
            } else {
                param = JSONObject.toJSONString(requestEntity.getParamsMap());
            }

            //是否 gzip 加密
            if (requestEntity.isCompress()) {
                String gzipParams = GzipUtils.gZip(param, requestEntity.getCharset());
                printWriter.write(gzipParams);
            } else {
                // 发送请求参数
                printWriter.print(param);
            }
            // flush输出流的缓冲
            printWriter.flush();
            // 定义BufferedReader输入流来读取URL的响应
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            logUtil.error(e.getMessage());
        } finally {
            //使用finally块来关闭输出流、输入流
            try {
                if (printWriter != null) {
                    printWriter.close();
                    bufferedReader.close();
                }
            } catch (IOException e) {
                logUtil.error(e.getMessage());
            }
        }
        return result;
    }

    /**
     * 设置 http 请求头
     *
     * @param httpPost      URL连接
     * @param requestEntity 请求头参数
     */
    private static void setHeader(HttpPost httpPost, HttpRequestEntity requestEntity) {
        Map<String, String> mapHeader = requestEntity.getHeaderMap();
        Set<Map.Entry<String, String>> entrySet = mapHeader.entrySet();
        // 设置通用的请求属性
        for (Map.Entry<String, String> entry :
                entrySet) {
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }

        //验证是否开启数据压缩
        if (requestEntity.isCompress()) {
            httpPost.setHeader(HttpHeaderConstant.contentEncoding, "gzip");
        }
    }
}
