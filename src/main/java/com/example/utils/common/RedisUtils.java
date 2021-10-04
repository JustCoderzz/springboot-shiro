package com.example.utils.common;

import javafx.print.Collation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author lusir
 * @date 2021/10/3 - 16:37
 **/
@Component
public class RedisUtils {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

//    普通缓存放入
    public boolean set(String key,Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

//    普通缓存放入并设置时间
    public boolean set(String key,Object value,long time){
            try {
                if(time>0){
                    redisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
                }else {
                    set(key,value);
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
    }
//    获取值
    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

//    删除缓存
@SuppressWarnings("unchecked")
public void del(String key){
    redisTemplate.delete(key);
}
}
