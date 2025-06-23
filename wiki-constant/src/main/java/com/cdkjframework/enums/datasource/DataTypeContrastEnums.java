package com.cdkjframework.enums.datasource;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.enums.datasource
 * @ClassName: DataTypeContrastEnums
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum DataTypeContrastEnums {

    /**
     * 字符串
     */
    STRING("java.lang.String", "VARCHAR"),
    /**
     * 字节
     */
    BYTE("java.lang.byte[]", "BLOB"),
    /**
     * 长字符串
     */
    TEXT("java.lang.String", "TEXT"),
    /**
     * 长整型
     */
    LONG("java.lang.Long", "INTEGER"),
    /**
     * 整数
     */
    INTEGER("java.lang.Integer", "INT"),
    /**
     * 整数
     */
    INT("java.lang.Int", "INT"),
    /**
     * 布尔值
     */
    BOOLEAN("java.lang.Boolean", "BIT"),
    /**
     * 大整型
     */
    BIGINTEGER("java.math.BigInteger", "BIGINT"),
    /**
     * 浮点
     */
    FLOAT("java.lang.Float", "FLOAT"),
    /**
     * 单精度
     */
    DOUBLE("java.lang.Double", "DOUBLE"),
    /**
     * 大十进制
     */
    BIGDECIMAL("java.math.BigDecimal", "DECIMAL"),
    /**
     * 日期时间
     */
    LOCALDATETIME("java.time.LocalDateTime", "DATETIME"),
    /**
     * 日期
     */
    LOCALDATE("java.time.LocalDate", "DATETIME"),
    /**
     * 时间
     */
    LOCALTIME("java.time.LocalTime", "TIME"),
    /**
     * 时间戳
     */
    INSTANT("java.time.Instant", "TIMESTAMP");

    /**
     * JAVA 类型
     */
    private final String javaType;

    /**
     * 程序类型
     */
    private final String dataType;

    /**
     * 获取 JAVA类型
     *
     * @return 返回结果
     */
    public String getJavaType() {
        return javaType;
    }

    /**
     * 获取 JAVA类型
     *
     * @return 返回结果
     */
    public String getDataType() {
        return dataType;
    }

    DataTypeContrastEnums(String javaType, String dataType) {
        this.javaType = javaType;
        this.dataType = dataType;
    }
}
