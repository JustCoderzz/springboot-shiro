package com.example.dto;

import lombok.Data;

/**
 * @author lusir
 * @date 2021/10/2 - 12:58
 **/
@Data
public class JsonResult <T>{
    private int code;
    private String msg;
    private T data;


    public JsonResult() {
        this.code = 200;
        this.msg = "ok!";
    }


    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public JsonResult(T data) {
        this.data = data;
        this.code = 200;
        this.msg = "ok!";
    }


    public JsonResult(T data, String msg) {
        this.data = data;
        this.code = 200;
        this.msg = msg;
    }
}
