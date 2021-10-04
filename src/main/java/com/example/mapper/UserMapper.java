package com.example.mapper;

import com.example.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author lusir
 * @date 2021/10/2 - 11:09
 **/
@Mapper
public interface UserMapper {
    User selectById(@Param("userId") String userId);

    void insertUser(@Param("user") User user);

    LinkedHashMap<String, Object> selectUserPermissionById(@Param("userId") String userId);


    LinkedHashMap<String, Object> selectUserPermissionByName(@Param("userName") String userId);
}
