package com.cdkjframework.entity.sms;

import com.cdkjframework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = false)
public class SendSmsEntity extends BaseSmsEntity {

    /**
     * 签名名称
     */
    private String signName;

    /**
     * 手机号列表
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
    /**
     * 批量内容
     */
    private List<Map<String, String>> contentList;
}
