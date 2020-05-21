package com.cdkjframework.enums;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.enums
 * @ClassName: SmsEnum
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum AliCloudSmsEnums {

    /**
     * 成功
     */
    OK {
        @Override
        public String getValue() {
            return "OK";
        }

        @Override
        public String getName() {
            return "请求成功";
        }
    },
    /**
     * RAM权限DENY
     */
    RAM_PERMISSION_DENY {
        @Override
        public String getValue() {
            return "isp.RAM_PERMISSION_DENY";
        }

        @Override
        public String getName() {
            return "RAM权限DENY";
        }
    },
    /**
     * 业务停机
     */
    OUT_OF_SERVICE {
        @Override
        public String getValue() {
            return "isv.OUT_OF_SERVICE";
        }

        @Override
        public String getName() {
            return "业务停机";
        }
    },
    /**
     * 未开通云通信产品的阿里云客户
     */
    PRODUCT_UN_SUBSCRIPT {
        @Override
        public String getValue() {
            return "isv.PRODUCT_UN_SUBSCRIPT";
        }

        @Override
        public String getName() {
            return "未开通云通信产品的阿里云客户";
        }
    },
    /**
     * 产品未开通
     */
    PRODUCT_UNSUBSCRIBE {
        @Override
        public String getValue() {
            return "isv.PRODUCT_UNSUBSCRIBE";
        }

        @Override
        public String getName() {
            return "产品未开通";
        }
    },
    /**
     * 账户不存在
     */
    User_NOT_EXISTS {
        @Override
        public String getValue() {
            return "isv.User_NOT_EXISTS";
        }

        @Override
        public String getName() {
            return "账户不存在";
        }
    },
    /**
     * 账户不存在
     */
    User_ABNORMAL {
        @Override
        public String getValue() {
            return "isv.User_ABNORMAL";
        }

        @Override
        public String getName() {
            return "账户异常";
        }
    },
    /**
     * 短信模板不合法
     */
    SMS_TEMPLATE_ILLEGAL {
        @Override
        public String getValue() {
            return "isv.SMS_TEMPLATE_ILLEGAL";
        }

        @Override
        public String getName() {
            return "短信模板不合法";
        }
    },
    /**
     * 短信签名不合法
     */
    SMS_SIGNATURE_ILLEGAL {
        @Override
        public String getValue() {
            return "isv.SMS_SIGNATURE_ILLEGAL";
        }

        @Override
        public String getName() {
            return "短信签名不合法";
        }
    },
    /**
     * 参数异常
     */
    INVALID_PARAMETERS {
        @Override
        public String getValue() {
            return "isv.INVALID_PARAMETERS";
        }

        @Override
        public String getName() {
            return "短信签名不合法";
        }
    },
    /**
     * 系统错误
     */
    SYSTEM_ERROR {
        @Override
        public String getValue() {
            return "isv.SYSTEM_ERROR";
        }

        @Override
        public String getName() {
            return "系统错误";
        }
    },
    /**
     * 非法手机号
     */
    MOBILE_NUMBER_ILLEGAL {
        @Override
        public String getValue() {
            return "isv.MOBILE_NUMBER_ILLEGAL";
        }

        @Override
        public String getName() {
            return "非法手机号";
        }
    },
    /**
     * 手机号码数量超过限制
     */
    MOBILE_COUNT_OVER_LIMIT {
        @Override
        public String getValue() {
            return "isv.MOBILE_COUNT_OVER_LIMIT";
        }

        @Override
        public String getName() {
            return "手机号码数量超过限制";
        }
    },
    /**
     * 模板缺少变量
     */
    TEMPLATE_MISSING_PARAMETERS {
        @Override
        public String getValue() {
            return "isv.TEMPLATE_MISSING_PARAMETERS";
        }

        @Override
        public String getName() {
            return "模板缺少变量";
        }
    },
    /**
     * 模板缺少变量
     */
    BUSINESS_LIMIT_CONTROL {
        @Override
        public String getValue() {
            return "isv.BUSINESS_LIMIT_CONTROL";
        }

        @Override
        public String getName() {
            return "业务限流";
        }
    },
    /**
     * JSON参数不合法，只接受字符串值
     */
    INVALID_JSON_PARAM {
        @Override
        public String getValue() {
            return "isv.INVALID_JSON_PARAM";
        }

        @Override
        public String getName() {
            return "JSON参数不合法，只接受字符串值";
        }
    },
    /**
     * 黑名单管控
     */
    BLACK_KEY_CONTROL_LIMIT {
        @Override
        public String getValue() {
            return "isv.BLACK_KEY_CONTROL_LIMIT";
        }

        @Override
        public String getName() {
            return "黑名单管控";
        }
    },
    /**
     * 参数超出长度限制
     */
    PARAM_LENGTH_LIMIT {
        @Override
        public String getValue() {
            return "isv.PARAM_LENGTH_LIMIT";
        }

        @Override
        public String getName() {
            return "参数超出长度限制";
        }
    },
    /**
     * 不支持URL
     */
    PARAM_NOT_SUPPORT_URL {
        @Override
        public String getValue() {
            return "isv.PARAM_NOT_SUPPORT_URL";
        }

        @Override
        public String getName() {
            return "不支持URL";
        }
    },
    /**
     * 账户余额不足
     */
    AMOUNT_NOT_ENOUGH {
        @Override
        public String getValue() {
            return "isv.AMOUNT_NOT_ENOUGH";
        }

        @Override
        public String getName() {
            return "账户余额不足";
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
