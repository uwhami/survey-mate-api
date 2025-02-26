package com.surveymate.api.domain.group.exception;

import com.surveymate.api.common.exception.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class GroupNotFoundException extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "GROUP_NOT_FOUND";

    public GroupNotFoundException() {
        super(DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);
    }

    public GroupNotFoundException(String message) {
        super(message);
    }
}
