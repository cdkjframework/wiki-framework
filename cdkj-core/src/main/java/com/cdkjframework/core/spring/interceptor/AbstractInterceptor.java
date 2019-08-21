package com.cdkjframework.core.spring.interceptor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.config.InterceptorConfig;
import com.cdkjframework.core.business.service.MongoService;
import com.cdkjframework.core.business.service.OrderNumberService;
import com.cdkjframework.core.spring.filter.HttpServletContentRequestWrapper;
import com.cdkjframework.entity.log.LogRecordEntity;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.spring.interceptor
 * @ClassName: AbstractInterceptor
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class AbstractInterceptor implements IInterceptor {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(AbstractInterceptor.class);

    /**
     * 不过虑类型
     */
    protected List<String> noFilterMethod = new ArrayList<>();

    /**
     * 读取过虑配置信息
     */
    @Autowired
    protected InterceptorConfig interceptorConfig;

    /**
     * 自定义配置信息
     */
    @Autowired
    protected CustomConfig customConfig;

    /**
     * 单据生成
     */
    @Autowired
    protected OrderNumberService orderNumberServiceImpl;

    /**
     * mongo 数据库操作
     */
    @Autowired
    protected MongoService mongoServiceImpl;

    /**
     * 左括号
     */
    private final String leftBrackets = "[";

    /**
     * 右括号
     */
    private final String rightBrackets = "]";

    /**
     * 构造函数
     */
    public AbstractInterceptor() {
        noFilterMethod.add("POST");
        noFilterMethod.add("GET");
    }

    /**
     * 验证用户是否登录
     *
     * @param token              密钥
     * @param verified           验证
     * @param httpServletRequest 请求信息
     * @throws GlobalException 异常信息
     */
    @Override
    public abstract void authenticateUserLogin(String token, boolean verified, HttpServletRequest httpServletRequest) throws GlobalException;

    /**
     * 修改参数
     *
     * @param httpServletRequest 参数
     * @param userEntity         用户实体
     */
    @Override
    public void modifyParameters(HttpServletRequest httpServletRequest, UserEntity userEntity) {

        try {
            String contentType = httpServletRequest.getHeader("Content-Type");
            if (StringUtils.isNullAndSpaceOrEmpty(contentType)) {
                contentType = "";
            }
            //如果是错误地址则不进行修改
            if (httpServletRequest.getServletPath().contains("/error") ||
                    !noFilterMethod.contains(httpServletRequest.getMethod()) ||
                    contentType.contains("multipart/form-data")) {
                return;
            }

            HttpServletContentRequestWrapper requestWrapper = (HttpServletContentRequestWrapper) httpServletRequest;
            String body = IOUtils.toString(requestWrapper.getBody(), httpServletRequest.getCharacterEncoding());

            //日志记录
            logRecord(httpServletRequest, userEntity, body);
            StringBuilder builder = new StringBuilder();

            //JSON对象转换
            bodyChangeJson(body, builder, userEntity);
            //回写数据
            requestWrapper.setBody(builder.toString().getBytes());
        } catch (IOException e) {
            logUtil.error(e);
        }
    }

    /**
     * 将请求体转换为 JSON 对象
     *
     * @param body       请求体
     * @param builder    转换结果
     * @param userEntity 用户信息
     * @return 返回是否做过转换
     */
    @Override
    public boolean bodyChangeJson(String body, StringBuilder builder, UserEntity userEntity) {
        boolean isBodyAdd = false;
        try {
            //验证是否为数组
            if (body.contains(leftBrackets) && body.contains(rightBrackets)) {
                if (!body.endsWith(rightBrackets) || !body.startsWith(leftBrackets)) {
                    body = leftBrackets + body + rightBrackets;
                    isBodyAdd = true;
                }
                JSONArray array = new JSONArray();
                //转换为 JSON 对象
                JSONArray jsonArray = JsonUtils.parseArray(body);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    addJsonObject(jsonObject, userEntity);
                    array.add(jsonObject);
                }
                if (isBodyAdd) {
                    builder.append(array.getJSONObject(0).toJSONString());
                } else {
                    builder.append(array.toJSONString());
                }
            } else {
                JSONObject jsonObject = JsonUtils.parseObject(body);
                if (jsonObject.size() > 0) {
                    addJsonObject(jsonObject, userEntity);
                    builder.append(jsonObject.toString());
                } else {
                    builder.append(body);
                }
            }
        } catch (Exception ex) {
            logUtil.info(ex.getMessage());
            builder.append(body);
        }

        return isBodyAdd;
    }

    /**
     * 日志记录
     *
     * @param httpServletRequest http request
     * @param userEntity         用户实体
     * @param inString           传入参数
     */
    @Override
    public void logRecord(HttpServletRequest httpServletRequest, UserEntity userEntity, String inString) {
        String id = GeneratedValueUtils.getUuidString();
        String serialNumber = "";
        try {
            serialNumber = orderNumberServiceImpl.generateNoDateNumber("R", 12).replace("R", "");
        } catch (GlobalException e) {
            logUtil.error(e.getMessage());
        }
        userEntity.setLogId(id);
        //日志记录
        LogRecordEntity recordEntity = new LogRecordEntity();
        recordEntity.setId(id);
        recordEntity.setServletPath(httpServletRequest.getServletPath());
        recordEntity.setUserName(userEntity.getUserName());
        recordEntity.setOperatorName(userEntity.getRealName());
        recordEntity.setAddTime(System.currentTimeMillis());
        recordEntity.setClientIp(httpServletRequest.getRemoteAddr());
        recordEntity.setParameter(inString);
        try {
            String modular = "未知模块";
            //获取路径
            String servletPath = httpServletRequest.getServletPath();
            //查找模块
            JSONArray jsonArray = JSONArray.parseArray(customConfig.getModular());
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Set<Map.Entry<String, Object>> entry = jsonObject.entrySet();
                Optional<Map.Entry<String, Object>> optional = entry.stream()
                        .filter(f -> servletPath.equals(f.getKey()))
                        .findFirst();
                if (!optional.isPresent()) {
                    continue;
                }
                Map.Entry<String, Object> map = optional.get();
                modular = map.getValue().toString();
            }

            recordEntity.setModular(modular);
        } catch (Exception ex) {
            logUtil.error(ex.getMessage());
        }
        recordEntity.setSerialNumber(Integer.valueOf(serialNumber).toString());

        //保存数据
        mongoServiceImpl.saveLog(recordEntity);
    }

    /**
     * 添加 JSON 值
     *
     * @param jsonObject JSON 对象
     * @param userEntity 用户实体
     */
    @Override
    public void addJsonObject(JSONObject jsonObject, UserEntity userEntity) {
        jsonObject.put("addUserId", userEntity.getId());
        jsonObject.put("addUserName", userEntity.getUserName());
        jsonObject.put("organizationId", userEntity.getOrganizationId());
        jsonObject.put("organizationCode", userEntity.getOrganizationCode());
        jsonObject.put("organizationName", userEntity.getOrganizationName());
        jsonObject.put("topOrganizationId", userEntity.getTopOrganizationId());
        jsonObject.put("topOrganizationCode", userEntity.getTopOrganizationCode());
        jsonObject.put("topOrganizationName", userEntity.getTopOrganizationName());
        jsonObject.put("logId", userEntity.getLogId());
    }

    /**
     * 请求是否需要验证
     *
     * @param servletPath 请求路径
     * @return 返回结果（布尔值）
     */
    @Override
    public boolean requestNeedsValidation(String servletPath) {
        String path = "/";
        //验证请求路径是否为或登录页面
        if (StringUtils.isNullAndSpaceOrEmpty(servletPath) || path.equals(servletPath)) {
            return false;
        }
        boolean validation = false;
        //获取不过虑地址
        String regex = "\\|";
        logUtil.info("访问路径：" + servletPath);
        logUtil.info("路径：" + interceptorConfig.getInterceptorFilter());
        String[] filterArray = interceptorConfig.getInterceptorFilter()
                .replaceAll(",", regex)
                .replaceAll("，", regex)
                .split(regex);

        //验证是否需要过虑
        List<String> filterList = Arrays.asList(filterArray);
        if (filterList.size() > 0) {
            //将字符转换为小写
            String servletPathLowerCase = servletPath.toLowerCase();
            List<String> list = filterList.stream()
                    .filter(f -> StringUtils.isNotNullAndEmpty(f) && servletPathLowerCase.contains(f.toLowerCase()))
                    .collect(Collectors.toList());
            if (list.size() == 0) {
                validation = true;
            }
        } else {
            validation = true;
        }

        logUtil.info("返回结果：" + validation);
        //返回结果
        return validation;
    }
}
