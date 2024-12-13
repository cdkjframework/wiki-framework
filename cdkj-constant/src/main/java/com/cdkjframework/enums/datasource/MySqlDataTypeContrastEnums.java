package com.cdkjframework.enums.datasource;

import com.cdkjframework.enums.InterfaceEnum;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.enums.datasource
 * @ClassName: MySqlDataTypeContrastEnum
 * @Description: mysql 数据类型对照
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum MySqlDataTypeContrastEnums implements InterfaceEnum {
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
}
