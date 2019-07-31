package com.cdkj.framework.enums;

/**
 * @ProjectName: ec-icm
 * @Package: com.cdkj.framework.core.enums
 * @ClassName: QueueMessageTypeEnum
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum QueueMessageTypeEnum {

    /**
     * 消息回执
     */
    RECEIPT {
        @Override
        public String getValue() {
            return "Receipt";
        }

        @Override
        public String getName() {
            return "消息回执";
        }
    },
    /**
     * 消息
     */
    MESSAGE {
        @Override
        public String getValue() {
            return "Message";
        }

        @Override
        public String getName() {
            return "消息";
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
