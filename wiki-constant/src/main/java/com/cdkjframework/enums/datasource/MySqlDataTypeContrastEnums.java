package com.cdkjframework.enums.datasource;

import com.cdkjframework.enums.InterfaceEnum;

import java.util.Arrays;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.enums.datasource
 * @ClassName: MySqlDataTypeContrastEnum
 * @Description: mysql 数据类型对照
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum
MySqlDataTypeContrastEnums implements InterfaceEnum {
  /**
   * varchar
   */
  VARCHAR {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "String";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.String";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  },
  /**
   * 长文本
   */
  LONGTEXT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "String";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.String";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  },
  /**
   * 键值
   */
  KEYID {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "String";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.String";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  },
  /**
   * 字符串
   */
  BPCHAR {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "String";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.String";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  },
  /**
   * 字符
   */
  CHAR {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "String";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.String";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  },
  /**
   * blob
   */
  BLOB {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "byte[]";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.byte";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  },
  /**
   * 字符串
   */
  TEXT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "String";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.String";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  },
  /**
   * MEDIUMTEXT
   */
  MEDIUMTEXT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "String";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.String";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  },
  /**
   * 整型
   */
  INTEGER {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Long";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Long";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * 短整型
   */
  TINYINT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Integer";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Integer";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * 获取枚举值
   */
  INT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Integer";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Integer";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * int
   */
  INT2 {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Integer";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Integer";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * int4
   */
  INT4 {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Integer";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Integer";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * int8
   */
  INT8 {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Integer";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Integer";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * 小数
   */
  SMALLINT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Integer";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Integer";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * 中间数字
   */
  MEDIUMINT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Integer";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Integer";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * BIT
   */
  BIT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Boolean";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Boolean";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "boolean";
    }
  },
  /**
   * java.lang.Long
   */
  BIGINT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BigInteger";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.math.BigInteger";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * 浮点型
   */
  FLOAT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Float";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Float";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * DOUBLE
   */
  DOUBLE {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Double";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Double";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * DECIMAL
   */
  DECIMAL {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BigDecimal";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.math.BigDecimal";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * 数值
   */
  NUMERIC {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BigDecimal";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.math.BigDecimal";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * 布尔类型
   */
  BOOLEAN {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Integer";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Integer";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * id
   */
  ID {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Long";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.lang.Long";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "number";
    }
  },
  /**
   * 日期类型
   */
  DATE {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "LocalDate";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.time.LocalDate";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  },
  /**
   * 时间
   */
  TIME {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "LocalTime";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.time.LocalTime";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  },
  /**
   * 时间
   */
  DATETIME {
    /**f
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "LocalDateTime";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.time.LocalDateTime";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  },
  /**
   * 时间戳
   */
  TIMESTAMP {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Instant";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.time.Instant";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  },
  /**
   * 年
   */
  YEAR {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "Date";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getText() {
      return "java.sql.Date";
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
      return "string";
    }
  };

  /**
   * 根据数据库类型获取枚举
   *
   * @param dataType 数据库类型
   * @return 返回结果
   */
  public static MySqlDataTypeContrastEnums formByDataType(String dataType) {
    MySqlDataTypeContrastEnums enums;
    try {
      enums = MySqlDataTypeContrastEnums.valueOf(dataType.toUpperCase());
    } catch (Exception e) {
      enums = MySqlDataTypeContrastEnums.VARCHAR;
    }
    return enums;
  }
}
