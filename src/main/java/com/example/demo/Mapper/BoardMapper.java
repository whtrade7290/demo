package com.example.demo.Mapper;

import com.example.demo.Model.BoardModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardModel> getBoardList(int startRow, int pageSize, String searchText, String category);
    List<BoardModel> ajaxBoardList();

    int getCountBoard();

    void boardDelete(int uid);

    void insertTest(String out);

    void testSelect();

    List<BoardModel> selectCommunity(@Param("startRow") int startRow,
                                         @Param("pageSize") int pageSize,
                                         @Param("sortCode") String sortCode,
                                         @Param("searchText") String searchText);

    String getBoardTest();

    void excelToInsert(BoardModel boardModel);

    void excelToUpdate(BoardModel boardModel);

    void insertBoard(BoardModel boardModel);
}
