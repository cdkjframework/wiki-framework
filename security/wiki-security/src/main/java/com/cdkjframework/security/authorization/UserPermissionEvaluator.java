package com.cdkjframework.security.authorization;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.authorization
 * @ClassName: UserPermissionEvaluator
 * @Description: 自定义权限注解验证
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {

    /**
     * hasPermission鉴权方法
     * 这里仅仅判断PreAuthorize注解中的权限表达式
     * 实际中可以根据业务需求设计数据库通过targetUrl和permission做更复杂鉴权
     * 当然targetUrl不一定是URL可以是数据Id还可以是管理员标识等,这里根据需求自行设计
     *
     * @Param authentication  用户身份(在使用hasPermission表达式时Authentication参数默认会自动带上)
     * @Param targetUrl  请求路径
     * @Param permission 请求路径权限
     * @Return boolean 是否通过
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
//        // 获取用户信息
//        SecurityUserEntity securityUserEntity =(SecurityUserEntity) authentication.getPrincipal();
//        // 查询用户权限(这里可以将权限放入缓存中提升效率)
//        Set<String> permissions = new HashSet<>();
//        List<SysMenuEntity> sysMenuEntityList = sysUserService.selectSysMenuByUserId(securityUserEntity.getUserId());
//        for (SysMenuEntity sysMenuEntity:sysMenuEntityList) {
//            permissions.add(sysMenuEntity.getPermission());
//        }
//        // 权限对比
//        if (permissions.contains(permission.toString())){
//            return true;
//        }
        return true;
    }

    /**
     * 用于评估权限的替代方法，其中只有目标对象的标识符可用，而不是目标实例本身。
     *
     * @param authentication 用户身份(在使用hasPermission表达式时Authentication参数默认会自动带上)
     * @param targetId       对象实例的标识符（通常为Long）
     * @param targetType     表示目标类型的字符串（通常是Java类名）。不为空。
     * @param permission     表达式系统提供的权限对象的表示形式。不为空。
     * @return 如果权限被授予，则为true，否则为false
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
