package com.example.demo.Controller;

import com.example.demo.Model.BoardModel;
import com.example.demo.Model.UserModel;
import com.example.demo.Service.MailService;
import com.example.demo.Service.UserService;
import com.example.demo.Validator.BoardValidator;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Log
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardValidator validator;


    @GetMapping("/login")
    public String login(){
        log.info("로그인");
        return "account/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        log.info("회원가입");
        model.addAttribute("userModel", new UserModel());
        return "account/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserModel userModel, BindingResult bindingResult){
        log.info("회원가입");

        validator.validate(userModel, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/account/register";
        }

        int result = userService.register(userModel);
        if (result != 0) {
            userService.authenticationTruncate();
        }
        return "redirect:/";
    }


}
