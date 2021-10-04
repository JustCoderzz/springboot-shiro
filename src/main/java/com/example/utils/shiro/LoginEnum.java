package com.example.utils.shiro;

/**
 * @author lusir
 * @date 2021/10/3 - 19:36
 **/
public enum LoginEnum {
    BY_PASSWORD("Password"),
    BY_CODE("Code")
    ;
   private String loginType;

    LoginEnum(String LoginType){
        this.loginType=LoginType;
    }
    public String getLoginType() {
        return loginType;
    }

}
