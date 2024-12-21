package com.surveymate.api.common.dto;

import lombok.Getter;

@Getter
public abstract class MemnumAware {
    private String memNum;

    public void setMemNum(String memNum) {
        this.memNum = memNum;
    }
}
