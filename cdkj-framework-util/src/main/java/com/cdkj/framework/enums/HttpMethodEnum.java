package com.cdkj.framework.enums;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkj.framework.core.enums
 * @ClassName: HttpMethodEnum
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum HttpMethodEnum {
    GET {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "GET";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getName() {
            return "GET请求";
        }
    },
    POST {
        /**
         * 获取枚举值
         *
         * @return 返回结果
         */
        @Override
        public String getValue() {
            return "POST";
        }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getName() {
            return "POST请求";
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
