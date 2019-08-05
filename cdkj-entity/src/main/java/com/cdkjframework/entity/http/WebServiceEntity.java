package com.cdkjframework.entity.http;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.core.entity.http
 * @ClassName: WebServiceEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class WebServiceEntity {

    /**
     * 请求地址
     */
    private String webServiceAddress;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 方法
     */
    private String methodName;

    /**
     * 参数
     */
    private Object[] params;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据格式 默认为JSON
     */
    private String dataFormat = "json";
}
