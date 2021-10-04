package com.example.utils.shiro.Realm;

import com.example.domain.User;
import com.example.mapper.UserMapper;
import com.example.utils.common.Constant;
import com.example.utils.common.RedisUtils;
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
 * @date 2021/10/3 - 21:04
 **/
@Slf4j
public class CodeRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtils redisUtil;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomizedToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("CodeRealm权限认证开始,传递的token:{}",authenticationToken);
        //找出数据库中的对象  给定用户输入的对象做出对比
        CustomizedToken token = (CustomizedToken) authenticationToken;
        log.info("CodeRealm转换的自定义token是:{}",token);
        // 根据userId查询用户
        User user=userMapper.selectById(token.getUsername());
        if (user == null) {
            // 抛出账号不存在异常
            throw new UnknownAccountException();
        }
        Object credentials = redisUtil.get(Constant.REDIS_LOGIN_CODE + token.getUsername());
        log.info("redis取出的用户密码是:{}",credentials);
        //param1:数据库用户 param2:密码 param3:加密所用盐值 param4:当前realm的名称
        return new SimpleAuthenticationInfo(user, credentials, ByteSource.Util.bytes(user.getUserId()),getName());
    }
}
