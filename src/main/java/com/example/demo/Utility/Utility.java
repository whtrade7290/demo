package com.example.demo.Utility;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Utility {

    public String AuthenticationNumber(){

        Random random = new Random();

        String randomNum = "" + random.nextInt(10000);

        if (randomNum.length() != 5) {
            int addNum = 5 - randomNum.length();
            if (addNum > 0) {
                for (int i = 0; i < addNum; i++) {
                    randomNum = "0" + randomNum;
                    System.out.println("randomNum == " + randomNum);
                }
            }
        }

        return randomNum;
    }
}
