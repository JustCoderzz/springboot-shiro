package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author lusir
 * @date 2021/10/3 - 19:17
 **/
@Getter
@Setter
@ToString
public class PassWordLoginDTO {
    @NotNull(message = "用户密码登录传递的id不能为空")
    private String userId;
    @NotBlank(message = "用户登录密码不能为空")
    private  String password;
}
