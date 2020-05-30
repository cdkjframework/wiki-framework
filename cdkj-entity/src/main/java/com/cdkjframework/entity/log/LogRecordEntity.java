package com.cdkjframework.entity.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.entity
 * @ClassName: LogRecordEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class LogRecordEntity {

    /**
     * 日志ID
     */
    private String id;

    /**
     * 序号
     */
    private String serialNumber;

    /**
     * 模块
     */
    private String modular;

    /**
     * 执行类
     */
    private String executionClass;

    /**
     * 方法
     */
    private String method;

    /**
     * 访问 地址
     */
    private String servletPath;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 操作账号
     */
    private String userName;

    /**
     * 操作人名称
     */
    private String operatorName;

    /**
     * 客户端 IP
     */
    private String clientIp;

    /**
     * 请求参数
     */
    private String parameter;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 返回错误信息
     */
    private String resultErrorMessage;

    /**
     * 状态
     */
    private int executionState = -1;

    /**
     * 操作时间
     */
    private Long addTime;

    /**
     * 返回结果时间
     */
    private long resultTime;
}
