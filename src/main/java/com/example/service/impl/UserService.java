package com.example.service.impl;

/**
 * @author lusir
 * @date 2021/10/2 - 11:23
 **/
public interface UserService {
    void register( String userId, String userName,String password, String remark);

    int sendVerificationCode(String userId);

    String passWordLogin(String usrId,String passWord);

    String verificationCodeLogin(String userId, String code);
}
