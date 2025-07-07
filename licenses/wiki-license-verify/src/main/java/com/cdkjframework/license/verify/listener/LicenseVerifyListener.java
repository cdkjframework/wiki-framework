package com.cdkjframework.license.verify.listener;

import com.cdkjframework.license.entity.LicenseResultEntity;
import com.cdkjframework.license.verify.config.LicenseVerifyConfig;
import com.cdkjframework.license.verify.manger.LicenseVerifyManager;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.license.verify.config
 * @ClassName: LicenseInterceptorConfig
 * @Description: 项目启动时安装证书&定时检测lic变化，自动更替lic
 * @Author: xiaLin
 * @Date: 2023/3/15 15:52
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class LicenseVerifyListener implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(LicenseVerifyListener.class);

    /**
     * 读取配置
     */
    private final LicenseVerifyConfig properties;

    /**
     * 文件唯一身份标识 == 相当于人类的指纹一样
     */
    private static String MD5 = StringUtils.Empty;

    /**
     * 是Load
     */
    private static boolean isLoad = false;

    /**
     * 应用程序事件
     *
     * @param event 事件
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (StringUtils.isNotNullAndEmpty(properties.getLicensePath())) {
            install();
            try {
                String readMd5 = getMd5(properties.getLicensePath());
                isLoad = true;
                if (StringUtils.isNullAndSpaceOrEmpty(MD5)) {
                    MD5 = readMd5;
                }
            } catch (Exception e) {
                logUtils.error(e);
            }
        }
    }

    /**
     * 每天凌晨 1 点检测一次，不能太快也不能太慢
     */
    @Scheduled(cron = "0 0 1 * * ? ")
//    @Scheduled(cron = "0/5 * * * * ?")
    protected void timer() throws Exception {
        if (!isLoad) {
            return;
        }
        String readMd5 = getMd5(properties.getLicensePath());
        // 不相等，说明lic变化了
        if (!readMd5.equals(MD5)) {
            install();
            MD5 = readMd5;
        }
    }

    /**
     * 安装证书
     */
    private void install() {
        logUtils.info("++++++++ 开始安装证书 ++++++++");
        LicenseVerifyManager licenseVerifyManager = new LicenseVerifyManager();
        /** 走定义校验证书并安装 */
        LicenseResultEntity result = licenseVerifyManager.install(properties.getVerifyParam());
        if (result.getResult()) {
            logUtils.info("++++++++ 证书安装成功 ++++++++");
        } else {
            logUtils.info("++++++++ 证书安装失败 ++++++++");
        }
    }

    /**
     * 获取文件的 md5
     */
    public String getMd5(String filePath) throws Exception {
        File file;
        String md5 = StringUtils.Empty;
        try {
            file = ResourceUtils.getFile(filePath);
            if (file.exists()) {
                FileInputStream stream = new FileInputStream(file);
                byte[] data = new byte[stream.available()];
                stream.read(data);
                md5 = DigestUtils.md5DigestAsHex(data);
                stream.close();
            }
        } catch (FileNotFoundException e) {
            logUtils.error(e);
        }
        // 返回结果
        return md5;
    }
}
