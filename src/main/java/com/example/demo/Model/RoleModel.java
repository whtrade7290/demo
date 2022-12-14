package com.example.demo.Model;

import lombok.Data;

import java.util.List;

@Data
public class RoleModel {

    private int id;
    private String name;

    private List<UserModel> users;
}
