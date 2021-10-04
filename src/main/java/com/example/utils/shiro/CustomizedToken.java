package com.example.utils.shiro;


import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author lusir
 * @date 2021/10/3 - 19:31
 **/

public class CustomizedToken extends UsernamePasswordToken {
    public String loginType;

    public CustomizedToken(final String username, final String password, final String loginType) {
        super(username, password);
        this.loginType = loginType;
    }
    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
    @Override
    public String toString(){
        return "loginType="+ loginType +",username=" + super.getUsername()+",password="+ String.valueOf(super.getPassword());
    }
}
