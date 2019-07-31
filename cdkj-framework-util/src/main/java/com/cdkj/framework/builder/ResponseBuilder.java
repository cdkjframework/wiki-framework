package com.cdkj.framework.builder;


import com.cdkj.framework.enums.ResponseBuilderEnum;
import com.cdkj.framework.util.tool.StringUtil;

import java.io.Serializable;

/**
 * @ProjectName: com.cdkj.framework.QRcode
 * @Package: com.cdkj.framework.core.builder
 * @ClassName: ResponseBuilder
 * @Description: 响应生成器
 * @Author: xiaLin
 * @Version: 1.0
 */

public class ResponseBuilder implements Serializable {

    /**
     * 用来表明类的不同版本间的兼容性
     */
    private static final long serialVersionUID = 2200635915391111373L;

    /**
     * 业务操作结果 Code
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 构造函数
     */
    public ResponseBuilder() {
        this.code = ResponseBuilderEnum.Success.getValue();
        this.message = ResponseBuilderEnum.Success.getName();
    }

    /**
     * 构造函数
     *
     * @param data 数据
     */
    public ResponseBuilder(Object data) {
        this.data = data;
        this.code = ResponseBuilderEnum.Success.getValue();
        this.message = ResponseBuilderEnum.Success.getName();
    }

    /**
     * 构造函数
     *
     * @param code 编码
     */
    public ResponseBuilder(int code) {
        this.code = code;
        ResponseBuilderEnum builderEnum = ResponseBuilderEnum.valueOf(String.valueOf(code));
        switch (builderEnum) {
            case Error:
                this.message = ResponseBuilderEnum.Error.getName();
                break;
            case Success:
                this.message = ResponseBuilderEnum.Success.getName();
                break;
            case Abnormal:
                this.message = ResponseBuilderEnum.Abnormal.getName();
                break;
        }
    }

    /**
     * 构造函数
     *
     * @param code    编码
     * @param message 信息
     */
    public ResponseBuilder(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     *
     * @param code    编码
     * @param message 信息
     * @param data    数据
     */
    public ResponseBuilder(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功 Builder
     *
     * @param message 消息
     */
    public void success(String message) {
        this.success(message, null);
    }

    /**
     * 成功 Builder
     *
     * @param message 消息
     * @param data    数据
     */
    public void success(String message, Object data) {
        this.code = ResponseBuilderEnum.Success.getValue();
        this.message = message;
        this.data = data;
    }

    /**
     * 成功 Builder
     */
    public static ResponseBuilder successBuilder() {
        ResponseBuilder builder = new ResponseBuilder();
        builder.success("成功");

        //返回结果
        return builder;
    }

    /**
     * 成功 Builder
     *
     * @param message 消息
     */
    public static ResponseBuilder successBuilder(String message) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.success(message);

        //返回结果
        return builder;
    }

    /**
     * 成功 Builder
     *
     * @param data 消息
     */
    public static ResponseBuilder successBuilder(Object data) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.success("成功", data);

        //返回结果
        return builder;
    }

    /**
     * 成功 Builder
     *
     * @param message 消息
     * @param data    数据
     */
    public static ResponseBuilder successBuilder(String message, Object data) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.success(message, data);

        //返回结果
        return builder;
    }

    /**
     * 失败 Builder
     *
     * @param message 消息
     */
    public void fail(String message) {
        this.fail(message, null);
    }


    /**
     * 失败 Builder
     *
     * @param message 消息
     * @param data    数据
     */
    public void fail(String message, Object data) {
        this.code = ResponseBuilderEnum.Error.getValue();
        this.message = message;
        this.data = data;
    }

    /**
     * 失败 Builder
     *
     * @param message 消息
     */
    public static ResponseBuilder failBuilder(String message) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.fail(message);

        //返回结果
        return builder;
    }

    /**
     * 失败 Builder
     */
    public static ResponseBuilder failBuilder() {
        ResponseBuilder builder = new ResponseBuilder();
        builder.fail("失败");

        //返回结果
        return builder;
    }

    /**
     * 失败 Builder
     *
     * @param message 消息
     * @param data    数据
     */
    public static ResponseBuilder failBuilder(String message, Object data) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.fail(message, data);

        //返回结果
        return builder;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        if (StringUtil.isNullAndSpaceOrEmpty(this.message)) {
            this.message = "操作失败，请稍后重试！";
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}