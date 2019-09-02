package com.cdkjframework.core.spring.filter;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.core.spring
 * @ClassName: HttpServletContentRequestWrapper
 * @Description: HTTP servlet请求包装
 * @Author: xiaLin
 * @Version: 1.0
 */

public class HttpServletContentRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 请求头
     */
    private final Map<String, String> headersList = new HashMap<>();

    /**
     * 添加请求头信息
     *
     * @param name  key 名称
     * @param value 值
     */
    public void putHeader(String name, String value) {
        this.headersList.put(name, value);
    }

    /**
     * 获取请求头信息
     *
     * @param name key 名称
     * @return 返回值
     */
    @Override
    public String getHeader(String name) {
        // check the custom headers first
        String headerValue = headersList.get(name);

        if (headerValue != null) {
            return headerValue;
        }

        // else return from into the original wrapped object
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    /**
     * 获取到全部请求头信息
     *
     * @return 返回列表
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        // create a set of the custom header names
        Set<String> set = new HashSet<>(headersList.keySet());

        // now add the headers from the wrapped request object
        @SuppressWarnings("unchecked")
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            // add the names of the request headers into the list
            String n = e.nextElement();
            set.add(n);
        }

        // create an enumeration from the set and return
        return Collections.enumeration(set);
    }

    /**
     * 数据信息二进制
     */
    private byte[] body;

    /**
     * 缓冲区读取
     */
    private BufferedReader reader;

    /**
     * servlet输入流
     */
    private ServletInputStream inputStream;

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
        inputStream = new RequestCachingInputStream(body);
    }

    /**
     * 构造函数
     *
     * @param request 请求
     * @throws IOException 异常信息
     */
    public HttpServletContentRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        loadBody(request);
    }

    /**
     * 加载数据
     *
     * @param request 请求
     * @throws IOException IO异常信息
     */
    private void loadBody(HttpServletRequest request) throws IOException {
        body = IOUtils.toByteArray(request.getInputStream());
        inputStream = new RequestCachingInputStream(body);
    }

    /**
     * 获取数据流
     *
     * @return 返回数据流信息
     * @throws IOException IO异常信息
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (inputStream != null) {
            return inputStream;
        }
        return super.getInputStream();
    }

    /**
     * 读取流
     *
     * @return 返回结果
     * @throws IOException 异常信息
     */
    @Override
    public BufferedReader getReader() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
        }

        //返回读取结果
        return reader;
    }

    /**
     * 请求缓存输入流
     */
    private static class RequestCachingInputStream extends ServletInputStream {

        /**
         * 字节数组输入流
         */
        private final ByteArrayInputStream inputStream;

        /**
         * 构造函数
         *
         * @param bytes 数据流
         */
        public RequestCachingInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }

        /**
         * 读取
         *
         * @return 返回结果
         * @throws IOException 异常信息
         */
        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        /**
         * 是否成功
         *
         * @return 返回布尔值
         */
        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        /**
         * 是否可读
         *
         * @return 反回结果
         */
        @Override
        public boolean isReady() {
            return true;
        }

        /**
         * 设置读取监听
         *
         * @param readlistener 监听
         */
        @Override
        public void setReadListener(ReadListener readlistener) {
        }
    }
}
