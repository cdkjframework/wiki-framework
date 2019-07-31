package com.cdkj.framework.entity.log;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.entity
 * @ClassName: LogRecordEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

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
    private String accountName;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModular() {
        return modular;
    }

    public void setModular(String modular) {
        this.modular = modular;
    }

    public String getServletPath() {
        return servletPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getExecutionState() {
        return executionState;
    }

    public void setExecutionState(int executionState) {
        this.executionState = executionState;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public long getResultTime() {
        return resultTime;
    }

    public void setResultTime(long resultTime) {
        this.resultTime = resultTime;
    }
}
