package com.example.demo.Mapper;

import com.example.demo.Model.RoleModel;
import com.example.demo.Model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {


    int register(UserModel userModel);


    void insertUserRole(RoleModel roleModel);

    void randomInsert(@Param("randomNum") String randomNum);

    void authenticationTruncate();
}
