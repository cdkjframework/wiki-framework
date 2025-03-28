package com.cdkjframework.enums;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.enums
 * @ClassName: AlgorithmTypeEnums
 * @Description: 加密类型枚举
 * @Author: xiaLin
 * @Version: 1.0
 */
public enum AlgorithmTypeEnums implements InterfaceEnum {

    /**
     * MD5 加密
     */
    MD5("MD5", "MD5 加密", ""),

    /**
     * HMAC-SHA256 加密
     */
    HMAC_SHA256("HmacSHA256", "HMAC-SHA256 加密", ""),

    /**
     * SHA256withRSA 加密
     */
    SHA256withRSA("SHA256withRSA", "SHA256withRSA 加密", ""),

    /**
     * HMAC-SHA384 加密
     */
    HMAC_SHA384("HMAC-SHA-384", "HMAC-SHA384 加密", ""),

    /**
     * HMAC-SHA512 加密
     */
    HMAC_SHA512("HMAC-SHA-512", "HMAC-SHA512 加密", ""),

    /**
     * RSA2 加密
     */
    RSA2("RSA2", "RSA2 加密", ""),

    /**
     * RSA 加密
     */
    RSA("RSA", "RSA 加密", ""),

    /**
     * SHA256 加密
     */
    SHA256("SHA-256", "SHA256 加密", ""),

    /**
     * RSA 加密
     */
    SHA384("SHA-384", "SHA384 加密", ""),

    /**
     * SHA512 加密
     */
    SHA512("SHA-512", "SHA512 加密", "");

    private final String value;
  private final String text;
  private final String node;

    /**
     * 构造函数
     *
     * @param value 值
     * @param text  说明
     * @param node  未使用
     */
    AlgorithmTypeEnums(String value, String text, String node) {
        this.value = value;
        this.text = text;
        this.node = node;
    }


    /**
     * 获取值
     *
     * @return 返回值
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * 获取描述
     *
     * @return 返描述
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
        return node;
    }
}
