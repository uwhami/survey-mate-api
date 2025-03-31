package com.surveymate.api.common.dto;

import lombok.Getter;

@Getter
public abstract class MemInfoAware {
    private String memNum;
    private Long groupId;

    public void setMemNum(String memNum) {
        this.memNum = memNum;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
