package com.cdkjframework.security.service.impl;

import com.cdkjframework.entity.user.security.SecurityUserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.service
 * @ClassName: AbstractUserDetailsService
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public abstract class AbstractUserDetailsService implements UserDetailsService {

    /**
     * 查询用户信息
     *
     * @param username 用户名
     * @return 返回用户信息
     * @throws UsernameNotFoundException 用户未找到异常信息
     */
    @Override
    public abstract SecurityUserEntity loadUserByUsername(String username) throws UsernameNotFoundException;
}
