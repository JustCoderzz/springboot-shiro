package com.example.service.impl.dao;

import com.example.domain.User;
import com.example.mapper.UserMapper;
import com.example.mapper.UserRoleMapper;
import com.example.utils.common.CommonsUtils;
import com.example.utils.common.Constant;
import com.example.utils.common.JwtUtils;
import com.example.utils.common.RedisUtils;
import com.example.utils.exception.CmdcException;
import com.example.utils.exception.ErrorEnum;
import com.example.utils.shiro.CustomizedToken;
import com.example.utils.shiro.LoginEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lusir
 * @date 2021/10/2 - 12:14
 **/
@Service
@Slf4j
public class UserDomainService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private  RedisUtils redisUtils;

    public  void register(String userId,String userName,String password,String remark){
        if(this.userMapper.selectById(userId)!=null) throw new CmdcException(500,"该手机号已经注册了");
        String encryptPassword= CommonsUtils.encryptPassword(password,String.valueOf(userId));
        log.info("加密后的密码是:"+encryptPassword);
        this.userMapper.insertUser(User.getUser(userId,userName,encryptPassword,remark));
        userRoleMapper.insert(userId,200);
    }

    public  int sendVerificationCode(String userId){
        if(this.userMapper.selectById(userId)==null){
            throw new CmdcException(ErrorEnum.USER_NOT_EXISTS);
        }
        int code=CommonsUtils.getCode();
        log.info("验证码为"+code);
        String encryptPassword=CommonsUtils.encryptPassword(String.valueOf(code),userId);
        redisUtils.set(Constant.REDIS_LOGIN_CODE+userId,encryptPassword,Constant.CODE_EXPIRE_TIME);
        return code;
    }

    public String passwordLogin(String userId,String password){
        Subject subject= SecurityUtils.getSubject();
        if(!StringUtils.hasLength(userId)) throw new CmdcException(ErrorEnum.ACCOUNT_UNUSUAL);
        User user=this.userMapper.selectById(userId);
        if(user==null) throw new CmdcException(ErrorEnum.ACCOUNT_UNUSUAL);
        CustomizedToken  customizedToken=new CustomizedToken(userId,password, LoginEnum.BY_PASSWORD.getLoginType());
        subject.login(customizedToken);
        return JwtUtils.sign(user.getUserId(),Constant.TOKEN_SECRET);
    }

    public String verificationCodeLogin(String userId,String code){
        Subject subject=SecurityUtils.getSubject();
        User user=this.userMapper.selectById(userId);
        if(user==null) throw new CmdcException(ErrorEnum.USER_NOT_EXISTS);
        CustomizedToken customizedToken=new CustomizedToken(userId,code,LoginEnum.BY_CODE.getLoginType());
        subject.login(customizedToken);
        String sign=JwtUtils.sign(user.getUserId(),Constant.TOKEN_SECRET);
        redisUtils.del(Constant.REDIS_LOGIN_CODE+userId);
        return sign;
    }


}
