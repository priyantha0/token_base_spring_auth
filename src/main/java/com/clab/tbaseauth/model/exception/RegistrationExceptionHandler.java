package com.clab.tbaseauth.model.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegistrationExceptionHandler {

  public record ErrorModel(String error) {}

  @ExceptionHandler
  public ResponseEntity<ErrorModel> handle(RegistrationRequestValidationException exception) {
    return ResponseEntity.badRequest().body(new ErrorModel(exception.getMessage()));
  }
}
