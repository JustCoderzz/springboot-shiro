package com.example.filter;

import com.example.utils.common.Constant;
import com.example.utils.shiro.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lusir
 * @date 2021/10/4 - 10:17
 **/
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            System.out.println("尝试登录");
            executeLogin(request, response);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        log.info("进入JwtFilter类中...");
        System.out.println("执行登录");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(Constant.TOKEN_HEADER_NAME);
        log.info("获取到的token是:{}",token);
        // 判断token是否存在
        if (token == null) {
            return false;
        }
        JwtToken jwtToken = new JwtToken(token);
        try{
            log.info("提交UserModularRealmAuthenticator决定由哪个realm执行操作...");
            getSubject(request, response).login(jwtToken);
        } catch (AuthenticationException e){
            log.info("捕获到身份认证异常");
            return false;
        }
        return true;
    }
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }


}
