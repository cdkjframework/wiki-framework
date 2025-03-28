package com.cdkjframework.mybatispro.core.assist;

import com.cdkjframework.mybatispro.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.assist
 * @ClassName: ISqlRunner
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface ISqlRunner {

  String INSERT = "com.cdkjframework.mybatispro.core.mapper.SqlRunner.Insert";
  String DELETE = "com.cdkjframework.mybatispro.core.mapper.SqlRunner.Delete";
  String UPDATE = "com.cdkjframework.mybatispro.core.mapper.SqlRunner.Update";
  String SELECT_LIST = "com.cdkjframework.mybatispro.core.mapper.SqlRunner.SelectList";
  String SELECT_OBJS = "com.cdkjframework.mybatispro.core.mapper.SqlRunner.SelectObjs";
  String COUNT = "com.cdkjframework.mybatispro.core.mapper.SqlRunner.Count";
  String SQL_SCRIPT = "${sql}";
  String SQL = "sql";
  String PAGE = "page";

  boolean insert(String sql, Object... args);

  boolean delete(String sql, Object... args);

  boolean update(String sql, Object... args);

  List<Map<String, Object>> selectList(String sql, Object... args);

  List<Object> selectObjs(String sql, Object... args);

  Object selectObj(String sql, Object... args);

  long selectCount(String sql, Object... args);

  Map<String, Object> selectOne(String sql, Object... args);

  <E extends IPage<Map<String, Object>>> E selectPage(E page, String sql, Object... args);
}
