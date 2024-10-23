package com.surveymate.api.util;

import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class CodeGenerator {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    private final MemberRepository memberRepository;

    public String generateCode(String code) throws Exception{
        String today = DATE_FORMAT.format(new Date());
        String topNumStr = null;
        String prefixCode = null;
        if("MU01".equals(code)){
            prefixCode = "M";
            topNumStr = memberRepository.findTopMemNumByToday(today);
        }else{
            throw new Exception("잘못된 코드 번호");
        }

        int number = 0;
        if(topNumStr != null && !topNumStr.isEmpty()){
            int codeStartIndex = topNumStr.indexOf(today);
            if(codeStartIndex != -1){
                int index = codeStartIndex + today.length();
                number = Integer.parseInt(topNumStr.substring(index));
            }
        }
        number += 1;
        String formattedNumber = String.format("%04d", number);

        return prefixCode + today + formattedNumber;
    }

}
