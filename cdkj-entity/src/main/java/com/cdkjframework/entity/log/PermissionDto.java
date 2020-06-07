package com.cdkjframework.entity.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.log
 * @ClassName: PermissionDto
 * @Description: 权限实体
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class PermissionDto {

    /**
     * 机构编码
     */
    private String organization;

    /**
     * 上级机构权限是否控制
     */
    private boolean superior = true;

    /**
     * 当前所在机构权限
     */
    private boolean current = false;
}
