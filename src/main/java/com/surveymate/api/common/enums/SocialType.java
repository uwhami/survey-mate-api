package com.surveymate.api.common.enums;

public enum SocialType {

    HOMEPAGE(0),
    GOOGLE(1);

    private final int value;

    SocialType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SocialType fromValue(int value) {
        for (SocialType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid SocialType value: " + value);
    }
}
