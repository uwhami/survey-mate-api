package com.surveymate.api.domain.group.exception;

import com.surveymate.api.common.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class GroupCodeGenerationException extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "그룹 코드를 생성할 수 없습니다.";

    public GroupCodeGenerationException() {
        super(DEFAULT_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public GroupCodeGenerationException(String message) {
        super(message);
    }
}
