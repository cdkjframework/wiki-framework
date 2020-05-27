package com.cdkjframework.security.server.encoder;

import com.cdkjframework.util.encrypts.Md5Utils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.encoder
 * @ClassName: CdkjPasswordEncoder
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class CdkjPasswordEncoder implements PasswordEncoder {

    /**
     * 加密处理
     *
     * @param rawPassword 值密码值
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return Md5Utils.getMd5(rawPassword.toString());
    }

    /**
     * 密码验证
     *
     * @param rawPassword     值密码
     * @param encodedPassword 加密后密码
     * @return 返回验证结果
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (StringUtils.isNullAndSpaceOrEmpty(rawPassword) || StringUtils.isNullAndSpaceOrEmpty(encodedPassword)) {
            return false;
        }
        return encode(rawPassword).equals(encodedPassword);
    }
}
