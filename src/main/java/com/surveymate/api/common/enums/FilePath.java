package com.surveymate.api.common.enums;

public enum FilePath {

    MEMBER_PROFILE("memberProfile")
    ,TEST("test");

    private final String propertyName;

    FilePath(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
