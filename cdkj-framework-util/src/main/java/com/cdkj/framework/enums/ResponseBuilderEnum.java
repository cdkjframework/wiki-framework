package com.cdkj.framework.enums;

/**
 * @ProjectName: com.cdkj.framework.QRcode
 * @Package: com.cdkj.framework.core.enums
 * @ClassName: ResultTypeEnum
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum ResponseBuilderEnum {
    /**
     * 成功
     */
    Success {
        @Override
        public int getValue() {
            return 0;
        }

        @Override
        public String getName() {
            return "成功！";
        }
    },

    /**
     * 异常信息
     */
    Abnormal {
        @Override
        public int getValue() {
            return 2;
        }

        @Override
        public String getName() {
            return "出现异常！";
        }
    },
    /**
     * 错误信息
     */
    Error {
        @Override
        public int getValue() {
            return 999;
        }

        @Override
        public String getName() {
            return "失败！";
        }
    },
    /**
     * 登录失效
     */
    LogonFailure {
        @Override
        public int getValue() {
            return 900;
        }

        @Override
        public String getName() {
            return "登录失效，请重新登录！";
        }
    };

    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    public abstract int getValue();

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    public abstract String getName();
}
