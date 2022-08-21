package com.example.demo.Service;

import com.example.demo.Mapper.UserMapper;
import com.example.demo.Model.RoleModel;
import com.example.demo.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public int register(UserModel userModel) {
        String encodedPasswd = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(encodedPasswd);
        userModel.setEnabled(true);

        int result = userMapper.register(userModel);
        RoleModel roleModel = new RoleModel();
        roleModel.setId(userModel.getId());


        userMapper.insertUserRole(roleModel);

        return result;


    }

    public void randomInsert(String randomNum) {
        userMapper.randomInsert(randomNum);
    }

    public void authenticationTruncate() {
        userMapper.authenticationTruncate();
    }
}
