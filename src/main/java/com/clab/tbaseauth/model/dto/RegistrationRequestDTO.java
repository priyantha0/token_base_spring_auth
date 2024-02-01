package com.clab.tbaseauth.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegistrationRequestDTO(
    @NotNull String name, @NotNull @Email String email, @NotNull String password) {}
