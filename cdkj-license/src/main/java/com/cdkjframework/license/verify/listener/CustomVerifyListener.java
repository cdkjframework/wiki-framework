package com.cdkjframework.license.verify.listener;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.license.core.entity.LicenseExtraEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.license.verify.config
 * @ClassName: LicenseInterceptorConfig
 * @Description: 增加业务系统中自定义证书验证监听器
 * @Author: xiaLin
 * @Date: 2023/3/15 15:52
 * @Version: 1.0
 */
public abstract class CustomVerifyListener {

    /**
     * 软件证书参数全局验证监听容器
     */
    private static final List<CustomVerifyListener> CUSTOM_VERIFY_LISTENER_LIST = new ArrayList<>(16);

    public static List<CustomVerifyListener> getCustomListenerList() {
        return CUSTOM_VERIFY_LISTENER_LIST;
    }

    /***
     * 默认构造函数，干了一件事情，就是会把所有实现了这个抽象类的子类实例全部添加到全局自定义验证监听器列表中
     * 因为在调用子类的构造函数时，会首先调用父类的构造器
     */
    public CustomVerifyListener() {
        addCustomListener(this);
    }

    public synchronized static void addCustomListener(CustomVerifyListener verifyListener) {
        CUSTOM_VERIFY_LISTENER_LIST.add(verifyListener);
    }

    /**
     * 业务系统自定义证书认证方法
     *
     * @param licenseExtra 自定义验证参数
     * @return boolean 是否成功
     */
    public abstract boolean verify(LicenseExtraEntity licenseExtra) throws GlobalException;

}
