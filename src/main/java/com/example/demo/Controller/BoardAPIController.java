package com.example.demo.Controller;

import com.example.demo.Model.BoardModel;
import com.example.demo.Service.BoardService;
import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Log
@RequestMapping(value = "/admin")
@RestController
public class BoardAPIController {

    @Autowired
    private BoardService boardService;

    @RequestMapping(value = "/ajaxList", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> list(){


        Gson gson = new Gson();


        // Map
        Map<String, Object> map = new HashMap<>();
        List<BoardModel> boards = boardService.ajaxBoardList();
        String jsonBoards = gson.toJson(boards);

        map.put("jsonBoards", jsonBoards);

        return new ResponseEntity(boards, HttpStatus.OK);
        // test
    }

    @RequestMapping(value = "/ajaxDelete", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody Map<String, Object> params){
//        String uid = request.getParameter("uid");
        String strUid = (String) params.get("uid");
        int uid = Integer.parseInt(strUid);
        log.info("uid ==" + uid);

         boardService.boardDelete(uid);





        return new ResponseEntity("OK", HttpStatus.OK);
    }

    @PostMapping (value = "/comunityList")
    public ResponseEntity<?> comunityList(@RequestBody  Map<String, Object> params){

        String strStartRow = (String) params.get("startRow");
        int startRow = Integer.parseInt(strStartRow);

        int pageSize = 10;

        String strPageNum = (String)params.get("pageNum");
        int pageNum = Integer.parseInt(strPageNum);

        String sortCode = (String)params.get("sortCode");
        String searchText = (String)params.get("searchText");


        log.info("testestset");
        Map<String, Object> map = new HashMap<>();

        List<BoardModel> communityList = boardService.selectCommunity(startRow, pageSize, sortCode, searchText);
        map.put("communityList", communityList);

        map.put("pageNum", pageNum);

        log.info("communityList == " + communityList);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

}
