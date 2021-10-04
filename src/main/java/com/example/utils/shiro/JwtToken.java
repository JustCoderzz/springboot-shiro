package com.example.utils.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author lusir
 * @date 2021/10/4 - 10:26
 **/
public class JwtToken implements AuthenticationToken {
    private String token;
    public JwtToken(String token){
        this.token=token;
    }
    @Override
    public Object getPrincipal() {
        return  token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
