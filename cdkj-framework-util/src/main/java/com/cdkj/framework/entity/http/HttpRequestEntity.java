package com.cdkj.framework.entity.http;


import com.cdkj.framework.enums.HttpMethodEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkj.framework.core.entity.http
 * @ClassName: HttpRequestEntity
 * @Description: HTTP 请求实体
 * @Author: xiaLin
 * @Version: 1.0
 */

public class HttpRequestEntity {

    /**
     * 请求地址
     */
    private String requestAddress;

    /**
     * 是否压缩数据
     */
    private boolean compress;

    /**
     * 编码
     */
    private String charset = "UTF-8";

    /**
     * 内容类型
     */
    private String contentType = "application/json; charset=utf-8";

    /**
     * 参数 POST 或者 GET 用
     */
    private Object data = null;

    /**
     * 参数 POST 或者 GET 用
     */
    private Map<String, Object> paramsMap = new HashMap<>();

    /**
     * 只限 POST 使用(优先级大于 map 参数)
     */
    private List<Object> objectList = new ArrayList<>();

    /**
     * 请求头
     */
    private Map<String, String> headerMap = new HashMap<>();

    /**
     * 请求类型
     */
    private HttpMethodEnum method = HttpMethodEnum.POST;

    public String getRequestAddress() {
        return requestAddress;
    }

    public void setRequestAddress(String requestAddress) {
        this.requestAddress = requestAddress;
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public List<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public HttpMethodEnum getMethod() {
        return method;
    }

    public void setMethod(HttpMethodEnum method) {
        this.method = method;
    }
}
