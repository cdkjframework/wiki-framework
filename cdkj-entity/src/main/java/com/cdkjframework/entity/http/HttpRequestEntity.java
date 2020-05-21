package com.cdkjframework.entity.http;


import com.cdkjframework.enums.HttpMethodEnums;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkjframework.core.entity.http
 * @ClassName: HttpRequestEntity
 * @Description: HTTP 请求实体
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
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
    private HttpMethodEnums method = HttpMethodEnums.POST;
}
