package com.clab.tbaseauth.service;

import com.clab.tbaseauth.model.RegistrationStatus;
import com.clab.tbaseauth.model.dto.RegistrationRequestDTO;
import com.clab.tbaseauth.model.dto.RegistrationResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
  public RegistrationResponseDTO register(RegistrationRequestDTO validRequestDTO) {
    return new RegistrationResponseDTO(RegistrationStatus.created, "ttt", "rtt");
  }
}
