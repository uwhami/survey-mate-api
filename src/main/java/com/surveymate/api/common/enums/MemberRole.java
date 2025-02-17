package com.surveymate.api.common.enums;

public enum MemberRole {

    USER(0, "ROLE_USER"),
    MANAGER(1, "ROLE_MANAGER"),
    ADMIN(2, "ROLE_ADMIN");

    private final int code;
    private final String authority;

    MemberRole(int code, String authority) {
        this.code = code;
        this.authority = authority;
    }

    public int getCode() {
        return code;
    }

    public String getAuthority() {
        return authority;
    }

    public static MemberRole fromCode(int code) {
        for (MemberRole role : values()) {
            if (role.code == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role code: " + code);
    }

    /**
     * 권한 문자열 기반으로 MemberRole 반환
     */
    public static MemberRole fromAuthority(String authority) {
        for (MemberRole role : values()) {
            if (role.authority.equalsIgnoreCase(authority)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role authority: " + authority);
    }

}
