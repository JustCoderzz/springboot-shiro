package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author lusir
 * @date 2021/10/2 - 10:57
 **/
@Getter
@Setter
@ToString
public class RegisterDTO {
    @NotNull(message = "用户输入手机号不可为空！")
    @Size(max = 11,min = 11,message = "手机号必须为11位")
    private String userId;

    @NotBlank(message = "用户输入的注册密码不可为空")
    @Size(max = 12,min = 6,message = "用户输入的密码在6到12位之间")
    private String  password;

    private  String userName;

    private  String remark;
}
