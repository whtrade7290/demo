package com.example.demo.Model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
public class BoardModel {

    private int uid;
    @NotNull
    @Size(min = 2, max = 30)
    private String title;
    @NotNull
    @Size(min = 2, max = 30, message = "좆빨아라")
    private String content;
    private String writer;
    private String save_time;
    private String binaryFile;

}
