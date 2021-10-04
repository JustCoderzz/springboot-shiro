package com.example.controller;

import com.example.dto.JsonResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lusir
 * @date 2021/10/4 - 15:20
 **/
@RestController
public class TestController {

    @GetMapping(value = "/test/get")
    @RequiresRoles(value = {"admin"},logical = Logical.OR)
    public JsonResult get() throws Exception{
        return new JsonResult("欢迎您,admin!");
    }

}
