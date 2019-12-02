package com.cdkjframework.enums.datasource;

import com.cdkjframework.enums.basics.BasicsEnum;

/**
 * @ProjectName: cdkjframework.core
 * @Package: com.cdkjframework.core.enums.SPRING_DATASOURCE
 * @ClassName: ApolloMongoEnum
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum ApolloMongoEnum implements BasicsEnum {
    uri {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.SPRING_DATASOURCE.mongodb.uri";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "uri";
        }
    },
    port {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.SPRING_DATASOURCE.mongodb.port";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "port";
        }
    },
    userName {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.SPRING_DATASOURCE.mongodb.userName";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "userName";
        }
    },
    password {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.SPRING_DATASOURCE.mongodb.password";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "password";
        }
    },
    dataSource {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.SPRING_DATASOURCE.mongodb.dataSource";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "dataSource";
        }
    },
    maxWaitTime {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.SPRING_DATASOURCE.mongodb.maxWaitTime";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "maxWaitTime";
        }
    },
    connectTimeout {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.SPRING_DATASOURCE.mongodb.connectTimeout";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "connectTimeout";
        }
    },
    minConnectionsPerHost {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.SPRING_DATASOURCE.mongodb.minConnectionsPerHost";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "minConnectionsPerHost";
        }
    },
    maxConnectionsPerHost {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.SPRING_DATASOURCE.mongodb.maxConnectionsPerHost";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "maxConnectionsPerHost";
        }
    };
}