package com.surveymate.api.common.enums;

public enum MemberStatus {
    ACTIVE(1),       // 활성화 회원
    DEACTIVATED(0);  // 탈퇴한 회원

    private final int value;

    MemberStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MemberStatus fromValue(int value) {
        for (MemberStatus status : MemberStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value for MemberStatus: " + value);
    }
}
