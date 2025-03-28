package com.cdkjframework.builder;

import com.cdkjframework.enums.ResponseBuilderEnums;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.builder
 * @ClassName: ResponseBuilder
 * @Description: 响应生成器
 * @Author: xiaLin
 * @Version: 1.0
 */

@Data
public class ResponseBuilder implements Serializable {

    /**
     * 用来表明类的不同版本间的兼容性
     */
    @Serial
    private static final long serialVersionUID = 2200635915391111373L;

    /**
     * 业务操作结果 Code
     */
    private int code;

    /**
     * 请求时间
     */
    private long requestTime;

    /**
     * 返回时间
     */
    private long responseTime = System.currentTimeMillis();

    /**
     * 消息
     */
    private String message = "操作失败，请稍后重试！";

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 构造函数
     */
    public ResponseBuilder() {
        this.code = ResponseBuilderEnums.Success.getValue();
        this.message = ResponseBuilderEnums.Success.getName();
    }

    /**
     * 构造函数
     *
     * @param data 数据
     */
    public ResponseBuilder(Object data) {
        this.data = data;
        this.code = ResponseBuilderEnums.Success.getValue();
        this.message = ResponseBuilderEnums.Success.getName();
    }

    /**
     * 构造函数
     *
     * @param code 编码
     */
    public ResponseBuilder(int code) {
        this.code = code;
        ResponseBuilderEnums builderEnum = ResponseBuilderEnums.valueOf(String.valueOf(code));
        switch (builderEnum) {
          default -> this.message = ResponseBuilderEnums.Error.getName();
          case Success -> this.message = ResponseBuilderEnums.Success.getName();
          case Abnormal -> this.message = ResponseBuilderEnums.Abnormal.getName();
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
        this.code = ResponseBuilderEnums.Success.getValue();
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
        this.code = ResponseBuilderEnums.Error.getValue();
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
}
