package com.cdkj.framework.entity.http;


import com.cdkj.framework.util.tool.StringUtil;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.core.entity.http
 * @ClassName: WebServiceEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

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

    public String getWebServiceAddress() {
        return webServiceAddress;
    }

    public void setWebServiceAddress(String webServiceAddress) {
        this.webServiceAddress = webServiceAddress;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataFormat() {
        if (!StringUtil.isNullAndSpaceOrEmpty(dataFormat)) {
            dataFormat = dataFormat.toLowerCase();
        }
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }
}
