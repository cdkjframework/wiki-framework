package com.cdkjframework.enums.datasource;

import com.cdkjframework.enums.basics.BasicsEnum;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.enums.datasource
 * @ClassName: MySQLMyBatisContrastEnum
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum MySqlJdbcTypeContrastEnums implements BasicsEnum {
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
        public String getCode() {
            return "java.lang.Integer";
        }
    },
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
}
