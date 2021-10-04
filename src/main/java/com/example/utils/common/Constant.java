package com.example.utils.common;

/**
 * @author lusir
 * @date 2021/10/3 - 16:55
 **/
public class Constant {

    /**
     * 验证码过期时间 此处为五分钟
     */
    public static final Integer CODE_EXPIRE_TIME = 60 * 5;

    /**
     * jwtToken过期时间 默认为30天
     * public static Integer TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;
     */
    public static final Long TOKEN_EXPIRE_TIME = 31 * 24 * 60 * 60 * 1000L;

    /**
     * UserId
     */
    public static final String USER_ID = "userId";


    /**
     * token请求头名称
     */
    public static final String TOKEN_HEADER_NAME = "authorization";

    /*做token签名的字符串*/
    public static final String TOKEN_SECRET="abc";

    //token的载荷中盛放的信息 只盛放一个userId 其余什么也不再盛放
    public static final String TOKEN_CLAIM="userId";

    //redis存放用户验证码时给的前缀
    public static final String REDIS_LOGIN_CODE="LOGIN_CODE:";
}
