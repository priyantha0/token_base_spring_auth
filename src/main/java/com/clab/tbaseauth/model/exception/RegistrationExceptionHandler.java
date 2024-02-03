package com.clab.tbaseauth.model.exception;

import com.clab.tbaseauth.model.RegistrationStatus;
import com.clab.tbaseauth.model.dto.RegistrationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class RegistrationExceptionHandler {

  @ExceptionHandler(RegistrationRequestValidationException.class)
  public ResponseEntity<RegistrationResponseDTO> handleRegistrationRequestExceptions(
      RegistrationRequestValidationException exception) {
    return badRequestResponse(Collections.singletonList(exception.getMessage()));
  }

  @ExceptionHandler(UserDataProcessingException.class)
  public ResponseEntity<RegistrationResponseDTO> handleUserDataProcessingException(
      UserDataProcessingException exception) {
    return badRequestResponse(Collections.singletonList(exception.getMessage()));
  }

  private ResponseEntity<RegistrationResponseDTO> badRequestResponse(List<String> errors) {
    return ResponseEntity.badRequest()
        .body(new RegistrationResponseDTO(RegistrationStatus.FAILED, errors));
  }
}
