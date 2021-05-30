package com.cdkjframework.entity.sms;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.sms
 * @ClassName: SendSmsEntity
 * @Description: 发送短信实体
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class SendSmsEntity {

    /**
     * 签名名称
     */
    private String signName;

    /**
     * 模板类型
     */
    private List<String> phoneNumbers;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 内容
     */
    private Map<String, String> content;
}
