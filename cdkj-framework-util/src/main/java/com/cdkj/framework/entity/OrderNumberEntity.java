package com.cdkj.framework.entity;

import com.cdkj.framework.util.tool.StringUtil;

/**
 * @ProjectName: com.cdkj.framework.core
 * @Package: com.cdkj.framework.core.entity
 * @ClassName: OrderNumberEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class OrderNumberEntity {

    /**
     * 构造函数 1
     */
    public OrderNumberEntity() {
    }

    /**
     * 构造函数 2
     *
     * @param prefix   前缀
     * @param length   长度
     * @param serialNo 序号
     */
    public OrderNumberEntity(String prefix, int length, int serialNo) {
        this.prefix = prefix;
        this.length = length;
        this.serialNo = serialNo;
    }

    /**
     * 构造函数 3
     *
     * @param serialNo 序号
     * @param noName
     * @param ruleNo   规则
     * @param length   长度
     * @param prefix   前缀
     */
    public OrderNumberEntity(long serialNo, String noName, long ruleNo, int length, String prefix) {
        this.serialNo = serialNo;
        this.noName = noName;
        this.ruleNo = ruleNo;
        this.length = length;
        this.prefix = prefix;
    }

    /**
     * 获取下一个单号
     *
     * @return 返回结果
     */
    public synchronized String nextNo() {
        serialNo++;
        return StringUtil.leftPad(String.valueOf(serialNo), length, "0");
    }

    /**
     * 创建单号
     *
     * @return 返回结果
     */
    public String createNextNo() {
        return StringUtil.leftPad(String.valueOf(serialNo + 1), length, "0");
    }

    private static final long serialVersionUID = 5333907085312643274L;

    /**
     * 序号
     */
    private long serialNo;

    /**
     *
     */
    private String noName;

    /**
     * 规则号
     */
    private long ruleNo;

    /**
     * 长度
     */
    private int length;

    /**
     * 前缀
     */
    private String prefix;

    public long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    public String getNoName() {
        return noName;
    }

    public void setNoName(String noName) {
        this.noName = noName;
    }

    public long getRuleNo() {
        return ruleNo;
    }

    public void setRuleNo(long ruleNo) {
        this.ruleNo = ruleNo;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
