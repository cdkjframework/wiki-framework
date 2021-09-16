package com.cdkjframework.enums.push;


/**
 * @ProjectName: bms-manger
 * @Package: com.lesmarthome.bms.client
 * @ClassName: AudienceEnum
 * @Author: lixin
 * @Version: 1.0
 * @Description: 推送范围
 **/
public enum PushRangeEnums {

    AUDIENCE_ALL(1, "all", "广播推送（全部设备）"),
    AUDIENCE_REGISTRATION_ID(2, "registration_id", "注册ID推送，多个注册ID之间是 OR 关系，即取并集。一次推送最多 1000 个。"),
    AUDIENCE_TAG_AND(3, "tag_and", "标签推送，多个标签之间是 OR 的关系，即取并集。一次推送最多 20 个。"),
    AUDIENCE_TAG_NOT(4, "tag_not", "标签推送，多个标签之间是 AND 关系，即取交集。一次推送最多 20 个。"),
    AUDIENCE_ALIAS(5, "alias", "别名推送，多个别名之间是 OR 关系，即取并集。一次推送最多 1000 个。");

    private Integer key;
    private String value;
    private String notes;

    PushRangeEnums(Integer key, String value, String notes) {
        this.key = key;
        this.value = value;
        this.notes = notes;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getNotes() {
        return notes;
    }
}
