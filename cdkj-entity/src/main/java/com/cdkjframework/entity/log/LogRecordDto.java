package com.cdkjframework.entity.log;

import com.cdkjframework.entity.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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
public class LogRecordDto {

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
     * 请求开始搜索时间
     */
    private LocalDateTime addTimeStart;

    /**
     * 请求结束搜索时间
     */
    private LocalDateTime addTimeEnd;

    /**
     * 返回结果时间
     */
    private long resultTime;

    /**
     * 计算后的页码大小
     */
    private int pageSize = 10;

    /**
     * 当前页索引
     */
    private int pageIndex = 1;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序类型
     */
    private String sortType = "descending";

    /**
     * 所在机构ID
     */
    protected String organizationId;

    /**
     * 所在机构编码
     */
    protected String organizationCode;

    /**
     * 所在机构名称
     */
    protected String organizationName;

    /**
     * 所在机构上级ID
     */
    protected String topOrganizationId;

    /**
     * 所在机构上级编码
     */
    protected String topOrganizationCode;

    /**
     * 所在机构上级名称
     */
    protected String topOrganizationName;
}
