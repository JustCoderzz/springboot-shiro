package com.example.service.impl.impl;

import com.example.service.impl.UserService;
import com.example.service.impl.dao.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author lusir
 * @date 2021/10/2 - 11:24
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDomainService userDomainService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String userId, String userName, String password, String remark) {
        userDomainService.register(userId,userName,password,remark);

    }

    @Override
    public int sendVerificationCode(String userId) {
       return userDomainService.sendVerificationCode(userId);
    }

    @Override
    public String passWordLogin(String userId, String passWord) {
        return userDomainService.passwordLogin(userId,passWord);
    }

    @Override
    public String verificationCodeLogin(String userId, String code) {
        return userDomainService.verificationCodeLogin(userId,code);
    }
}
