package com.surveymate.api.domain.group.exception;

import com.surveymate.api.common.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class GroupNotFoundException extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "해당 그룹이 존재하지 않습니다.";

    public GroupNotFoundException() {
        super(DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);
    }

    public GroupNotFoundException(String message) {
        super(message);
    }
}
