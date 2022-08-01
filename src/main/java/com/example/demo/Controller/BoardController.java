package com.example.demo.Controller;

import com.example.demo.Model.BoardModel;
import com.example.demo.Model.PageModel;
import com.example.demo.Service.BoardService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Log
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "") String searchText,
                       @RequestParam(defaultValue = "") String category,
                       BoardModel boardModel,
                       Model model) {
        log.info("searchText == " + searchText);
        log.info("category == " + category);

        // 전체 글갯수 가져오기
        // int count = noticeDao.getCountAll();
        int count = boardService.getCountBoard(); // 검색어 기준으로 글갯수 가져오기



        // ==================================================
        // 한 페이지에 해당하는 글목록 구하기 작업
        // ==================================================

        // 한페이지당 보여줄 글갯수 설정
        int pageSize = 10;

        // 사용자가 요청하는 페이지번호 파라미터값 가져오기
        int startRow = (pageNum - 1) * pageSize;

        // 글목록 가져오기
        List<BoardModel> boards = null;
        if (count > 0) {
            //noticeList = noticeDao.getNotices(startRow, pageSize);
            boards = boardService.getBoardList(startRow, pageSize, searchText, category);
            log.info("boards ==" + boards);
        }




        // ==================================================
        // 페이지블록 관련정보 구하기 작업
        // ==================================================

        PageModel pageModel = new PageModel();

        // 글갯수가 0보다 크면 페이지블록 계산해서 출력하기
        if (count > 0) {
            // 총 필요한 페이지 갯수 구하기
            // 글50개. 한화면에보여줄글 10개 => 50/10 = 5
            // 글55개. 한화면에보여줄글 10개 => 55/10 = 5 + 1페이지(나머지존재) => 6
            int pageCount = (count / pageSize) + (count % pageSize == 0 ? 0 : 1);
            //int pageCount = (int) Math.ceil((double) count / pageSize);

            // 한 화면에 보여줄 페이지갯수 설정
            int pageBlock = 5;

            // 화면에 보여줄 시작페이지번호 구하기
            // 1~5          6~10          11~15          16~20       ...
            // 1~5 => 1     6~10 => 6     11~15 => 11    16~20 => 16
            int startPage = ((pageNum / pageBlock) - (pageNum % pageBlock == 0 ? 1 : 0)) * pageBlock + 1;

            // 화면에 보여줄 끝페이지번호 구하기
            int endPage = startPage + pageBlock - 1;
            if (endPage > pageCount) {
                endPage = pageCount;
            }

            // 뷰에서 필요한 데이터를 PageDto에 저장

            pageModel.setCount(count);
            pageModel.setPageCount(pageCount);
            pageModel.setPageBlock(pageBlock);
            pageModel.setStartPage(startPage);
            pageModel.setEndPage(endPage);

            model.addAttribute("start", startPage);
            model.addAttribute("end", endPage);

            log.info("start = " + startPage);
            log.info("end = " + endPage);

        } // if


        model.addAttribute("boardModel", boardModel);
        model.addAttribute("boards", boards);
        model.addAttribute("pageNum", pageNum);






        return "board/list";
    }
}
