package com.example.demo.Validator;

import com.example.demo.Model.BoardModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BoardModel.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        BoardModel p = (BoardModel) obj;
        if (p.getContent().isEmpty()){
            errors.rejectValue("content","key", "내용을 입력하세요.");
        }
        if (p.getTitle().isEmpty()){
            errors.rejectValue("title", "key", "Validation 커스텀 끝!!");
        }

    }
}
