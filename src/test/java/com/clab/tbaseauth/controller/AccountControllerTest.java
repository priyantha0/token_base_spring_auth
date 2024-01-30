package com.clab.tbaseauth.controller;

import com.clab.tbaseauth.model.dto.RegistrationRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;


@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void context_loads() throws Exception {
        assertThat(mockMvc).isNotNull();
        assertThat(objectMapper).isNotNull();

    }

    @Test
    @WithMockUser
    void welcome_should_return_welcome_with_status_ok_for_valid_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/account/welcome"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("welcome"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/welcome")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("welcome"));
    }

    @Test
    void welcome_should_return_status_unauthorized_for_invalid_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/account/welcome"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void register_should_return_status_bad_request_for_empty_request_body() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/register")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser
    void register_should_return_errors_with_status_bad_request_for_invalid_body() throws Exception {
        RegistrationRequestDTO invalidRequestDTO = new RegistrationRequestDTO(
                null,
                "invalid_email",
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/register")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error").isString());
    }


}
