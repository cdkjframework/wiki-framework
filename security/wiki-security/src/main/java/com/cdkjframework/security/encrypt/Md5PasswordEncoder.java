package com.cdkjframework.security.encrypt;

import com.cdkjframework.util.encrypts.Md5Utils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.encrypt
 * @ClassName: Md5PasswordEncoder
 * @Description: Md5密码编码器
 * @Author: xiaLin
 * @Version: 1.0
 */
public class Md5PasswordEncoder implements PasswordEncoder {
    /**
     * 对原始密码进行编码
     *
     * @param rawPassword 密码
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return Md5Utils.getMd5(rawPassword.toString());
    }

    /**
     * 比较
     *
     * @param rawPassword     要编码和匹配的原始密码
     * @param encodedPassword 要与之比较的存储器中的编码密码
     * @return 如果原始密码在编码后与存储中的编码密码匹配，则为true
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 密码是否为空
        if (StringUtils.isNullAndSpaceOrEmpty(encodedPassword)) {
            return false;
        }

        // 对密码加密
        encodedPassword = encode(encodedPassword);

        // 返回比较结果
        return rawPassword.toString().equals(encodedPassword);
    }
}
