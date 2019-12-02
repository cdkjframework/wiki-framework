package com.cdkjframework.constant.datasource;

/**
 * @ProjectName: cdkjframework.core
 * @Package: com.cdkjframework.core.consts
 * @ClassName: ApolloDataSourceConstant
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class ApolloDataSourceConsts extends ApolloBasicsConsts {

    /**
     * 类型
     */
    public static final String TYPE = SPRING_DATASOURCE + "TYPE";

    /**
     * Mybatis驱动程序类名
     */
    public static final String MYBATIS_DRIVER_CLASS_NAME = SPRING_DATASOURCE + "mybatis.driverClassName";

    /**
     * mybatis文件夹
     */
    public static final String MYBATIS_MYBATIS_MAPPER = SPRING_DATASOURCE + "mybatis.mybatisMapper";

    /**
     * mybatis 文件夹 Xml
     */
    public static final String MYBATIS_MYBATIS_MAPPER_XML = SPRING_DATASOURCE + "mybatis.mybatisMapperXml";

    /**
     * 连接属性
     */
    public static final String CONNECTION_PROPERTIES = SPRING_DATASOURCE + "CONNECTION_PROPERTIES";

    /**
     * 过滤器
     */
    public static final String FILTERS = SPRING_DATASOURCE + "filters";

    /**
     * 验证查询
     */
    public static final String VALIDATION_QUERY = SPRING_DATASOURCE + "validationQuery";

    /**
     * 返回测试
     */
    public static final String TEST_ON_RETURN = SPRING_DATASOURCE + "testOnReturn";

    /**
     * 初始尺寸
     */
    public static final String INITIAL_SIZE = SPRING_DATASOURCE + "initialSize";

    /**
     * 最大活动
     */
    public static final String MAX_ACTIVE = SPRING_DATASOURCE + "maxActive";

    /**
     * 最大等待
     */
    public static final String MAX_WAIT = SPRING_DATASOURCE + "maxWait";

    /**
     * 逐出运行间隔时间
     */
    public static final String TIME_BETWEEN_EVICTION_RUNS_MILLIS = SPRING_DATASOURCE + "timeBetweenEvictionRunsMillis";

    /**
     * 最小隐形时间
     */
    public static final String MIN_EVICTABLE_IDLE_TIME_MILLIS = SPRING_DATASOURCE + "minEvictableIdleTimeMillis";

    /**
     * 怠速试验
     */
    public static final String TEST_WHILE_IDLE = SPRING_DATASOURCE + "testWhileIdle";

    /**
     * 借阅测验
     */
    public static final String TEST_ON_BORROW = SPRING_DATASOURCE + "testOnBorrow";

    /**
     * 查询超时
     */
    public static final String VALIDATION_QUERY_TIMEOUT = SPRING_DATASOURCE + "validationQueryTimeout";

    /**
     * 池准备语句
     */
    public static final String POOL_PREPARED_STATEMENTS = SPRING_DATASOURCE + "poolPreparedStatements";

    /**
     * 每个连接大小的最大池准备语句数
     */
    public static final String MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE = SPRING_DATASOURCE + "maxPoolPreparedStatementPerConnectionSize";
}
