package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Entity
@Table(name = "bms_configure", catalog = "")
public class ConfigureEntity extends BaseEntity {

    /**
     * 配置名称
     */
    @Column(name = "config_name")
    private String configName;
    /**
     * 键
     */
    @Column(name = "config_key")
    private String configKey;
    /**
     * 值
     */
    @Column(name = "config_value")
    private String configValue;
    /**
     * 控制类型（input,select）
     */
    @Column(name = "control_type")
    private String controlType;
    /**
     * 控件值
     */
    @Column(name = "default_value")
    private String defaultValue;

    /**
     * 参数（JSON对象）
     */
    @Column(name = "argument")
    private String argument;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
}
