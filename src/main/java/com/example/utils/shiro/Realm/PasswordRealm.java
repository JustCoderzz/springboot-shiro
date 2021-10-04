package com.example.utils.shiro.Realm;

import com.example.domain.User;
import com.example.mapper.UserMapper;
import com.example.utils.shiro.CustomizedToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lusir
 * @date 2021/10/3 - 20:00
 **/
@Slf4j
public class PasswordRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomizedToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("PasswordRealm权限认证开始,传递的token:{}",authenticationToken);
        CustomizedToken token = (CustomizedToken) authenticationToken;
        log.info("PasswordRealm转换的自定义token是:{}",token);
        User user=userMapper.selectById(token.getUsername());
        if (user == null) {
            // 抛出账号不存在异常
            throw new UnknownAccountException();
        }
        Object credentials = user.getPassword();
        return new SimpleAuthenticationInfo(user, credentials, ByteSource.Util.bytes(user.getUserId()),getName());

    }


}
