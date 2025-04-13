package com.surveymate.api.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseResponse {
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String createMemNum;
    private String updateMemNum;
}
