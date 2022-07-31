package com.example.demo.Mapper;

import com.example.demo.Model.BoardModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardModel> getBoardList(int startRow, int pageSize);
    List<BoardModel> ajaxBoardList();

    int getCountBoard();

    void boardDelete(int uid);
}
