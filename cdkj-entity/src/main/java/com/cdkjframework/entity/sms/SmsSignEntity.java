package com.cdkjframework.entity.sms;

import com.cdkjframework.entity.BaseEntity;
import com.cdkjframework.enums.sms.AliSmsActionEnums;
import lombok.Data;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.sms
 * @ClassName: SmsSignEntity
 * @Description: 主体签名
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class SmsSignEntity extends BaseEntity {

    /**
     * 签名请求方式 {@link AliSmsActionEnums}
     */
    private AliSmsActionEnums signAction;

    /**
     * 签名名称
     */
    private String SignName;

    /**
     * 签名来源、当为空时则默认为 0 。取值：
     * <p>
     * 0：企事业单位的全称或简称。
     * 1：工信部备案网站的全称或简称。
     * 2：App应用的全称或简称。
     * 3：公众号或小程序的全称或简称。
     * 4：电商平台店铺名的全称或简称。
     * 5：商标名的全称或简称。
     */
    private Integer SignSource;

    /**
     * 短信签名申请说明。请在申请说明中详细描述您的业务使用场景，申请工信部备案网站的全称或简称请在此处填写域名，长度不超过200个字符。
     */
    private String Remark;

    /**
     * 场景类型
     */
    private Integer sceneType;

    /**
     * 签名名称
     */
    private List<SmsSignFileEntity> SignFileList;
}
