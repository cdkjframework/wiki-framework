package com.cdkjframework.pay.impl;

import com.cdkjframework.constant.CacheConsts;
import com.cdkjframework.constant.PayTypeConsts;
import com.cdkjframework.core.business.mapper.PayConfigMapper;
import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.pay.PayConfigService;
import com.cdkjframework.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay.impl
 * @ClassName: PayConfigServiceImpl
 * @Description: 支付配置服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class PayConfigServiceImpl implements PayConfigService {

    /**
     * 支付配置服务
     */
    @Autowired
    private PayConfigMapper payConfigMapper;

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param entity 实体对象封装操作类（可以为 null）
     * @return List<PayConfigEntity>
     */
    @Override
    public List<PayConfigEntity> listFindByEntity(PayConfigEntity entity) {
        List<PayConfigEntity> configEntities;
        // 验证是否存在支付记录
        if (RedisUtils.syncExists(CacheConsts.PAY_CONFIG)) {
            configEntities = RedisUtils.syncGetList(CacheConsts.PAY_CONFIG, PayConfigEntity.class);
        } else {
            // 查询数据
            configEntities = payConfigMapper.listFindByEntity(entity);
            if (configEntities.size() > 0) {
                // 写入缓存
                RedisUtils.syncListSet(CacheConsts.PAY_CONFIG, configEntities);
            }
        }
        // 返回结果
        return configEntities;
    }

    /**
     * 获取一个配置
     *
     * @param entity 查询实体 payType 必须传
     * @return 返回实体
     */
    @Override
    public PayConfigEntity findEntity(PayConfigEntity entity) {
        String payType = entity.getPayType();
        entity.setPayType("");
        List<PayConfigEntity> configEntities = listFindByEntity(entity);

        // 获取数据
        Optional<PayConfigEntity> optional = configEntities.stream()
                .filter(f -> payType.equals(f.getPayType()))
                .findFirst();

        if (optional.isPresent()) {
            return optional.get();
        }
        // 返回结果
        return null;
    }
}
