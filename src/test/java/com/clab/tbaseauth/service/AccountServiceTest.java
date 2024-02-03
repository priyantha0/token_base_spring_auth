package com.clab.tbaseauth.service;

import com.clab.tbaseauth.model.RegistrationStatus;
import com.clab.tbaseauth.model.User;
import com.clab.tbaseauth.model.dto.RegistrationRequestDTO;
import com.clab.tbaseauth.model.dto.RegistrationResponseDTO;
import static org.junit.jupiter.api.Assertions.*;

import com.clab.tbaseauth.model.exception.UserDataProcessingException;
import com.clab.tbaseauth.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountServiceTest {

  @MockBean private UserRepository userRepository;

  @Autowired private AccountService accountService;

  private static RegistrationRequestDTO validRequestDTO;

  @BeforeAll
  static void setup() {
    validRequestDTO =
        new RegistrationRequestDTO("priyantha gunasekara", "priyantha.getc@gmail.com", "1q2w3e4r");
  }

  @Test
  void register_should_return_valid_registration_response() {
    Mockito.when(userRepository.save(new User())).thenReturn(new User());

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

  //  void register_should_throw_validation_exception_for_reques

  @Test
  void save_user_should_throw_exception_for_invalid_user_data() {
    User savedUserWithoutId = new User(null, "test_user_name", "user@email.com", "encrypted_pwd");

    assertThrowsExactly(
        UserDataProcessingException.class, () -> accountService.saveUser(savedUserWithoutId));
  }
}
