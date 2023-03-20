package com.cdkjframework.license.verify.config;

import com.cdkjframework.license.core.entity.LicenseVerifyEntity;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.license.verify.config
 * @ClassName: LicenseVerifyConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/3/15 15:43
 * @Version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.custom.license.verify")
public class LicenseVerifyConfig {

    /**
     * 主题
     */
    private String subject;

    /**
     * 公共别名
     */
    private String publicAlias;

    /**
     * 公共密钥路径
     */
    private String publicKeysStorePath;

    /**
     * 密钥密码
     */
    private String storePassword;

    /**
     * 受权文件路径
     */
    private String licensePath;

    /**
     * 获取验证实体
     *
     * @return 返回验证参数
     */
    public LicenseVerifyEntity getVerifyParam() {
        LicenseVerifyEntity param = new LicenseVerifyEntity();
        param.setSubject(subject);
        param.setPublicAlias(publicAlias);
        param.setStorePass(storePassword);
        param.setLicensePath(licensePath);
        param.setPublicKeysStorePath(publicKeysStorePath);

        // 返回结果
        return param;
    }
}
