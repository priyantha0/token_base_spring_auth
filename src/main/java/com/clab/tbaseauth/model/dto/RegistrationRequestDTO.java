package com.clab.tbaseauth.model.dto;

import com.clab.tbaseauth.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegistrationRequestDTO(
    @NotNull String name, @NotNull @Email String email, @NotNull String password) {

  public User toEntity() {
    return new User(null, name, email, password);
  }
}
