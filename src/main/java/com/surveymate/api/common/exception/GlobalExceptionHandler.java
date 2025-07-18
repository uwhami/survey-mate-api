package com.surveymate.api.common.exception;

import com.surveymate.api.common.dto.ResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ResponseDTO<?>> handlerCustomRuntimeExceptionHandler(CustomRuntimeException e) {
        log.error("CustomRuntimeException 발생: {}", e.getMessage());
        return new ResponseEntity<>(ResponseDTO.failure(e.getCause().getMessage()), e.getStatus());
    }


    // RuntimeException 처리
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO<?>> handleRuntimeException(RuntimeException ex) {
        log.error("RuntimeException 발생: {}",ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 에러
                .body(ResponseDTO.failure("Unexpected error occurred"));
    }

    // 기타 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<?>> handleGenericException(Exception ex) {
        log.error("Exception 발생: {}",ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 에러
                .body(ResponseDTO.failure("An error occurred"));
    }


}
