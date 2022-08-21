package com.example.demo.Controller;

import com.example.demo.Service.MailService;
import com.example.demo.Service.UserService;
import com.example.demo.Utility.Utility;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
@Log
@Controller
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private Utility utility;

    @RequestMapping(value = "/mailSend", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> mailSend(@RequestBody Map<String, Object> params){
        log.info("샌드");
        String email = (String) params.get("email");
        log.info("email == " + email);

        String randomNum = utility.AuthenticationNumber();

        userService.randomInsert(randomNum);
        mailService.send(email, randomNum);

        return new ResponseEntity("OK", HttpStatus.OK);
    }
}
