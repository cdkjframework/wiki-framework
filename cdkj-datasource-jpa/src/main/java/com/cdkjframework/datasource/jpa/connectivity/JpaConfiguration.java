package com.cdkjframework.datasource.jpa.connectivity;

import com.cdkjframework.datasource.jpa.config.JpaConfig;
import com.cdkjframework.util.tool.StringUtils;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.database.jpa.postgresql.connectivity
 * @ClassName: JpaConfig
 * @Description: JPA 配置 需要 mybatis 同时使用
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@EnableTransactionManagement
public class JpaConfiguration {

    /**
     * 数据源
     */
    @Resource(name = "jpaDataSource")
    @Qualifier("jpaDataSource")
    private DataSource jpaDataSource;

    /**
     * 配置
     */
    @Autowired
    private JpaConfig jpaReadConfig;

    /**
     * JAP 管理工厂
     *
     * @return 返回实体管理工厂
     */
<<<<<<< HEAD
    @Bean(name = "jpaEntityManagerFactory")
=======
>>>>>>> a5f9c671e949caeb9a3ff179418aadcbfa050c60
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(jpaReadConfig.isShowSql());

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPackagesToScan(jpaReadConfig.getPackagesToScan());
        factory.setDataSource(jpaDataSource);

        Map<String, Object> jpaProperties = new HashMap<String, Object>(6);
        jpaProperties.put("hibernate.format_sql", jpaReadConfig.isFormatSql());
        jpaProperties.put("hibernate.dialect", jpaReadConfig.getDialect());
        jpaProperties.put("hibernate.ejb.naming_strategy", jpaReadConfig.getNamingStrategy());
        jpaProperties.put("hibernate.jdbc.batch_size", jpaReadConfig.getBatchSize());
        jpaProperties.put("spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults", false);
        //设置自动更新表结构
        if (StringUtils.isNotNullAndEmpty(jpaReadConfig.getHbm2ddlAuto())) {
            jpaProperties.put("hibernate.hbm2ddl.auto", jpaReadConfig.getHbm2ddlAuto());
        }
        //设置配置
        factory.setJpaPropertyMap(jpaProperties);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    /**
     * JAP 事件
     *
     * @return 返回事务接口
     */
    @Bean(name = "jpaTransactionManager")
<<<<<<< HEAD
    public PlatformTransactionManager transactionManager(@Qualifier("jpaEntityManagerFactory") EntityManagerFactory jpaEntityManagerFactory) {
=======
    public PlatformTransactionManager transactionManager() {
>>>>>>> a5f9c671e949caeb9a3ff179418aadcbfa050c60
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(jpaEntityManagerFactory);
        return txManager;
    }
}
