package com.surveymate.api.common.exception;

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
    public ResponseEntity<String> handlerCustomRuntimeExceptionHandler(CustomRuntimeException e) {
        log.error("CustomRuntimeException 발생: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.error("UserAlreadyExistsException 발생: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserNotFoundException ex) {
        log.error("UserNotFoundException 발생: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ThumbnailCreationException.class)
    public ResponseEntity<String> ThumbnailCreationException(ThumbnailCreationException ex) {
        log.error("ThumbnailCreationException 발생: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNameTooLongException.class)
    public ResponseEntity<String> FileNameTooLongException(FileNameTooLongException ex) {
        log.error("FileNameTooLongException 발생: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> EmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        log.error("EmailAlreadyExistsException 발생: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }


}
