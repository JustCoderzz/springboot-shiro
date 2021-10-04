package com.example.utils.exception;

import com.example.dto.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

/**
 * @author lusir
 * @date 2021/10/4 - 15:35
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(CmdcException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult handleBusinessError(CmdcException exception) {
        int code = exception.getCode();
        String message = exception.getMessage();
        return new JsonResult(code, message);
    }

    @ExceptionHandler
    @ResponseBody
    public JsonResult<ErrorEnum> ErrorHandler(AuthorizationException e) {
        log.error("权限校验失败！", e);
        return new JsonResult<>(ErrorEnum.NO_AUTH.getCode(),ErrorEnum.NO_AUTH.getMsg());
    }


    @ExceptionHandler
    @ResponseBody
    public JsonResult ErrorHandler(AuthenticationException e) {
        log.error("用户名或密码错误,用户登录失败！", e);
        return new JsonResult<>(ErrorEnum.ERROR_ACCOUNT.getCode(),ErrorEnum.ERROR_ACCOUNT.getMsg());
    }

    @ExceptionHandler
    @ResponseBody
    public JsonResult ErrorHandler(IncorrectCredentialsException e) {
        log.error("用户名或密码错误,用户登录失败！", e);
        return new JsonResult<>(ErrorEnum.ERROR_ACCOUNT.getCode(),ErrorEnum.ERROR_ACCOUNT.getMsg());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public JsonResult handler(RuntimeException e) throws IOException {
        log.error("运行时异常:-------------->",e);
        return new JsonResult(ErrorEnum.SERVER_ERROR.getCode(),ErrorEnum.SERVER_ERROR.getMsg());
    }





}
