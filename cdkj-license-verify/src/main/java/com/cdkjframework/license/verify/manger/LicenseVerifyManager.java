package com.cdkjframework.license.verify.manger;

import com.cdkjframework.license.entity.LicenseResultEntity;
import com.cdkjframework.license.entity.LicenseVerifyEntity;
import com.cdkjframework.license.helper.ParamInitHelper;
import com.cdkjframework.license.manager.LicenseCustomManager;
import com.cdkjframework.license.util.DateUtils;
import com.cdkjframework.util.log.LogUtils;
import de.schlichtherle.license.*;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

/**
 * <p>License校验类</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
public class LicenseVerifyManager {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(LicenseVerifyManager.class);

    /**
     * <p>安装License证书</p>
     *
     * @param param License校验类需要的参数
     */
    public synchronized LicenseResultEntity install(LicenseVerifyEntity param) {
        try {
            /** 1、初始化License证书参数 */
            LicenseParam licenseParam = ParamInitHelper.initLicenseParam(param, LicenseVerifyManager.class);
            /** 2、创建License证书管理器对象 */
            //走自定义的Lic管理
            LicenseCustomManager licenseManager = new LicenseCustomManager(licenseParam);
            /** 3、获取要安装的证书文件 */
            File licenseFile = ResourceUtils.getFile(param.getLicensePath());
            /** 4、如果之前安装过证书，先卸载之前的证书 == 给null */
            licenseManager.uninstall();
            /** 5、开始安装 */
            LicenseContent content = licenseManager.install(licenseFile);
            String message = MessageFormat.format("证书安装成功，证书有效期：{0} - {1}",
                    DateUtils.date2Str(content.getNotBefore()), DateUtils.date2Str(content.getNotAfter()));
            logUtils.info(message);
            return new LicenseResultEntity(message, content);
        } catch (LicenseContentException exception) {
            String message = exception.getMessage();
            logUtils.error(message);
            return new LicenseResultEntity(false, message, exception);
        } catch (Exception e) {
            logUtils.error(e.getMessage(), e);
            return new LicenseResultEntity(false, e.getMessage(), e);
        }
    }

    /**
     * <p>校验License证书</p>
     *
     * @param param License校验类需要的参数
     */
    public LicenseResultEntity verify(LicenseVerifyEntity param) {

        /** 1、初始化License证书参数 */
        LicenseParam licenseParam = ParamInitHelper.initLicenseParam(param, LicenseVerifyManager.class);
        /** 2、创建License证书管理器对象 */
        LicenseManager licenseManager = new LicenseCustomManager(licenseParam);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        /** 3、开始校验证书 */
        try {
            LicenseContent licenseContent = licenseManager.verify();
            String message = MessageFormat.format("证书校验通过，证书有效期：{0} - {1}",
                    format.format(licenseContent.getNotBefore()), format.format(licenseContent.getNotAfter()));
            logUtils.info(message);
            return new LicenseResultEntity(message, licenseContent);
        } catch (NoLicenseInstalledException ex) {
            String message = "证书未安装！";
            logUtils.error(message, ex);
            return new LicenseResultEntity(false, message, ex);
        } catch (LicenseContentException cex) {
            logUtils.error(cex.getMessage(), cex);
            return new LicenseResultEntity(false, cex.getMessage(), cex);
        } catch (Exception e) {
            String message = "证书校验失败！";
            logUtils.error(message, e);
            return new LicenseResultEntity(false, message, e);
        }
    }


}
