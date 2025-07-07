package com.cdkjframework.license.verify.interceptor;

import com.cdkjframework.enums.ResponseBuilderEnums;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.license.entity.LicenseExtraEntity;
import com.cdkjframework.license.entity.LicenseResultEntity;
import com.cdkjframework.license.verify.annotion.VerifyLicense;
import com.cdkjframework.license.verify.config.LicenseVerifyConfig;
import com.cdkjframework.license.verify.listener.CustomVerifyListener;
import com.cdkjframework.license.verify.manger.LicenseVerifyManager;
import com.cdkjframework.util.tool.StringUtils;
import de.schlichtherle.license.LicenseContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.license.verify.config
 * @ClassName: LicenseInterceptorConfig
 * @Description: License 验证拦截器
 * @Author: xiaLin
 * @Date: 2023/3/15 15:52
 * @Version: 1.0
 */
public class LicenseVerifyInterceptor implements HandlerInterceptor {

    /**
     * 配置读取
     */
    @Autowired
    private LicenseVerifyConfig properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            VerifyLicense annotation = method.getAnnotation(VerifyLicense.class);
            if (StringUtils.isNotNullAndEmpty(annotation)) {
                LicenseVerifyManager licenseVerifyManager = new LicenseVerifyManager();
                /** 1、校验证书是否有效 */
                LicenseResultEntity verifyResult = licenseVerifyManager.verify(properties.getVerifyParam());
                if (!verifyResult.getResult()) {
                    throw new GlobalException(verifyResult.getMessage());
                }
                LicenseContent content = verifyResult.getContent();
                LicenseExtraEntity licenseCheck = (LicenseExtraEntity) content.getExtra();
                if (verifyResult.getResult()) {
                    /** 增加业务系统监听，是否自定义验证 */
                    List<CustomVerifyListener> customListenerList = CustomVerifyListener.getCustomListenerList();
                    boolean compare = true;
                    for (CustomVerifyListener listener : customListenerList) {
                        boolean verify = listener.verify(licenseCheck);
                        compare = compare && verify;
                    }
                    return compare;
                }
                throw new GlobalException(ResponseBuilderEnums.Error.getValue(), verifyResult.getException().getMessage());
            }
        }
        return true;
    }
}
