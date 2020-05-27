package com.cdkjframework.security.server.service;

import com.cdkjframework.center.service.RmsClientDetailsService;
import com.cdkjframework.entity.user.security.RmsClientDetailsEntity;
import com.cdkjframework.util.tool.CopyUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security
 * @ClassName: SecurityDetailsService
 * @Description: 安全详细信息服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class ClientDetailsServiceImpl implements ClientDetailsService {

    /**
     * 客户权限服务
     */
    @Autowired
    private RmsClientDetailsService rmsClientDetailsServiceImpl;

    /**
     * 通过用户名加载用户信息
     *
     * @param clientId 用户名
     * @return 返回用户信息
     * @throws ClientRegistrationException 用户异常
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        // 查询数据
        RmsClientDetailsEntity detailsEntity = new RmsClientDetailsEntity();
        detailsEntity.setClientId(clientId);
        detailsEntity = rmsClientDetailsServiceImpl.findClientDetailsByEntity(detailsEntity);

        // 加载信息
        return loadClient(detailsEntity);
    }

    /**
     * 加载客户信息
     *
     * @param detailsEntity 受权验证
     * @return 返回结果
     */
    public ClientDetails loadClient(RmsClientDetailsEntity detailsEntity) {
        BaseClientDetails clientDetails = CopyUtils.copyNoNullProperties(detailsEntity, BaseClientDetails.class);
        // 权限类型
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (StringUtils.isNotNullAndEmpty(detailsEntity.getAuthorities())) {
            String[] authoritiesArray = detailsEntity.getAuthorities().split(",");
            for (String key :
                    authoritiesArray) {
                authorities.add(new SimpleGrantedAuthority(key));
            }
        }
        clientDetails.setAuthorities(authorities);
        // 授权类型
        Set<String> authorizedGrantTypes = new TreeSet<>();
        if (StringUtils.isNotNullAndEmpty(detailsEntity.getAuthorizedGrantTypes())) {
            String[] authorizedGrantTypeArray = detailsEntity.getAuthorizedGrantTypes().split(",");
            authorizedGrantTypes.addAll(Arrays.asList(authorizedGrantTypeArray));
        } else {
            authorizedGrantTypes.add("password");
            authorizedGrantTypes.add("refresh_token");
            authorizedGrantTypes.add("authorization_code");
        }
        clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
        Set<String> scope = new TreeSet<>();
        if (StringUtils.isNotNullAndEmpty(detailsEntity.getScope())) {
            String[] scopeArray = detailsEntity.getScope().split(",");
            scope.addAll(Arrays.asList(scopeArray));
        } else {
            scope.add("all");
        }
        clientDetails.setScope(scope);
        clientDetails.isAutoApprove("true");
        return clientDetails;
    }
}
