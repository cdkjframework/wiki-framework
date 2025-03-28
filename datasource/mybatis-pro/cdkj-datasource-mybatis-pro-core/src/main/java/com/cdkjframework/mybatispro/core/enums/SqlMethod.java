package com.cdkjframework.mybatispro.core.enums;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.enums
 * @ClassName: SqlMethod
 * @Description: MybatisPro 支持 sql 方法
 * @Author: xiaLin
 * @Version: 1.0
 */
public enum SqlMethod {

  /**
   * 插入
   */
  INSERT_ONE("insert", "插入一条数据（选择字段插入）", "<script>\nINSERT INTO %s %s VALUES %s\n</script>"),
  UPSERT_ONE("upsert", "Phoenix插入一条数据（选择字段插入）", "<script>\nUPSERT INTO %s %s VALUES %s\n</script>"),

  /**
   * 删除
   */
  DELETE_BY_ID("deleteById", "根据ID 删除一条数据", "DELETE FROM %s WHERE %s=#{%s}"),
  @Deprecated
  DELETE_BY_MAP("deleteByMap", "根据columnMap 条件删除记录", "<script>\nDELETE FROM %s %s\n</script>"),
  DELETE("delete", "根据 entity 条件删除记录", "<script>\nDELETE FROM %s %s %s\n</script>"),
  /**
   * @deprecated 3.5.7 {@link #DELETE_BY_IDS}
   */
  @Deprecated
  DELETE_BATCH_BY_IDS("deleteBatchIds", "根据ID集合，批量删除数据", "<script>\nDELETE FROM %s WHERE %s IN (%s)\n</script>"),
  /**
   * 
   */
  DELETE_BY_IDS("deleteByIds", "根据ID集合，批量删除数据", "<script>\nDELETE FROM %s WHERE %s IN (%s)\n</script>"),

  /**
   * 逻辑删除
   */
  LOGIC_DELETE_BY_ID("deleteById", "根据ID 逻辑删除一条数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),
  LOGIC_DELETE_BY_MAP("deleteByMap", "根据columnMap 条件逻辑删除记录", "<script>\nUPDATE %s %s %s\n</script>"),
  LOGIC_DELETE("delete", "根据 entity 条件逻辑删除记录", "<script>\nUPDATE %s %s %s %s\n</script>"),
  /**
   * @deprecated 3.5.7 {@link #LOGIC_DELETE_BY_IDS}
   */
  @Deprecated
  LOGIC_DELETE_BATCH_BY_IDS("deleteBatchIds", "根据ID集合，批量逻辑删除数据", "<script>\nUPDATE %s %s WHERE %s IN (%s) %s\n</script>"),
  /**
   * 
   */
  LOGIC_DELETE_BY_IDS("deleteByIds", "根据ID集合，批量逻辑删除数据", "<script>\nUPDATE %s %s WHERE %s IN (%s) %s\n</script>"),

  /**
   * 修改
   */
  UPDATE_BY_ID("updateById", "根据ID 选择修改数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),
  UPDATE("update", "根据 whereEntity 条件，更新记录", "<script>\nUPDATE %s %s %s %s\n</script>"),

  /**
   * 逻辑删除 -> 修改
   */
  LOGIC_UPDATE_BY_ID("updateById", "根据ID 修改数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),

  /**
   * 查询
   */
  SELECT_BY_ID("selectById", "根据ID 查询一条数据", "SELECT %s FROM %s WHERE %s=#{%s} %s"),
  @Deprecated
  SELECT_BY_MAP("selectByMap", "根据columnMap 查询一条数据", "<script>SELECT %s FROM %s %s\n</script>"),
  /**
   * @deprecated 3.5.8 {@link #SELECT_BY_IDS}
   */
  @Deprecated
  SELECT_BATCH_BY_IDS("selectBatchIds", "根据ID集合，批量查询数据", "<script>SELECT %s FROM %s WHERE %s IN (%s) %s </script>"),
  /**
   * @since 3.5.8
   */
  SELECT_BY_IDS("selectByIds", "根据ID集合，批量查询数据", "<script>SELECT %s FROM %s WHERE %s IN (%s) %s </script>"),
  @Deprecated
  SELECT_ONE("selectOne", "查询满足条件一条数据", "<script>%s SELECT %s FROM %s %s %s\n</script>"),
  SELECT_COUNT("selectCount", "查询满足条件总记录数", "<script>%s SELECT COUNT(%s) AS total FROM %s %s %s\n</script>"),
  SELECT_LIST("selectList", "查询满足条件所有数据", "<script>%s SELECT %s FROM %s %s %s %s\n</script>"),
  @Deprecated
  SELECT_PAGE("selectPage", "查询满足条件所有数据（并翻页）", "<script>%s SELECT %s FROM %s %s %s %s\n</script>"),
  SELECT_MAPS("selectMaps", "查询满足条件所有数据", "<script>%s SELECT %s FROM %s %s %s %s\n</script>"),
  @Deprecated
  SELECT_MAPS_PAGE("selectMapsPage", "查询满足条件所有数据（并翻页）", "<script>\n%s SELECT %s FROM %s %s %s %s\n</script>"),
  SELECT_OBJS("selectObjs", "查询满足条件所有数据", "<script>%s SELECT %s FROM %s %s %s %s\n</script>");

  private final String method;
  private final String description;
  private final String sql;

  SqlMethod(String method, String desc, String sql) {
    this.method = method;
    this.description = desc;
    this.sql = sql;
  }

  public String getMethod() {
    return method;
  }

  public String getDescription() {
    return description;
  }

  public String getSql() {
    return sql;
  }
}
