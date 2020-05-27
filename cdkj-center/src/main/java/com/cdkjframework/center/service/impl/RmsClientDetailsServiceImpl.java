package com.cdkjframework.center.service.impl;

import com.cdkjframework.center.service.RmsClientDetailsService;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.business.mapper.RmsClientDetailsMapper;
import com.cdkjframework.core.member.CurrentUser;
import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.entity.user.security.RmsClientDetailsEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.CopyUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @ProjectName: com.lesmarthome.wms
 * @Package: com.lesmarthome.wms
 * @ClassName: RmsClientDetails
 * @Description: 
 * @Author: DESKTOP-U0VVSVK
 * @Version: 1.0
 */

@Service
public class RmsClientDetailsServiceImpl implements RmsClientDetailsService {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(RmsClientDetailsServiceImpl.class);

    /**
     *  mapper
     */
    @Autowired
    private RmsClientDetailsMapper rmsClientDetailsMapper;

    /**
     * 查找一条客户详情
     *
     * @param detailsEntity 查询条件
     * @return 返回结果
     */
    @Override
    public RmsClientDetailsEntity findClientDetailsByEntity(RmsClientDetailsEntity detailsEntity) {
        return rmsClientDetailsMapper.findEntity(detailsEntity);
    }
}

