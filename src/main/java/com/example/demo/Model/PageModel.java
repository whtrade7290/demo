package com.example.demo.Model;

import lombok.Data;

@Data
public class PageModel {

    private int count;
    private int pageCount;
    private int pageBlock;
    private int startPage;
    private int endPage;
}
