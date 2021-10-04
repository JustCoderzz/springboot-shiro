package com.example.controller;

import com.example.dto.CodeLoginDTO;
import com.example.dto.JsonResult;
import com.example.dto.PassWordLoginDTO;
import com.example.dto.RegisterDTO;
import com.example.service.impl.UserService;
import com.example.utils.common.Constant;
import com.example.utils.common.RedisUtils;
import com.example.utils.exception.CmdcException;
import com.example.utils.exception.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author lusir
 * @date 2021/10/2 - 12:56
 **/
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private  RedisUtils redisUtils;

    @PostMapping(value = "/user/register",name = "用户注册")
    public JsonResult userRegister(@RequestBody @Valid RegisterDTO registerDTO){
        JsonResult jsonResult;
        try {
            userService.register(registerDTO.getUserId(),registerDTO.getUserName(),registerDTO.getPassword(),registerDTO.getRemark());
            jsonResult=new JsonResult();
        }catch (CmdcException e){
            throw e;
        }
        catch (Exception e){
            log.info(e.getMessage());
            throw new CmdcException(ErrorEnum.SERVER_ERROR);
        }
        return jsonResult;
    }

    @GetMapping(value = "/user/sendVerifcationCode",name = "发送验证码")
    public JsonResult<Object> sendVerifcationCode(String userId){
        JsonResult<Object> jsonResult;
        if(!StringUtils.hasLength(userId)) throw new CmdcException(ErrorEnum.BAD_PARAM);
        try{
            int code=userService.sendVerificationCode(userId);
            jsonResult=new JsonResult<Object>();
            jsonResult.setData(code);
        }catch (CmdcException e){ //如果是我们的异常 直接扔出去
            throw e;
        }catch (Exception e){ //如果不是我们期待的异常,返回服务器错误就好
            log.info("错误信息",e);
            throw new CmdcException(ErrorEnum.SERVER_ERROR);
        }
        return jsonResult;
    }
    @PostMapping(value = "/user/passwordLogin",name = "用户密码登录")
    public  JsonResult passwordLogin(@RequestBody @Valid PassWordLoginDTO passWordLoginDTO){
        log.info("参数为"+passWordLoginDTO);
        JsonResult jsonResult;
        try {
            jsonResult=new JsonResult(userService.passWordLogin(passWordLoginDTO.getUserId(),passWordLoginDTO.getPassword()));
        }catch (CmdcException e){
            throw  e;
        }catch (Exception e){
            log.info(e.getMessage());
            if(e instanceof IncorrectCredentialsException){
                throw e;
            }
            throw new CmdcException(ErrorEnum.SERVER_ERROR);
        }
        return  jsonResult;
    }

    @PostMapping(value = "/user/codeLogin",name = "验证码登录")
    public  JsonResult codeLogin(@RequestBody @Valid CodeLoginDTO codeLoginDTO){
        log.info("验证码登录参数为"+codeLoginDTO);
        JsonResult jsonResult;
        try {
            jsonResult =new JsonResult(userService.verificationCodeLogin(codeLoginDTO.getUserId(),codeLoginDTO.getCode()));

        }catch (CmdcException e){
            throw e;
        }catch (Exception e){
            log.info("异常:{}",e);
            throw new CmdcException(ErrorEnum.SERVER_ERROR);
        }
        return jsonResult;
    }
}
