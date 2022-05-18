package com.cdkjframework.entity.message.tencent;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.message.tencent
 * @ClassName: XingGeEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class XinGeEntity {

    /**
     * result : {}
     * environment :
     * push_id : 1328245138690125824
     * err_msg : NO_ERROR
     * err_msg_zh :
     * ret_code : 0
     * seq : 0
     */

    private String result;
    private String environment;

    @JSONField(name = "push_id")
    private String pushId;

    @JSONField(name = "err_msg")
    private String errMessage;

    @JSONField(name = "err_msg_zh")
    private String errMessageZh;
    @JSONField(name = "ret_code")
    private int retCode;
    private int seq;
}
