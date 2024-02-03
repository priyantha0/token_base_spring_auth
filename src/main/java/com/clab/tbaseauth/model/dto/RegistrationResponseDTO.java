package com.clab.tbaseauth.model.dto;

import java.util.List;

public record RegistrationResponseDTO(
    String status, String token, String refreshToken, List<String> errors) {

  public RegistrationResponseDTO(String status, List<String> errors) {
    this(status, null, null, errors);
  }

  public RegistrationResponseDTO(String status, String token, String refreshToken) {
    this(status, token, refreshToken, null);
  }
}
