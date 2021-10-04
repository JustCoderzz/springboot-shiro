package com.example.utils.exception;

import lombok.Data;

/**
 * @author lusir
 * @date 2021/10/2 - 12:31
 **/
@Data
public class CmdcException extends RuntimeException{

    private int code;
    private String message;

    public CmdcException(ErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMsg();
    }

    public CmdcException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
