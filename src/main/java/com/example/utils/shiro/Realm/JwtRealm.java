package com.example.utils.shiro.Realm;

import com.example.domain.User;
import com.example.mapper.UserMapper;
import com.example.utils.common.Constant;
import com.example.utils.common.JwtUtils;
import com.example.utils.exception.ErrorEnum;
import com.example.utils.shiro.JwtToken;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author lusir
 * @date 2021/10/3 - 21:11
 **/
@Slf4j
public class JwtRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String token=principalCollection.toString();
        String userId = JwtUtils.getUserId(token);
        log.info("JwtRealm身份认证开始，获取到的token是:{}",token);
        // 这里的空指针异常不需要处理 无论此处抛出什么异常 shiro均认为身份有问题
        LinkedHashMap<String, Object> stringObjectLinkedHashMap = userMapper.selectUserPermissionById(userId);
        //获取所有的角色
        String roleName = String.valueOf(stringObjectLinkedHashMap.get("roleName"));
        //获取所有的权限
        List<String> permissionsNameList =
                Splitter.on(",").splitToList(String.valueOf(stringObjectLinkedHashMap.get("permissionsNameList")));
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 添加角色
        authorizationInfo.addRole(roleName);
        //添加权限
        authorizationInfo.addStringPermissions(permissionsNameList);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("jwtRealm中关于身份验证的方法执行...传递的token是:{}",authenticationToken);
        String token = (String) authenticationToken.getCredentials();
        // 解密获得phone，用于和数据库进行对比
        String userId = JwtUtils.getUserId(token);
        if (userId == null) {
            throw new AuthenticationException(ErrorEnum.TOKEN_EXCEPTION.getMsg());
        }
        User user = userMapper.selectById(userId);
        //校验用户是否存在
        if(user == null){
            throw new AuthenticationException(ErrorEnum.ACCOUNT_UNUSUAL.getMsg());
        }
        //操作时校验的是非对称加密是否成立.
        if (!JwtUtils.verify(token, Constant.TOKEN_SECRET)) {
            log.info("token校验无效...");
            throw new AuthenticationException(ErrorEnum.TOKEN_EXCEPTION.getMsg());
        }

        log.info("进行身份验证时,用户提供的token有效");
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
