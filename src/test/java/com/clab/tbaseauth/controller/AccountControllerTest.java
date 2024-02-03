package com.clab.tbaseauth.controller;

import com.clab.tbaseauth.model.RegistrationStatus;
import com.clab.tbaseauth.model.dto.RegistrationRequestDTO;
import com.clab.tbaseauth.model.dto.RegistrationResponseDTO;
import com.clab.tbaseauth.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private AccountService accountService;
  @Autowired private ObjectMapper objectMapper;

  private static final String WELCOME_ENDPOINT = "/api/accounts/welcome";
  private static final String REGISTER_ENDPOINT = "/api/accounts/register";
  private static RegistrationRequestDTO validRequestDTO;
  private static RegistrationRequestDTO invalidRequestDTO;
  private static RegistrationResponseDTO validResponseDTO;

  @BeforeAll
  static void setup() {
    validRequestDTO =
        new RegistrationRequestDTO("priyantha gunasekara", "priyantha.getc@gmail.com", "1q2w3e4r");

    invalidRequestDTO = new RegistrationRequestDTO(null, "invalid_email", null);
    validResponseDTO =
        new RegistrationResponseDTO(
            RegistrationStatus.CREATED, "sample-toke", "sample-refresh-token");
  }

  @Test
  void context_loads() throws Exception {
    assertThat(mockMvc).isNotNull();
    assertThat(objectMapper).isNotNull();
  }

  @Test
  @WithMockUser
  void welcome_should_return_welcome_with_status_ok_for_valid_user() throws Exception {
    mockMvc
        .perform(get(WELCOME_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(content().string("welcome"));

    mockMvc
        .perform(post(WELCOME_ENDPOINT).with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().string("welcome"));
  }

  @Test
  void welcome_should_return_status_unauthorized_for_invalid_user() throws Exception {
    mockMvc.perform(get(WELCOME_ENDPOINT)).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  void register_should_return_status_bad_request_for_empty_request_body() throws Exception {
    mockMvc.perform(post(REGISTER_ENDPOINT).with(csrf())).andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser
  void register_should_return_errors_with_status_bad_request_for_invalid_body() throws Exception {
    mockMvc
        .perform(
            post(REGISTER_ENDPOINT)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequestDTO)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors").exists())
        .andExpect(jsonPath("$.errors").isArray());
  }

  @Test
  void register_should_not_be_protected_or_blocked() throws Exception {
    mockMvc
        .perform(
            post(REGISTER_ENDPOINT)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(objectMapper.writeValueAsString(validRequestDTO)))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void register_should_return_valid_resp_with_status_created_for_valid_request() throws Exception {

    when(accountService.register(validRequestDTO)).thenReturn(validResponseDTO);

    mockMvc
        .perform(
            post(REGISTER_ENDPOINT)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequestDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.status", Matchers.is(RegistrationStatus.CREATED)))
        .andExpect(jsonPath("$.token", Matchers.not(Matchers.emptyOrNullString())))
        .andExpect(jsonPath("$.refreshToken", Matchers.not(Matchers.emptyOrNullString())));
  }
}
