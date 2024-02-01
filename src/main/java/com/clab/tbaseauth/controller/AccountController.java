package com.clab.tbaseauth.controller;

import com.clab.tbaseauth.model.RegistrationStatus;
import com.clab.tbaseauth.model.dto.RegistrationRequestDTO;
import com.clab.tbaseauth.model.dto.RegistrationResponseDTO;
import com.clab.tbaseauth.model.exception.RegistrationRequestValidationException;
import com.clab.tbaseauth.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  // test api
  // do not exclude from security check
  @RequestMapping("/welcome")
  public String welcome() {
    return "welcome";
  }

  @PostMapping("/register")
  public ResponseEntity<RegistrationResponseDTO> register(
      @Valid @RequestBody RegistrationRequestDTO registrationRequestDTO,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new RegistrationRequestValidationException(bindingResult);
    }

    long newUserId = 123L;
    URI location = URI.create("/api/users/" + newUserId);

    return ResponseEntity.created(location).body(accountService.register(registrationRequestDTO));
  }
}
