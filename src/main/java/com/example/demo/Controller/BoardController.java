package com.example.demo.Controller;

import com.example.demo.Model.BoardModel;
import com.example.demo.Model.PageModel;
import com.example.demo.Service.BoardService;
import lombok.extern.java.Log;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Log
@Controller
@RequestMapping("/board/*")
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
            int numberOfPosts = boards.size();
            double resultOfPages = numberOfPosts/(double)5;
            if (resultOfPages < 1) {
                resultOfPages = 1;
            }
            if (resultOfPages % 5 != 0){

                resultOfPages= Math.floor(resultOfPages);

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
        model.addAttribute("searchText", searchText);






        return "board/list";
    }
    @RequestMapping(value = "/insertTest", method = RequestMethod.POST)
    public String insertTest(){

        boardService.testSelect();
        boardService.insertTest("");

        log.info("실행완료");
        return "board/list";
    }

    String out = new String();
    @RequestMapping(value = "/fileToBinary")
    public String fileToBinary(){

        out = "";

        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        File file = new File("C:\\Users\\WHtra\\Downloads\\jlpt 자격증.jpg");

        log.info("file == " + file.getName());

        try{
            fis = new FileInputStream(file);
        }catch (FileNotFoundException e){
            log.info("FileNotFoundException Error");
        }

        int len = 0;
        byte[] buf = new byte[1024];
        try {
            while ((len = fis.read(buf)) != -1){
                baos.write(buf, 0, len);
            }

            byte[] fileArray = baos.toByteArray();
            out = new String(base64Enc(fileArray));

            fis.close();
            baos.close();
        }catch (IOException e){
            log.info("IOException Error");
        }

        log.info("out ==" + out);

        boardService.insertTest(out);

        return "board/fileBinaryTest";
    }

    @RequestMapping(value = "/binaryToFile")
    public String binaryToFile(){

        String binaryFile = boardService.getBoardTest();

        log.info("binaryFile ==" + binaryFile);

        String filePath = "C:\\Users\\WHtra\\Downloads";
        String fileName = "TestFile";

        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
        fileDir.mkdirs();
        }

        File destFile = new File(filePath +"\\"+ fileName);

        byte[] buff = binaryFile.getBytes();
        String toStr = new String(buff);
        byte[] b64dec = base64Dec(toStr);

        try {
          FileOutputStream fos = new FileOutputStream(destFile);
          fos.write(b64dec);
          fos.close();

        } catch (IOException e){
            log.info("IOException Error");
        }



        log.info("저장 됐는지 확인해봐!");

        return "board/fileBinaryTest";
    }

    private  byte[] base64Dec(String toStr) {
        return Base64.decodeBase64(toStr);
    }

    public  byte[] base64Enc(byte[] buffer) {
    return Base64.encodeBase64(buffer);
    }

    @RequestMapping("/excelToData")
    public String excelToData(){

        return "board/excelToData";
    }
    @RequestMapping(value = "/excelToData",method = RequestMethod.POST)
    public String excelToData(@RequestParam(name = "excelFile", required = false) MultipartFile multipartFile){

        try{
            FileInputStream file = new FileInputStream("C:\\Users\\WHtra\\Desktop\\학습\\test.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            int rowNo = 0;
            int cellIndex = 0;

            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getPhysicalNumberOfRows();
            for (rowNo = 0; rowNo<rows; rowNo++){
                XSSFRow row = sheet.getRow(rowNo);
                if (row != null){
                    int cells = row.getPhysicalNumberOfCells();

                    for (cellIndex = 0; cellIndex <= cells; cellIndex++){
                        XSSFCell cell = row.getCell(cellIndex);
                        String value="";
                        if (cell == null){
                            continue;
                        }else {

                            switch (cell.getCellType()){
                                case XSSFCell.CELL_TYPE_FORMULA:
                                    value = cell.getCellFormula();
                                    break;
                                case XSSFCell.CELL_TYPE_NUMERIC:
                                    value = cell.getNumericCellValue() + "";
                                    break;
                                case XSSFCell.CELL_TYPE_STRING:
                                    value = cell.getStringCellValue() + "";
                                    break;
                                case XSSFCell.CELL_TYPE_BLANK:
                                    value = cell.getBooleanCellValue() + "";
                                    break;
                                case XSSFCell.CELL_TYPE_ERROR:
                                    value = cell.getErrorCellValue() + "";
                                    break;

                            }
                        }
                        log.info(rowNo + "번 행 : " + cellIndex + "번 열 값은: " + value);
                    }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }




        return "board/excelToDataResult";
    }


}
