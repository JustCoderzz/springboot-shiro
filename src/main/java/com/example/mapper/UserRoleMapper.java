package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lusir
 * @date 2021/10/2 - 12:50
 **/
@Mapper
public interface UserRoleMapper {
    void insert(@Param("userId") String userId, @Param("roleId") Integer roleId);
}
