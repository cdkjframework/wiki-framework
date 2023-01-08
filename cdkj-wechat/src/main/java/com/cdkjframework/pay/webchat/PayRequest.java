package com.cdkjframework.pay.webchat;

import com.cdkjframework.constant.EncodingConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.files.XmlUtils;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay.qrcode.webchat
 * @ClassName: PayRequest
 * @Description: 支付请求
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class PayRequest {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(PayRequest.class);

    /**
     * 编码
     */
    private final String CHARSET_NAME = "UTF-8";

    /**
     * 请求数据
     *
     * @param t              数据实体
     * @param requestAddress 请求地址
     * @param <T>            实体对象
     * @return 返回结果
     */
    public <T> String request(T t, String requestAddress) {
        StringBuilder builder = new StringBuilder();
        try {
            //将实体转换为 XML 文件 WebChatQueryEntity
            String xml = XmlUtils.beanToXml(t.getClass(), t);

            //创建请求地址
            URL url = new URL(requestAddress);
            //获取参数
            byte[] postDataBytes = xml.getBytes(CHARSET_NAME);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), EncodingConsts.UTF8));
            for (int c; (c = in.read()) >= IntegerConsts.ZERO; ) {
                builder.append((char) c);
            }
        } catch (Exception ex) {
            logUtils.error(ex.getCause(), ex.getMessage());
        }

        //返回结果
        return builder.toString();
    }
}
