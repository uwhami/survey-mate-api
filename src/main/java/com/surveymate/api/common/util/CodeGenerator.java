package com.surveymate.api.common.util;

import com.surveymate.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
@Component
@RequiredArgsConstructor
public class CodeGenerator {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    private final MemberRepository memberRepository;

    public String generateCode(String code) {
        String today = DATE_FORMAT.format(new Date());
        int topNum = 0;
        String prefixCode = null;
        try{
            if ("MU01".equals(code)) {
                prefixCode = "M";
                topNum = memberRepository.findTopMemNumByToday(today);
            } else {
                throw new IllegalArgumentException("잘못된 코드 번호입니다.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


        topNum += 1;
        String codeNum;
        if(topNum < 1000){
            String formattedNumber = String.format("%04d", topNum);
            codeNum = prefixCode + today + formattedNumber;
        }else{
            codeNum = prefixCode + today + topNum;
        }

        return codeNum;
    }

}
