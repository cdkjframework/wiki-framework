package com.cdkjframework.core.spring.body;

import com.cdkjframework.constant.EncodingConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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
     * 是否加密
     */
    private boolean isEncryption;

    /**
     * 构造函数
     *
     * @param httpInputMessage 消息
     */
    public HttpInputMessageAdvice(HttpInputMessage httpInputMessage, boolean encryption) throws IOException {
        isEncryption = encryption;
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
    private synchronized InputStream getBody(InputStream stream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[IntegerConsts.BYTE_LENGTH];
        int read;
        while ((read = stream.read(buff, IntegerConsts.ZERO, buff.length)) > IntegerConsts.ZERO) {
            swapStream.write(buff, IntegerConsts.ZERO, read);
        }
        if (!isEncryption) {
            return new ByteArrayInputStream(swapStream.toByteArray());
        }
        byte[] bytes = swapStream.toByteArray();
        String streamString = new String(bytes, StandardCharsets.UTF_8);
        String context = AesUtils.base64Decrypt(streamString.trim());
        return new ByteArrayInputStream(context.getBytes(StandardCharsets.UTF_8));
    }
}
