package com.cdkjframework.enums.config;

import com.cdkjframework.enums.basics.BasicsEnum;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.enums.config
 * @ClassName: XxlJobEnum
 * @Description: XxlJob
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum XxlJobEnums implements BasicsEnum {

    adminAddresses {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.xxl.job.adminAddresses";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "adminAddresses";
        }
    },
    appName {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.xxl.job.appName";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "appName";
        }
    },
    ip {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.xxl.job.ip";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "ip";
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
            return "spring.xxl.job.port";
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
    accessToken {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.xxl.job.accessToken";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "accessToken";
        }
    },
    logPath {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.xxl.job.logPath";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "logPath";
        }
    },
    logRetentionDays {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "spring.xxl.job.logRetentionDays";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "logRetentionDays";
        }
    };
}