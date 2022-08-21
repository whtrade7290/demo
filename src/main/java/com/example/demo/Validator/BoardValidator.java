package com.example.demo.Validator;

import com.example.demo.Model.BoardModel;
import com.example.demo.Model.UserModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserModel.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
//        BoardModel boardModel = (BoardModel) obj;
        UserModel userModel = (UserModel) obj;
//        if (boardModel.getContent().isEmpty()){
//            errors.rejectValue("content","key", "내용을 입력하세요.");
//        }
//        if (boardModel.getTitle().isEmpty()){
//            errors.rejectValue("title", "key", "Validation 커스텀 끝!!");
//        }
        if (userModel.getUsername().isEmpty()){
            errors.rejectValue("username", "key", "아이디를 입력하세요.");
        }

    }
}
