package com.example.demo.Service;

import com.example.demo.Mapper.BoardMapper;
import com.example.demo.Model.BoardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardMapper boardMapper;

    public List<BoardModel> getBoardList(int startRow, int pageSize, String searchText, String category) {
        return boardMapper.getBoardList(startRow, pageSize, searchText, category);
    }
    public List<BoardModel> ajaxBoardList(){
        return boardMapper.ajaxBoardList();
    }

    public int getCountBoard() {
        int count = boardMapper.getCountBoard();
        return count;
    }

    public void boardDelete(int uid) {
        boardMapper.boardDelete(uid);

    }

    public void insertTest(String out){
        boardMapper.insertTest(out);
    }


    public void testSelect() {
        boardMapper.testSelect();
    }

    public List<BoardModel> selectCommunity(int startRow, int pageSize, String sortCode, String searchText) {
        return boardMapper.selectCommunity(startRow, pageSize, sortCode, searchText);
    }

    public String getBoardTest(){
        return boardMapper.getBoardTest();
    }

    public  void excelToInsert(BoardModel boardModel){
        boardMapper.excelToInsert(boardModel);
    }

    public void excelToUpdate(BoardModel model) {
        boardMapper.excelToUpdate(model);
    }

    public void insertBoard(BoardModel boardModel) {
        boardMapper.insertBoard(boardModel);
    }
}
