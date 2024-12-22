package com.surveymate.api.common.exception;

import com.surveymate.api.common.dto.ResponseDTO;
import com.surveymate.api.domain.member.exception.UserNotFoundException;
import com.surveymate.api.email.exception.EmailAlreadyExistsException;
import com.surveymate.api.file.exception.FileNameTooLongException;
import com.surveymate.api.file.exception.ThumbnailCreationException;
import com.surveymate.api.domain.member.exception.UserAlreadyExistsException;
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
    public ResponseEntity<ResponseDTO<?>> handleUserAlreadyExistsException(UserNotFoundException e) {
        log.error("UserNotFoundException 발생: {}", e.getMessage(), e);
        return new ResponseEntity<>(ResponseDTO.failure(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ThumbnailCreationException.class)
    public ResponseEntity<ResponseDTO<?>> ThumbnailCreationException(ThumbnailCreationException e) {
        log.error("ThumbnailCreationException 발생: {}", e.getMessage(), e);
        return new ResponseEntity<>(ResponseDTO.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNameTooLongException.class)
    public ResponseEntity<ResponseDTO<?>> FileNameTooLongException(FileNameTooLongException e) {
        log.error("FileNameTooLongException 발생: {}", e.getMessage(), e);
        return new ResponseEntity<>(ResponseDTO.failure(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO<?>> EmailAlreadyExistsException(EmailAlreadyExistsException e) {
        log.error("EmailAlreadyExistsException 발생: {}", e.getMessage(), e);
        return new ResponseEntity<>(ResponseDTO.failure(e.getMessage()), HttpStatus.CONFLICT);
    }


}
