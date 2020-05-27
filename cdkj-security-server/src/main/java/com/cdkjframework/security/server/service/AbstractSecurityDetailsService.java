package com.cdkjframework.security.server.service;

import com.cdkjframework.entity.user.security.UserDetailsEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security
 * @ClassName: SecurityDetailsService
 * @Description: 安全详细信息服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public abstract class AbstractSecurityDetailsService implements UserDetailsService {

    /**
     * 通过用户名加载用户信息
     *
     * @param userName 用户名
     * @return 返回用户信息
     * @throws UsernameNotFoundException 用户异常
     */
    @Override
    public abstract UserDetailsEntity loadUserByUsername(String userName) throws UsernameNotFoundException;
}
