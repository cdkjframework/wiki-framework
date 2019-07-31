package com.cdkj.framework.consts.datasource;

/**
 * @ProjectName: cdkj.framework.core
 * @Package: com.cdkj.framework.core.consts
 * @ClassName: ApolloDataSourceConstant
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class ApolloDataSourceConstant extends ApolloBasicsConstant {

    /**
     * 类型
     */
    public static final String type = datasource + "type";

    /**
     * Mybatis驱动程序类名
     */
    public static final String mybatisDriverClassName = datasource + "mybatis.driverClassName";

    /**
     * mybatis文件夹
     */
    public static final String mybatisMybatisMapper = datasource + "mybatis.mybatisMapper";

    /**
     * mybatis 文件夹 Xml
     */
    public static final String mybatisMybatisMapperXml = datasource + "mybatis.mybatisMapperXml";

    /**
     * 连接属性
     */
    public static final String connectionProperties = datasource + "connectionProperties";

    /**
     * 过滤器
     */
    public static final String filters = datasource + "filters";

    /**
     * 验证查询
     */
    public static final String validationQuery = datasource + "validationQuery";

    /**
     * 返回测试
     */
    public static final String testOnReturn = datasource + "testOnReturn";

    /**
     * 初始尺寸
     */
    public static final String initialSize = datasource + "initialSize";

    /**
     * 最大活动
     */
    public static final String maxActive = datasource + "maxActive";

    /**
     * 最大等待
     */
    public static final String maxWait = datasource + "maxWait";

    /**
     * 逐出运行间隔时间
     */
    public static final String timeBetweenEvictionRunsMillis = datasource + "timeBetweenEvictionRunsMillis";

    /**
     * 最小隐形时间
     */
    public static final String minEvictableIdleTimeMillis = datasource + "minEvictableIdleTimeMillis";

    /**
     * 怠速试验
     */
    public static final String testWhileIdle = datasource + "testWhileIdle";

    /**
     * 借阅测验
     */
    public static final String testOnBorrow = datasource + "testOnBorrow";

    /**
     * 查询超时
     */
    public static final String validationQueryTimeout = datasource + "validationQueryTimeout";

    /**
     * 池准备语句
     */
    public static final String poolPreparedStatements = datasource + "poolPreparedStatements";

    /**
     * 每个连接大小的最大池准备语句数
     */
    public static final String maxPoolPreparedStatementPerConnectionSize = datasource + "maxPoolPreparedStatementPerConnectionSize";
}
