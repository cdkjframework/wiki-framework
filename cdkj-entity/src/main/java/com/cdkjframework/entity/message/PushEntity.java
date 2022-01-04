package com.cdkjframework.entity.message;

import com.cdkjframework.enums.push.PushPlatformEnums;
import com.cdkjframework.enums.push.PushRangeEnums;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.message
 * @ClassName: PushEntity
 * @Description: 推送实体
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class PushEntity {

    /**
     * 推送平台
     */
    private PushPlatformEnums platform;
    /**
     * 推送范围
     */
    private PushRangeEnums range;
    /**
     * 推送目标
     */
    private List<String> registerIdList;
    /**
     * 通知标题
     */
    private String title;
    /**
     * 通知内容
     */
    private String message;
    /**
     * 扩展字段
     */
    private String extras;
    /**
     * 应用程序ID
     */
    private String appId;
    /**
     * 极光appKey
     */
    private String appKey;
    /**
     * 极光masterSecret
     */
    private String secretKey;
    /**
     * 推送次数
     */
    private long count;
    /**
     * 推送状态
     * true 成功
     * false 失败
     */
    private boolean status;
    /**
     * 推送id
     */
    private String pushId;
    /**
     * 推送时间
     */
    private LocalDateTime pushTime;

    /**
     * 是否固定时间
     */
    private Boolean fixedTime;

    /**
     * 远程 LOGO
     */
    private String logoUrl;

    /**
     * 本地 LOGO
     */
    private String logo;

    /**
     * 请求服务
     */
    private String url;

    /**
     * 环境
     */
    private String environment;

    /**
     * 推送类型
     */
    private String pushType;
}
