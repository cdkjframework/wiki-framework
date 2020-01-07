package com.cdkjframework.core.spring.body;

import com.cdkjframework.util.encrypts.AesUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        byte[] buff = new byte[1024];
        int read = 0;
        while ((read = stream.read(buff, 0, buff.length)) > 0) {
            swapStream.write(buff, 0, read);
        }
        byte[] bytes = swapStream.toByteArray();
        String context = AesUtils.base64Decrypt(bytes);
        return new ByteArrayInputStream(context.getBytes());
    }
}
