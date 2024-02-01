package com.clab.tbaseauth.service;

import com.clab.tbaseauth.model.RegistrationStatus;
import com.clab.tbaseauth.model.dto.RegistrationRequestDTO;
import com.clab.tbaseauth.model.dto.RegistrationResponseDTO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AccountServiceTest {

  private static AccountService accountService;

  private static RegistrationRequestDTO validRequestDTO;

  @BeforeAll
  static void setup() {
    accountService = new AccountService();
    validRequestDTO =
        new RegistrationRequestDTO("priyantha gunasekara", "priyantha.getc@gmail.com", "1q2w3e4r");
  }

  @Test
  void register_should_return_valid_registration_response() {
    RegistrationResponseDTO responseDTO = accountService.register(validRequestDTO);
    assertNotNull(responseDTO, "Response should not be null for a valid request");
    assertEquals(
        RegistrationStatus.created,
        responseDTO.status(),
        "Response should have CREATED status for a valid request");
    assertInstanceOf(
        String.class,
        responseDTO.token(),
        "Token should not be null and should be String type for a valid request");
    assertInstanceOf(
        String.class,
        responseDTO.refreshToken(),
        "Refresh token should not be null and should be String type for a valid request");
  }
}
