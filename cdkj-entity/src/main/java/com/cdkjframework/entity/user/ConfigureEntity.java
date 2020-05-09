package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.user
 * @ClassName: ConfigureEntity
 * @Description: 系统配置信息
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class ConfigureEntity extends BaseEntity {

    /**
     * 配置名称
     */
    private String configName;
    /**
     * 键
     */
    private String configKey;
    /**
     * 值
     */
    private String configValue;
    /**
     * 控制类型（input,select）
     */
    private String controlType;
    /**
     * 控件值
     */
    private String defaultValue;
    /**
     * 备注
     */
    private String remark;
}
