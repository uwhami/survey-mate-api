package com.surveymate.api.common;

import com.surveymate.api.common.util.CodeGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CodeGeneratorTests {

    @Autowired
    private CodeGenerator codeGenerator;

    @Test
    public void createSQCodeTest() {
        String sqCode = codeGenerator.generateCode("SQ01");
        System.out.println(sqCode);
    }

}
