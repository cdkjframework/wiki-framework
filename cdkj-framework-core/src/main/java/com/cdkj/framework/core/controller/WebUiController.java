package com.cdkj.framework.core.controller;

import com.cdkj.framework.config.CustomConfig;
import com.cdkj.framework.consts.CacheConstant;
import com.cdkj.framework.core.builder.ResponseBuilder;
import com.cdkj.framework.entity.PageEntity;
import com.cdkj.framework.entity.log.LogRecordEntity;
import com.cdkj.framework.entity.user.UserEntity;
import com.cdkj.framework.enums.ResponseBuilderEnum;
import com.cdkj.framework.enums.basics.BasicsEnum;
import com.cdkj.framework.util.cache.JedisPoolUtil;
import com.cdkj.framework.util.jwt.JwtUtil;
import com.cdkj.framework.util.tool.JsonUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ProjectName: com.cdkj.framework.QRcode
 * @Package: com.cdkj.framework.core.controller
 * @ClassName: WebUIController
 * @Description: 基控制器
 * @Author: xiaLin
 * @Version: 1.0
 */

public class WebUiController extends AbstractController {

    /**
     * 自定义配置信息
     */
    @Autowired
    private CustomConfig customConfig;

    /**
     * 登出登录
     *
     * @param id 主键
     * @return 返回结果
     */
    @Override
    public ResponseBuilder quit(String id) {
        ResponseBuilder builder = new ResponseBuilder();
        try {
            final String key = CacheConstant.userLogin + id;
            JedisPoolUtil.exists(key);
        } catch (Exception ex) {
            ex.printStackTrace();

            builder.setCode(ResponseBuilderEnum.Error.getValue());
            builder.setMessage(ResponseBuilderEnum.Error.getName());
        }

        //返回结果
        return builder;
    }

    /**
     * 获取抽象信息
     *
     * @return 返回用户抽象实体
     */
    @Override
    public UserEntity getCurrentUser() {
        UserEntity entity = getCurrentUser(UserEntity.class);
        if (entity == null) {
            entity = new UserEntity();
        }

        //返回结果
        return entity;
    }

    /**
     * 获取抽象信息
     *
     * @param clazz 实体
     * @return 返回用户抽象实体
     */
    @Override
    public <T> T getCurrentUser(Class<T> clazz) {
        T userEntity = null;
        try {
            String key = getRequestHeader("token");
            Claims claims = JwtUtil.parseJWT(key, customConfig.getJwtKey());
            String token = claims.get("token").toString();
            return getCurrentUser(token, clazz);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //返回结果
        return userEntity;
    }

    /**
     * 获取抽象信息
     *
     * @param id    主键
     * @param clazz 实体
     * @return 返回用户抽象实体
     */
    @Override
    public <T> T getCurrentUser(String id, Class<T> clazz) {
        T userEntity = null;
        try {
            userEntity = JedisPoolUtil.getEntity(CacheConstant.userLogin + id, clazz);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //返回结果
        return userEntity;
    }

    /**
     * 定入日志
     *
     * @param builder      结果
     * @param logId        日志ID
     * @param businessType 业务类型
     * @param modular      模块
     */
    @Override
    public void writeLog(ResponseBuilder builder, String logId, BasicsEnum businessType, BasicsEnum modular) {
        setLog(builder, businessType, modular, logId, builder.getCode());
    }

    /**
     * 定入日志
     *
     * @param pageEntity   结果
     * @param logId        日志ID
     * @param businessType 业务类型
     * @param modular      模块
     */
    @Override
    public void writeLog(PageEntity pageEntity, String logId, BasicsEnum businessType, BasicsEnum modular) {
        setLog(pageEntity, businessType, modular, logId, pageEntity.getCode());
    }

    /**
     * 设置日志记录信息
     *
     * @param result       返回结果
     * @param businessType 业务类型
     * @param modular      模块
     * @param logId        日志ID
     * @param code         编码
     */
    private void setLog(Object result, BasicsEnum businessType, BasicsEnum modular, String logId, int code) {
        try {
            LogRecordEntity logRecordEntity = new LogRecordEntity();
            logRecordEntity.setId(logId);
            logRecordEntity.setResult(JsonUtil.objectToJsonString(result));
            logRecordEntity.setBusinessType(businessType.getValue());
            logRecordEntity.setModular(modular.getValue());
            logRecordEntity.setExecutionState(code == 0 ? 1 : 0);

            writeLog(logRecordEntity);
        } catch (Exception ex) {
        }
    }
}