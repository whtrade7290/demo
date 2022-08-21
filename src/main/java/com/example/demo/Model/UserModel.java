package com.example.demo.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserModel {

    private int id;
    private String username;
    private String password;
    private String email;
    private Boolean enabled;

    private List<RoleModel> roles = new ArrayList<>();

}
