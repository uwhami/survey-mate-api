package com.surveymate.api.common.exception;

import com.surveymate.api.common.dto.ResponseDTO;
import com.surveymate.api.domain.member.exception.PasswordMismatchException;
import com.surveymate.api.domain.member.exception.UserAlreadyExistsException;
import com.surveymate.api.domain.member.exception.UserNotFoundException;
import com.surveymate.api.email.exception.EmailAlreadyExistsException;
import com.surveymate.api.file.exception.FileNameTooLongException;
import com.surveymate.api.file.exception.ThumbnailCreationException;
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
        log.error("CustomRuntimeException 발생: {}", e.getMessage(), e);
        return new ResponseEntity<>(ResponseDTO.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO<?>> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        log.error("UserAlreadyExistsException 발생: {}", e.getMessage(), e);
        return new ResponseEntity<>(ResponseDTO.failure(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleUserNotFoundException(UserNotFoundException e) {
        log.error("UserNotFoundException 발생: {}", e.getMessage(), e);
        return new ResponseEntity<>(ResponseDTO.failure(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ThumbnailCreationException.class)
    public ResponseEntity<ResponseDTO<?>> handleThumbnailCreationException(ThumbnailCreationException e) {
        log.error("ThumbnailCreationException 발생: {}", e.getMessage(), e);
        return new ResponseEntity<>(ResponseDTO.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNameTooLongException.class)
    public ResponseEntity<ResponseDTO<?>> handleFileNameTooLongException(FileNameTooLongException e) {
        log.error("FileNameTooLongException 발생: {}", e.getMessage(), e);
        return new ResponseEntity<>(ResponseDTO.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO<?>> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        log.error("EmailAlreadyExistsException 발생: {}", e.getMessage(), e);
        return new ResponseEntity<>(ResponseDTO.failure(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ResponseDTO<?>> handlePasswordMismatchException(PasswordMismatchException e) {
        log.error("PasswordMismatchException 발생: {}", e.getMessage(), e);
        return new ResponseEntity<>(ResponseDTO.failure(e.getMessage()), HttpStatus.CONFLICT);
    }

    // RuntimeException 처리
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO<?>> handleRuntimeException(RuntimeException ex) {
        log.error("RuntimeException 발생: {}",ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 에러
                .body(ResponseDTO.failure("Unexpected error occurred"));
    }

    // 기타 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<?>> handleGenericException(Exception ex) {
        log.error("Exception 발생: {}",ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 에러
                .body(ResponseDTO.failure("An error occurred"));
    }


}
