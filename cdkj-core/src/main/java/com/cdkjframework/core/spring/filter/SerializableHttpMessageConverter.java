package com.cdkjframework.core.spring.filter;

import com.cdkjframework.util.log.LogUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.util.Base64;

import static org.springframework.http.converter.FormHttpMessageConverter.DEFAULT_CHARSET;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.serialization
 * @ClassName: SerializableHttpMessageConverter
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
public class SerializableHttpMessageConverter extends AbstractHttpMessageConverter<Serializable> {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(SerializableHttpMessageConverter.class);

    /**
     * 构造函数
     */
    public SerializableHttpMessageConverter() {
        // 构造方法中指明consumes（req）和produces（resp）的类型，指明这个类型才会使用这个converter
        super();
    }

    /**
     * 支架
     *
     * @param clazz 类型
     * @return 返回结果
     */
    @Override
    protected boolean supports(Class<?> clazz) {
        // 使用Serializable，这里可以直接返回true
        // 使用object，这里还要加上Serializable接口实现类判断
        // 根据自己的业务需求加上其他判断
        return true;
    }

    /**
     * 内部读取
     *
     * @param clazz        类
     * @param inputMessage 输入信息
     * @return 返回序列化信息
     * @throws IOException                     IO异常
     * @throws HttpMessageNotReadableException 异常信息
     */
    @Override
    protected Serializable readInternal(Class<? extends Serializable> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        String body = StreamUtils.copyToString(inputMessage.getBody(), DEFAULT_CHARSET);

        byte[] bytes = StreamUtils.copyToByteArray(inputMessage.getBody());
        // base64使得二进制数据可视化，便于测试
        ByteArrayInputStream bytesInput = new ByteArrayInputStream(Base64.getDecoder().decode(bytes));
        ObjectInputStream objectInput = new ObjectInputStream(bytesInput);
        try {
            return (Serializable) objectInput.readObject();
        } catch (ClassNotFoundException e) {
            logUtil.error("exception when java deserialize, the input is:{}" + new String(bytes, "UTF-8"));
            return null;
        }
    }

    /**
     * 内部写入
     *
     * @param serializable  反序列化
     * @param outputMessage 输出信息
     * @throws IOException                     IO异常
     * @throws HttpMessageNotReadableException 异常信息
     */
    @Override
    protected void writeInternal(Serializable serializable, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream();
        ObjectOutputStream objectOutput = new ObjectOutputStream(bytesOutput);
        objectOutput.writeObject(serializable);
        // base64使得二进制数据可视化，便于测试
        outputMessage.getBody().write(Base64.getEncoder().encode(bytesOutput.toByteArray()));
    }
}
