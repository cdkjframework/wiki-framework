package com.cdkjframework.core.spring.body;

import com.cdkjframework.constant.EncodingConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.core.spring.body
 * @ClassName: HttpInputMessageAdvice
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class HttpInputMessageAdvice implements HttpInputMessage {

    /**
     * 日志
     */
    private final LogUtils LOG_UTILS = LogUtils.getLogger(HttpInputMessageAdvice.class);

    /**
     * 头部信息
     */
    private HttpHeaders headers;

    /**
     * 消息休
     */
    private InputStream body;

    /**
     * 构造函数
     *
     * @param httpInputMessage 消息
     */
    public HttpInputMessageAdvice(HttpInputMessage httpInputMessage) throws IOException {
        body = getBody(httpInputMessage.getBody());
        headers = httpInputMessage.getHeaders();
    }

    @Override
    public InputStream getBody() {
        return body;
    }

    /**
     * Return the headers of this message.
     *
     * @return a corresponding HttpHeaders object (never {@code null})
     */
    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    /**
     * 获取输入参数解密
     *
     * @param stream 流
     * @return 返回解密数据
     * @throws IOException IO异常
     */
    private InputStream getBody(InputStream stream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[IntegerConsts.BYTE_LENGTH];
        int read;
        while ((read = stream.read(buff, IntegerConsts.ZERO, buff.length)) > IntegerConsts.ZERO) {
            swapStream.write(buff, IntegerConsts.ZERO, read);
        }
        byte[] bytes = swapStream.toByteArray();
        LOG_UTILS.info(new String(bytes, EncodingConsts.UTF8));
        String context = AesUtils.base64Decrypt(bytes);
        LOG_UTILS.info(context);
        context = URLDecoder.decode(context, EncodingConsts.UTF8);
        LOG_UTILS.info(context);
        return new ByteArrayInputStream(context.getBytes(EncodingConsts.UTF8));
    }
}
