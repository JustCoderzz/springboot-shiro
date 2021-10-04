package com.example.utils.common;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author lusir
 * @date 2021/10/2 - 12:37
 **/
public class CommonsUtils {

    //使用Md5加密
    public  static  String encryptPassword(String password,String phoneNumber){
        return String.valueOf(new SimpleHash("MD5", password, phoneNumber, 1024));
    }

    //获得六位验证码
    public  static  int getCode(){
        return (int)(((Math.random()*9+1))*100000);
    }
}
