package com.cdkjframework.enums.datasource;


import com.cdkjframework.enums.basics.BasicsEnum;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.enums.datasource
 * @ClassName: ApolloRedisEnum
 * @Description: redis
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum ApolloRedisEnum implements BasicsEnum {

    database {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.data.redis.database";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "database";
        }
    },
    host {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.data.redis.host";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "host";
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
            return "spring.data.redis.port";
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
    password {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.data.redis.password";
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
    maxActive {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.data.redis.maxActive";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "maxActive";
        }
    },
    timeOut {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.data.redis.timeOut";
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
    maxIdle {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.data.redis.maxIdle";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "maxIdle";
        }
    };
}
