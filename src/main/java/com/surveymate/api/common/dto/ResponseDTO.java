package com.surveymate.api.common.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {

    private boolean result;
    private String message;
    private T data;

    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>(true, null, data);
    }

    public static <T> ResponseDTO<T> failure(String message) {
        return new ResponseDTO<>(false, message, null);
    }

}
