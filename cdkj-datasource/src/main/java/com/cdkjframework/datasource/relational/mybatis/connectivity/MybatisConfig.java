package com.cdkjframework.datasource.relational.mybatis.connectivity;

import com.cdkjframework.datasource.relational.mybatis.LogbackImpl;
import com.cdkjframework.datasource.relational.mybatis.config.MybatisReadConfig;
import com.cdkjframework.util.log.LogUtils;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.database.mybatis.postgresql.connectivity
 * @ClassName: PostgreSqlMyBatisConfig
 * @Description: MyBatis 数据连接
 * @Author: xiaLin
 * @Version: 1.0
 */

@Configuration
@EnableTransactionManagement
public class MybatisConfig {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(MybatisConfig.class);

    /**
     * 读取配置
     */
    @Autowired
    private MybatisReadConfig mybatisSqlConfig;

    /**
     * 数据源
     */
    @Resource(name = "mybatisDataSource")
    @Qualifier("mybatisDataSource")
    private DataSource mybatisDataSource;

    /**
     * mapper路径
     */
    private static String MAPPER_LOCATION = "classpath*:";

    /**
     * 创建 SQL 连接工厂
     *
     * @return 返回结果
     * @throws Exception 异常信息
     */
    @Bean
    public SqlSessionFactory mybatisSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        try {
            sqlSessionFactoryBean.setDataSource(mybatisDataSource);
            //配置信息
            sqlSessionFactoryBean.setConfiguration(myBatisConfiguration());
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

            //Mapper xml 路径
            MAPPER_LOCATION += mybatisSqlConfig.getMybatisMapperXml();
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_LOCATION));

            //分页
            sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper()});
        } catch (Exception ex) {
            logUtil.error(ex.getMessage());
        }
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 平台事务管理器
     *
     * @return 返回结果
     * @throws SQLException 异常信息
     */
    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(mybatisDataSource);
    }

    /**
     * sql会话模板
     *
     * @param sqlSessionFactory SQL 会话工厂
     * @return 返回结果
     * @throws Exception 异常信息
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 配置信息
     *
     * @return 返回配置结果
     */
    private org.apache.ibatis.session.Configuration myBatisConfiguration() {
        //配置
        org.apache.ibatis.session.Configuration configuration =
                new org.apache.ibatis.session.Configuration();
        Properties properties = new Properties();
        configuration.setVariables(properties);
        configuration.setCallSettersOnNulls(true);
        //这个配置使全局的映射器启用或禁用缓存。系统默认值是true
        configuration.setCacheEnabled(false);
        //全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载。 系统默认值是true
        configuration.setLazyLoadingEnabled(false);
        // 允许或不允许多种结果集从一个单独的语句中返回（需要适合的驱动）。 系统默认值是true
        configuration.setMultipleResultSetsEnabled(false);
        // 使用列标签代替列名。不同的驱动在这方便表现不同。参考驱动文档或充分测试两种方法来决定所使用的驱动。 系统默认值是true
        configuration.setUseColumnLabel(true);
        // 允许 JDBC 支持生成的键。需要适合的驱动。如果设置为 true 则这个设置强制生成的键被使用，尽管一些驱动拒绝兼容但仍然有效（比如
        //            Derby）。 系统默认值是false
        configuration.setUseGeneratedKeys(false);
        // 配置默认的执行器。SIMPLE 执行器没有什么特别之处。REUSE 执行器重用预处理语句。BATCH 执行器重用语句和批量更新 系统默认值是SIMPLE
        configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
        // 设置超时时间，它决定驱动等待一个数据库响应的时间。 系统默认值是null
        configuration.setDefaultStatementTimeout(25000);
        // 设置字段和类是否支持驼峰命名的属性。 系统默认值是false
        configuration.setMapUnderscoreToCamelCase(true);

        //  添加日志输出
        configuration.setLogImpl(LogbackImpl.class);

        //返回结果
        return configuration;
    }

    /**
     * 分页设置
     */
    private PageInterceptor pageHelper() {
        // 分页控件
        PageInterceptor pageHelper = new PageInterceptor();
        Properties prop = new Properties();
        // 启用合理化后，如果pageNumM<1会查询第一页，如果pageNum>pages会查询最后一页
        prop.setProperty("reasonable", "false");

        // 设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
        // 和startPage中的pageNum效果一样
        prop.setProperty("offsetAsPageNum", "true");
        // 设置为true时，使用RowBounds分页会进行count查询
        prop.setProperty("rowBoundsWithCount", "true");
        // 设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果
        // 相当于没有执行分页查询，但是返回结果仍然是Page类型
        prop.setProperty("pageSizeZero", "true");
        // 支持通过Mapper接口参数来传递分页参数
        prop.setProperty("supportMethodsArguments", "false");
        // always总是返回PageInfo类型,check检查返回类型是否为PageInfo,none返回Page
        prop.setProperty("returnPageInfo", "none");
        //添加插件
        pageHelper.setProperties(prop);

        return pageHelper;
    }
}
