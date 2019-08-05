package com.cdkjframework.datasource.relational.mybatis.connectivity;

import com.cdkjframework.datasource.relational.mybatis.config.MybatisReadConfig;
import com.cdkjframework.util.log.LogUtil;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
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
    private LogUtil logUtil = LogUtil.getLogger(MybatisConfig.class);

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

            //配置
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            Properties properties = new Properties();
            properties.put("callSettersOnNulls", true);
            configuration.setVariables(properties);
            configuration.setCallSettersOnNulls(true);

            sqlSessionFactoryBean.setConfiguration(configuration);
            //分页控件
            PageHelper pageHelper = new PageHelper();
            Properties prop = new Properties();
            //启用合理化后，如果pageNumM<1会查询第一页，如果pageNum>pages会查询最后一页
            prop.setProperty("reasonable", "true");
            prop.setProperty("offsetAsPageNum", "true");
            prop.setProperty("rowBoundsWithCount", "true");
            //添加插件
            pageHelper.setProperties(prop);
            sqlSessionFactoryBean.setPlugins(new Interceptor[]{(Interceptor) pageHelper});
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

            //Mapper xml 路径
            MAPPER_LOCATION += mybatisSqlConfig.getMybatisMapperXml();
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_LOCATION));
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
}
