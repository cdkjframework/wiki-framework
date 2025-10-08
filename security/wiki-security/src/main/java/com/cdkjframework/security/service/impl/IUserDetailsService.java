package com.cdkjframework.security.service.impl;

import com.cdkjframework.entity.user.security.SecurityUserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 抽象用户详细信息服务
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.service
 * @ClassName: IUserDetailsService
 * @Description: 抽象用户详细信息服务
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface IUserDetailsService extends UserDetailsService {


  /**
   * 列出用户所在的组织
   *
   * @param user 用户信息
   * @return 返回用户所在的组织列表
   */
  default void buildOrganization(SecurityUserEntity user) {

  }

  /**
   * 切换机构信息
   *
   * @param id 机构ID
   */
  default void change(String id) {

  }
}
