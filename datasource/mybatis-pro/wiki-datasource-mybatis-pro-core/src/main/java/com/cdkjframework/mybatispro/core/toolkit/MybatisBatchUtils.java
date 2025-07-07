package com.cdkjframework.mybatispro.core.toolkit;

import com.cdkjframework.mybatispro.core.batch.BatchMethod;
import com.cdkjframework.mybatispro.core.batch.BatchSqlSession;
import com.cdkjframework.mybatispro.core.batch.MybatisBatch;
import com.cdkjframework.mybatispro.core.batch.ParameterConvert;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.toolkit
 * @ClassName: MybatisBatchUtils
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public class MybatisBatchUtils {


  /**
   * 执行批量操作
   *
   * @param sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param statement         执行的 mapper 方法 (示例: com.baomidou.mybatisplus.core.mapper.BaseMapper.insert )
   * @param <T>               泛型
   * @return 批处理结果
   */
  public static <T> List<BatchResult> execute(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, String statement) {
    return new MybatisBatch<>(sqlSessionFactory, dataList).execute(statement);
  }

  /**
   * 执行批量操作
   *
   * @param sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param statement         执行的 mapper 方法 (示例: com.baomidou.mybatisplus.core.mapper.BaseMapper.insert )
   * @param <T>               泛型
   * @param batchSize         插入批次数量
   * @return 批处理结果
   */
  public static <T> List<BatchResult> execute(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, String statement, int batchSize) {
    return new MybatisBatch<>(sqlSessionFactory, dataList, batchSize).execute(statement);
  }

  /**
   * 执行批量操作
   *
   * @param sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param statement         执行的 mapper 方法 (示例: com.baomidou.mybatisplus.core.mapper.BaseMapper.insert )
   * @param parameterConvert  参数转换器
   * @param <T>               泛型
   * @return 批处理结果
   */
  public static <T> List<BatchResult> execute(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, String statement, ParameterConvert<T> parameterConvert) {
    return new MybatisBatch<>(sqlSessionFactory, dataList).execute(statement, parameterConvert);
  }

  /**
   * 执行批量操作
   *
   * @param sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param statement         执行的 mapper 方法 (示例: com.baomidou.mybatisplus.core.mapper.BaseMapper.insert )
   * @param parameterConvert  参数转换器
   * @param <T>               泛型
   * @param batchSize         插入批次数量
   * @return 批处理结果
   */
  public static <T> List<BatchResult> execute(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, String statement, ParameterConvert<T> parameterConvert, int batchSize) {
    return new MybatisBatch<>(sqlSessionFactory, dataList, batchSize).execute(statement, parameterConvert);
  }

  /**
   * 执行批量操作
   *
   * @param sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param autoCommit        是否自动提交(这里生效的前提依赖于事务管理器 {@link org.apache.ibatis.transaction.Transaction})
   * @param statement         执行的 mapper 方法 (示例: com.baomidou.mybatisplus.core.mapper.BaseMapper.insert )
   * @param <T>               泛型
   * @return 批处理结果
   */
  public static <T> List<BatchResult> execute(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, boolean autoCommit, String statement) {
    return new MybatisBatch<>(sqlSessionFactory, dataList).execute(autoCommit, statement);
  }

  /**
   * 执行批量操作
   *
   * @param sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param autoCommit        是否自动提交(这里生效的前提依赖于事务管理器 {@link org.apache.ibatis.transaction.Transaction})
   * @param statement         执行的 mapper 方法 (示例: com.baomidou.mybatisplus.core.mapper.BaseMapper.insert )
   * @param <T>               泛型
   * @param batchSize         插入批次数量
   * @return 批处理结果
   */
  public static <T> List<BatchResult> execute(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, boolean autoCommit, String statement, int batchSize) {
    return new MybatisBatch<>(sqlSessionFactory, dataList, batchSize).execute(autoCommit, statement);
  }

  /**
   * 执行批量操作
   *
   * @param sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param autoCommit        是否自动提交(这里生效的前提依赖于事务管理器 {@link org.apache.ibatis.transaction.Transaction})
   * @param statement         执行的 mapper 方法 (示例: com.baomidou.mybatisplus.core.mapper.BaseMapper.insert )
   * @param parameterConvert  参数转换器
   * @param <T>               泛型
   * @return 批处理结果
   */
  public static <T> List<BatchResult> execute(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, boolean autoCommit, String statement, ParameterConvert<T> parameterConvert) {
    return new MybatisBatch<>(sqlSessionFactory, dataList).execute(autoCommit, statement, parameterConvert);
  }

  /**
   * 执行批量操作
   *
   * @param sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param autoCommit        是否自动提交(这里生效的前提依赖于事务管理器 {@link org.apache.ibatis.transaction.Transaction})
   * @param statement         执行的 mapper 方法 (示例: com.baomidou.mybatisplus.core.mapper.BaseMapper.insert )
   * @param parameterConvert  参数转换器
   * @param <T>               泛型
   * @param batchSize         插入批次数量
   * @return 批处理结果
   */
  public static <T> List<BatchResult> execute(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, boolean autoCommit, String statement, ParameterConvert<T> parameterConvert, int batchSize) {
    return new MybatisBatch<>(sqlSessionFactory, dataList, batchSize).execute(autoCommit, statement, parameterConvert);
  }

  /**
   * 执行批量操作
   *
   * @param sqlSessionFactory sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param batchMethod       批量操作方法
   * @param <T>               泛型
   * @return 批处理结果
   */
  public static <T> List<BatchResult> execute(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, BatchMethod<T> batchMethod) {
    return new MybatisBatch<>(sqlSessionFactory, dataList).execute(batchMethod);
  }

  /**
   * 执行批量操作
   *
   * @param sqlSessionFactory sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param batchMethod       批量操作方法
   * @param <T>               泛型
   * @param batchSize         插入批次数量
   * @return 批处理结果
   */
  public static <T> List<BatchResult> execute(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, BatchMethod<T> batchMethod, int batchSize) {
    return new MybatisBatch<>(sqlSessionFactory, dataList, batchSize).execute(batchMethod);
  }

  /**
   * 执行批量操作
   *
   * @param sqlSessionFactory sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param autoCommit        是否自动提交(这里生效的前提依赖于事务管理器 {@link org.apache.ibatis.transaction.Transaction})
   * @param batchMethod       批量操作方法
   * @param <T>               泛型
   * @return 批处理结果
   */
  public static <T> List<BatchResult> execute(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, boolean autoCommit, BatchMethod<T> batchMethod) {
    return new MybatisBatch<>(sqlSessionFactory, dataList).execute(autoCommit, batchMethod);
  }

  /**
   * 执行批量操作
   *
   * @param sqlSessionFactory sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param autoCommit        是否自动提交(这里生效的前提依赖于事务管理器 {@link org.apache.ibatis.transaction.Transaction})
   * @param batchMethod       批量操作方法
   * @param <T>               泛型
   * @param batchSize         插入批次数量
   * @return 批处理结果
   */
  public static <T> List<BatchResult> execute(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, boolean autoCommit, BatchMethod<T> batchMethod, int batchSize) {
    return new MybatisBatch<>(sqlSessionFactory, dataList, batchSize).execute(autoCommit, batchMethod);
  }

  /**
   * 批量保存或更新
   * 这里需要注意一下,如果在insertPredicate里判断调用其他sqlSession(类似mapper.xxx)时,要注意一级缓存问题或数据感知问题(因为当前会话数据还未提交)
   * 举个例子(事务开启状态下):
   * 如果当前批次里面执行两个主键相同的数据,当调用mapper.selectById时,如果数据库未有这条记录,在同个sqlSession下,由于一级缓存的问题,下次再查就还是null,导致插入主键冲突,
   * 但使用 {@link BatchSqlSession}时,由于每次select操作都会触发一次flushStatements,就会执行更新操作
   *
   * @param sqlSessionFactory sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param insertMethod      插入方法
   * @param insertPredicate   插入条件 (当条件满足时执行插入方法,否则执行更新方法)
   * @param updateMethod      更新方法
   * @param <T>               泛型
   * @return 批处理结果
   */
  public static <T> List<BatchResult> saveOrUpdate(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, BatchMethod<T> insertMethod, BiPredicate<BatchSqlSession, T> insertPredicate, BatchMethod<T> updateMethod) {
    return new MybatisBatch<>(sqlSessionFactory, dataList).saveOrUpdate(insertMethod, insertPredicate, updateMethod);
  }

  /**
   * 批量保存或更新
   * 这里需要注意一下,如果在insertPredicate里判断调用其他sqlSession(类似mapper.xxx)时,要注意一级缓存问题或数据感知问题(因为当前会话数据还未提交)
   * 举个例子(事务开启状态下):
   * 如果当前批次里面执行两个主键相同的数据,当调用mapper.selectById时,如果数据库未有这条记录,在同个sqlSession下,由于一级缓存的问题,下次再查就还是null,导致插入主键冲突,
   * 但使用 {@link BatchSqlSession}时,由于每次select操作都会触发一次flushStatements,就会执行更新操作
   *
   * @param sqlSessionFactory sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param insertMethod      插入方法
   * @param insertPredicate   插入条件 (当条件满足时执行插入方法,否则执行更新方法)
   * @param updateMethod      更新方法
   * @param <T>               泛型
   * @param batchSize         插入批次数量
   * @return 批处理结果
   */
  public static <T> List<BatchResult> saveOrUpdate(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, BatchMethod<T> insertMethod, BiPredicate<BatchSqlSession, T> insertPredicate, BatchMethod<T> updateMethod, int batchSize) {
    return new MybatisBatch<>(sqlSessionFactory, dataList, batchSize).saveOrUpdate(insertMethod, insertPredicate, updateMethod);
  }

  /**
   * 批量保存或更新
   * 这里需要注意一下,如果在insertPredicate里判断调用其他sqlSession(类似mapper.xxx)时,要注意一级缓存问题或数据感知问题(因为当前会话数据还未提交)
   * 举个例子(事务开启状态下):
   * 如果当前批次里面执行两个主键相同的数据,当调用mapper.selectById时,如果数据库未有这条记录,在同个sqlSession下,由于一级缓存的问题,下次再查就还是null,导致插入主键冲突,
   * 但使用 {@link BatchSqlSession}时,由于每次select操作都会触发一次flushStatements,就会执行更新操作
   *
   * @param sqlSessionFactory sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param autoCommit        是否自动提交(这里生效的前提依赖于事务管理器 {@link org.apache.ibatis.transaction.Transaction})
   * @param insertMethod      插入方法
   * @param insertPredicate   插入条件 (当条件满足时执行插入方法,否则执行更新方法)
   * @param updateMethod      更新方法
   * @param <T>               泛型
   * @return 批处理结果
   */
  public static <T> List<BatchResult> saveOrUpdate(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, boolean autoCommit, BatchMethod<T> insertMethod, BiPredicate<BatchSqlSession, T> insertPredicate, BatchMethod<T> updateMethod) {
    return new MybatisBatch<>(sqlSessionFactory, dataList).saveOrUpdate(autoCommit, insertMethod, insertPredicate, updateMethod);
  }

  /**
   * 批量保存或更新
   * 这里需要注意一下,如果在insertPredicate里判断调用其他sqlSession(类似mapper.xxx)时,要注意一级缓存问题或数据感知问题(因为当前会话数据还未提交)
   * 举个例子(事务开启状态下):
   * 如果当前批次里面执行两个主键相同的数据,当调用mapper.selectById时,如果数据库未有这条记录,在同个sqlSession下,由于一级缓存的问题,下次再查就还是null,导致插入主键冲突,
   * 但使用 {@link BatchSqlSession}时,由于每次select操作都会触发一次flushStatements,就会执行更新操作
   *
   * @param sqlSessionFactory sqlSessionFactory {@link SqlSessionFactory}
   * @param dataList          数据集列表
   * @param autoCommit        是否自动提交(这里生效的前提依赖于事务管理器 {@link org.apache.ibatis.transaction.Transaction})
   * @param insertMethod      插入方法
   * @param insertPredicate   插入条件 (当条件满足时执行插入方法,否则执行更新方法)
   * @param updateMethod      更新方法
   * @param <T>               泛型
   * @param batchSize         插入批次数量
   * @return 批处理结果
   */
  public static <T> List<BatchResult> saveOrUpdate(SqlSessionFactory sqlSessionFactory, Collection<T> dataList, boolean autoCommit, BatchMethod<T> insertMethod, BiPredicate<BatchSqlSession, T> insertPredicate, BatchMethod<T> updateMethod, int batchSize) {
    return new MybatisBatch<>(sqlSessionFactory, dataList, batchSize).saveOrUpdate(autoCommit, insertMethod, insertPredicate, updateMethod);
  }
}
