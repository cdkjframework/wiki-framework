package com.cdkjframework.mybatispro.core;

import com.cdkjframework.mybatispro.core.config.GlobalConfig;
import com.cdkjframework.mybatispro.core.incrementer.DefaultIdentifierGenerator;
import com.cdkjframework.mybatispro.core.incrementer.IdentifierGenerator;
import com.cdkjframework.mybatispro.core.injector.SqlRunnerInjector;
import com.cdkjframework.mybatispro.core.toolkit.GlobalConfigUtils;
import com.cdkjframework.mybatispro.core.toolkit.IdWorker;
import com.cdkjframework.mybatispro.core.toolkit.NetUtils;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.InetAddress;
import java.util.Properties;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core
 * @ClassName: MybatisSqlSessionFactoryBuilder
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public class MybatisSqlSessionFactoryBuilder extends SqlSessionFactoryBuilder {

  @SuppressWarnings("Duplicates")
  @Override
  public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
    try {
      MybatisXMLConfigBuilder parser = new MybatisXMLConfigBuilder(reader, environment, properties);
      return build(parser.parse());
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error building SqlSession.", e);
    } finally {
      ErrorContext.instance().reset();
      try {
        reader.close();
      } catch (IOException e) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }

  @SuppressWarnings("Duplicates")
  @Override
  public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
    try {
      MybatisXMLConfigBuilder parser = new MybatisXMLConfigBuilder(inputStream, environment, properties);
      return build(parser.parse());
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error building SqlSession.", e);
    } finally {
      ErrorContext.instance().reset();
      try {
        inputStream.close();
      } catch (IOException e) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }

  @Override
  public SqlSessionFactory build(Configuration configuration) {
    GlobalConfig globalConfig = GlobalConfigUtils.getGlobalConfig(configuration);

    final IdentifierGenerator identifierGenerator;
    if (null == globalConfig.getIdentifierGenerator()) {
      GlobalConfig.Sequence sequence = globalConfig.getSequence();
      if (sequence.getWorkerId() != null && sequence.getDatacenterId() != null) {
        identifierGenerator = new DefaultIdentifierGenerator(sequence.getWorkerId(), sequence.getDatacenterId());
      } else {
        NetUtils.NetProperties netProperties = new NetUtils.NetProperties(sequence.getPreferredNetworks(), sequence.getIgnoredInterfaces());
        InetAddress inetAddress = new NetUtils(netProperties).findFirstNonLoopbackAddress();
        identifierGenerator = new DefaultIdentifierGenerator(inetAddress);
      }
      globalConfig.setIdentifierGenerator(identifierGenerator);
    } else {
      identifierGenerator = globalConfig.getIdentifierGenerator();
    }
    IdWorker.setIdentifierGenerator(identifierGenerator);

    if (globalConfig.isEnableSqlRunner()) {
      new SqlRunnerInjector().inject(configuration);
    }

    SqlSessionFactory sqlSessionFactory = super.build(configuration);

    // 缓存 sqlSessionFactory
    globalConfig.setSqlSessionFactory(sqlSessionFactory);

    return sqlSessionFactory;
  }
}

