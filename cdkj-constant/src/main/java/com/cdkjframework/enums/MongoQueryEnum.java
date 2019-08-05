package com.cdkjframework.enums;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.enums
 * @ClassName: MongoQueryEnum
 * @Description: Mongo 查询枚举
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum MongoQueryEnum {

    /**
     * 或者
     */
    OR {
        @Override
        public String getValue() {
            return "Or";
        }

        @Override
        public String getName() {
            return "或者";
        }
    },
    /**
     * 且
     */
    AND {
        @Override
        public String getValue() {
            return "And";
        }

        @Override
        public String getName() {
            return "且";
        }
    },
    /**
     * 包含
     */
    IN {
        @Override
        public String getValue() {
            return "In";
        }

        @Override
        public String getName() {
            return "包含";
        }
    },
    /**
     * 匹配
     */
    LIKE {
        @Override
        public String getValue() {
            return "Like";
        }

        @Override
        public String getName() {
            return "匹配";
        }
    },
    /**
     * 大于等于
     */
    GTE {
        @Override
        public String getValue() {
            return "Gte";
        }

        @Override
        public String getName() {
            return "大于等于";
        }
    },
    /**
     * 大于
     */
    GT {
        @Override
        public String getValue() {
            return "Gt";
        }

        @Override
        public String getName() {
            return "大于";
        }
    },
    /**
     * 小于等于
     */
    LTE {
        @Override
        public String getValue() {
            return "Lte";
        }

        @Override
        public String getName() {
            return "小于等于";
        }
    },
    /**
     * 小于
     */
    LT {
        @Override
        public String getValue() {
            return "Lt";
        }

        @Override
        public String getName() {
            return "小于";
        }
    };

    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    public abstract String getValue();

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    public abstract String getName();
}
