package com.cdkjframework.security.service;

import com.cdkjframework.entity.user.BmsConfigureEntity;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.service
 * @ClassName: ConfigureService
 * @Description: 配置服务
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface ConfigureService {

    /**
     * 查询配置信息
     *
     * @param configure 配置查询
     * @return 返回配置信息
     */
    List<BmsConfigureEntity> listConfigure(BmsConfigureEntity configure);
}
