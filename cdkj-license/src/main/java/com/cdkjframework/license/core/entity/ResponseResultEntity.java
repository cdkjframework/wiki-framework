package com.cdkjframework.license.core.entity;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.enums.ResponseBuilderEnums;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * <p>请求结果（封装）</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:32 下午 2020/8/21
 */
@ApiModel(value = "请求结果（封装）")
public class ResponseResultEntity {

    /**
     * 响应结果状态码
     */
    @ApiModelProperty(value = "响应结果状态码")
    private Integer status;

    /**
     * 响应结果消息
     */
    @ApiModelProperty(value = "响应结果消息")
    private String message;

    /**
     * 响应结果对应的（包含）的数据，空的话不反序列话
     */
    @JsonInclude(value = Include.NON_NULL)
    @ApiModelProperty(value = "响应结果对应的（包含）的数据，空的话不反序列话")
    private Object data;

    /**
     * 响应时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "响应时间")
    private Date timeStamp = new Date();

    public ResponseResultEntity() {
        this.status = null;
        this.message = null;
        this.data = null;
    }

    public ResponseResultEntity(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public ResponseResultEntity(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseResultEntity(ResponseBuilderEnums resultCode, String message) {
        this.status = resultCode.getValue();
        this.message = message;
    }

    /**
     * 默认成功返回的实例
     *
     * @param data 对象
     */
    private ResponseResultEntity(Object data) {
        this.status = ResponseBuilderEnums.Success.getValue();
        this.message = ResponseBuilderEnums.Success.getName();
        this.data = data;
    }

    private ResponseResultEntity(String message, Object data) {
        this.status = ResponseBuilderEnums.Success.getValue();
        this.message = message;
        this.data = data;
    }

    private ResponseResultEntity(ResponseBuilderEnums code) {
        this.status = code.getValue();
        this.message = code.getName();
        this.data = null;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public static ResponseResultEntity ok(Object data) {
        return new ResponseResultEntity(data);
    }

    public static ResponseResultEntity ok(String message, Object data) {
        return new ResponseResultEntity(data);
    }

    public static ResponseResultEntity fail(ResponseBuilderEnums code) {
        return new ResponseResultEntity(code);
    }

    public static ResponseResultEntity fail(String message) {
        return new ResponseResultEntity(HttpStatus.BAD_REQUEST.value(), "失败", message);
    }
}
