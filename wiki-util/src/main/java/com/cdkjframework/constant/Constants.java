package com.cdkjframework.constant;

import java.io.Serializable;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.datasource.mybatispro.consts
 * @ClassName: Constants
 * @Description: 常量接口
 * @Author: xiaLin
 * @Date: 2025/2/6 17:36
 * @Version: 1.0
 */
public interface Constants extends StringConstant, Serializable {

  /**
   * project name
   */
  String MYBATIS_PRO = "mybatis-pro";

  /**
   * MD5
   */
  String MD5 = "MD5";
  /**
   * AES
   */
  String AES = "AES";
  /**
   * AES 算法
   */
  String AES_CBC_CIPHER = "AES/CBC/PKCS5Padding";
  /**
   * as
   */
  String AS = " AS ";


  /**
   * 实体类
   */
  String ENTITY = "et";

  /**
   * 填充实体
   */
  String MP_FILL_ET = "mpFillEt";

  /**
   * 实体类 带后缀 ==> .
   */
  String ENTITY_DOT = ENTITY + DOT;
  /**
   * wrapper 类
   */
  String WRAPPER = "ew";
  /**
   * wrapper 类 带后缀 ==> .
   */
  String WRAPPER_DOT = WRAPPER + DOT;
  /**
   * wrapper 类的属性 entity
   */
  String WRAPPER_ENTITY = WRAPPER_DOT + "entity";
  /**
   * wrapper 类的属性 sqlSegment
   */
  String WRAPPER_SQLSEGMENT = WRAPPER_DOT + "sqlSegment";
  /**
   * wrapper 类的属性 emptyOfNormal
   */
  String WRAPPER_EMPTYOFNORMAL = WRAPPER_DOT + "emptyOfNormal";
  /**
   * wrapper 类的属性 nonEmptyOfNormal
   */
  String WRAPPER_NONEMPTYOFNORMAL = WRAPPER_DOT + "nonEmptyOfNormal";
  /**
   * wrapper 类的属性 nonEmptyOfEntity
   */
  String WRAPPER_NONEMPTYOFENTITY = WRAPPER_DOT + "nonEmptyOfEntity";
  /**
   * wrapper 类的属性 emptyOfWhere
   */
  String WRAPPER_EMPTYOFWHERE = WRAPPER_DOT + "emptyOfWhere";
  /**
   * wrapper 类的判断属性 nonEmptyOfWhere
   */
  String WRAPPER_NONEMPTYOFWHERE = WRAPPER_DOT + "nonEmptyOfWhere";
  /**
   * wrapper 类的属性 entity 带后缀 ==> .
   */
  String WRAPPER_ENTITY_DOT = WRAPPER_DOT + "entity" + DOT;
  /**
   * wrapper 类的属性 expression 下级属性 order
   */
  String WRAPPER_EXPRESSION_ORDER = WRAPPER_DOT + "useAnnotationOrderBy";
  /**
   * UpdateWrapper 类的属性 sqlSet
   */
  String U_WRAPPER_SQL_SET = WRAPPER_DOT + "sqlSet";
  /**
   * QueryWrapper 类的属性 sqlSelect
   */
  String Q_WRAPPER_SQL_SELECT = WRAPPER_DOT + "sqlSelect";
  /**
   * wrapper 类的属性 sqlComment
   */
  String Q_WRAPPER_SQL_COMMENT = WRAPPER_DOT + "sqlComment";
  /**
   * wrapper 类的属性 sqlFirst
   */
  String Q_WRAPPER_SQL_FIRST = WRAPPER_DOT + "sqlFirst";
  /**
   * columnMap
   */
  @Deprecated
  String COLUMN_MAP = "cm";
  /**
   * columnMap.isEmpty
   */
  String COLUMN_MAP_IS_EMPTY = COLUMN_MAP + DOT + "isEmpty";
  /**
   * collection
   *
   * @see #COLL
   * @deprecated 3.5.2 后面修改成collection
   */
  @Deprecated
  String COLLECTION = "coll";

  /**
   *
   */
  String COLL = "coll";
  /**
   * list
   */
  String LIST = "list";
  /**
   * where
   */
  String WHERE = "WHERE";
  /**
   * limit
   */
  String LIMIT = "LIMIT";

  /**
   *
   */
  String ARRAY = "array";
  /**
   * order by
   */
  String ORDER_BY = "ORDER BY";
  /**
   * asc
   */
  String ASC = "ASC";
  /**
   * desc
   */
  String DESC = "DESC";
  /**
   * 乐观锁字段
   */
  String MP_OPTLOCK_VERSION_ORIGINAL = "MP_OPTLOCK_VERSION_ORIGINAL";

  /**
   * wrapper 内部参数相关
   */
  String WRAPPER_PARAM = "MPGENVAL";
  /**
   * wrapper 缓存参数相关
   */
  String WRAPPER_PARAM_MIDDLE = ".paramNameValuePairs" + DOT;

  /**
   * 默认批次提交数量
   */
  int DEFAULT_BATCH_SIZE = 1000;
}
