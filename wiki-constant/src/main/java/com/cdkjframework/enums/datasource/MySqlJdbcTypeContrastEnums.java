package com.cdkjframework.enums.datasource;

import com.cdkjframework.enums.basics.BasicsEnum;

import java.util.Arrays;
import java.util.Optional;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.enums.datasource
 * @ClassName: MySQLMyBatisContrastEnum
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum MySqlJdbcTypeContrastEnums implements BasicsEnum {
  /**
   * VARCHAR
   */
  VARCHAR {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "varchar";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "VARCHAR";
    }
  },
  /**
   * KEYID
   */
  KEYID {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "varchar";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "VARCHAR";
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
      return "varchar";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "VARCHAR";
    }
  },
  /**
   * 数字
   */
  DECIMAL {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "decimal";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "DECIMAL";
    }
  },
  /**
   * 数字
   */
  NUMERIC {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "numeric";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "NUMERIC";
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
      return "char";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "CHAR";
    }
  },
  /**
   * 小数
   */
  TINYINT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "tinyint";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "TINYINT";
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
      return "smallint";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "SMALLINT";
    }
  },
  /**
   * 整数
   */
  INT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "int";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "INTEGER";
    }
  },
  /**
   * 整数
   */
  INT2 {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "int";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "INTEGER";
    }
  },
  /**
   * 整数
   */
  INT4 {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "int";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "INTEGER";
    }
  },
  /**
   * 整数
   */
  INT8 {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "int";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "INTEGER";
    }
  },
  /**
   * 整数
   */
  MEDIUMINT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "int";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "INTEGER";
    }
  },
  /**
   * 整数
   */
  FLOAT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "float";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "FLOAT";
    }
  },
  /**
   * 整数
   */
  BIGINT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "bigint";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "BIGINT";
    }
  },
  /**
   * 浮点数
   */
  DOUBLE {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "double";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "DOUBLE";
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
      return "bit";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "BOOLEAN";
    }
  },
  /**
   * DATE
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
    public String getCode() {
      return "DATE";
    }
  },
  /**
   * TIME
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
    public String getCode() {
      return "TIME";
    }
  },
  /**
   * DATETIME
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
    public String getCode() {
      return "TIMESTAMP";
    }
  },
  /**
   * TIMESTAMP
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
    public String getCode() {
      return "TIMESTAMP";
    }
  },
  /**
   * TEXT
   */
  TEXT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "text";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "VARCHAR";
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
      return "mediumtext";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "VARCHAR";
    }
  },
  /**
   * 长整形
   */
  LONGTEXT {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "longtext";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "LONGTEXT";
    }
  };

  /**
   * 根据数据库类型获取枚举
   *
   * @param dataType 数据库类型
   * @return 返回结果
   */
  public static MySqlJdbcTypeContrastEnums formByDataType(String dataType) {
    Optional<MySqlJdbcTypeContrastEnums> optional = Arrays.stream(MySqlJdbcTypeContrastEnums.values())
        .filter(e -> e.getValue().equals(dataType.toLowerCase()))
        .findFirst();
    // 返回结果
    return optional.orElse(MySqlJdbcTypeContrastEnums.VARCHAR);
  }
}
