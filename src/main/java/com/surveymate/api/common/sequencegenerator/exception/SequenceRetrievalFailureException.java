package com.surveymate.api.common.sequencegenerator.exception;

import com.surveymate.api.common.exception.CustomRuntimeException;

public class SequenceRetrievalFailureException extends CustomRuntimeException {

    private static final String DEFAULT_MESSAGE = "Sequence was updated but could not be retrieved";

    public SequenceRetrievalFailureException() {
        super(DEFAULT_MESSAGE);
    }

    public SequenceRetrievalFailureException(String message) {
        super(message);
    }
}
