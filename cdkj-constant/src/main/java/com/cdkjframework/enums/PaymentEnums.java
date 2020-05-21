package com.cdkjframework.enums;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.enums
 * @ClassName: PaymentEnum
 * @Description: 支付枚举
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum PaymentEnums {

    AliPay {
        @Override
        public String getValue() {
            return "AliPay";
        }

        @Override
        public String getName() {
            return "支付宝";
        }
    },
    WebChat {
        @Override
        public String getValue() {
            return "WebChat";
        }

        @Override
        public String getName() {
            return "微信";
        }
    },
    Ccb {
        @Override
        public String getValue() {
            return "Ccb";
        }

        @Override
        public String getName() {
            return "建设银行";
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
