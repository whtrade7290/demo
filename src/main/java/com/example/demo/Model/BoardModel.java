package com.example.demo.Model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BoardModel {

    private int uid;
    private String title;
    private String content;
    private String writer;
    private Timestamp save_time;
}
