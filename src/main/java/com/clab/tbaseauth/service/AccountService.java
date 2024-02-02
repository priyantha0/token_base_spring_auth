package com.clab.tbaseauth.service;

import com.clab.tbaseauth.model.RegistrationStatus;
import com.clab.tbaseauth.model.User;
import com.clab.tbaseauth.model.constants.UserHandlingExceptionConstants;
import com.clab.tbaseauth.model.dto.RegistrationRequestDTO;
import com.clab.tbaseauth.model.dto.RegistrationResponseDTO;
import com.clab.tbaseauth.model.exception.UserHandlingException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

import static com.clab.tbaseauth.model.constants.UserHandlingExceptionConstants.*;

@Service
public class AccountService {
  public RegistrationResponseDTO register(RegistrationRequestDTO validRequestDTO) {
    saveUser(validRequestDTO.toEntity());
    return new RegistrationResponseDTO(RegistrationStatus.created, "ttt", "rtt");
  }

  public User saveUser(User user) {

    if (ObjectUtils.isEmpty(user)) {
      throw new UserHandlingException(ERROR_NULL_OBJECT_TO_SAVE);
    }

    if (!ObjectUtils.isEmpty(user.getId())) {
      throw new UserHandlingException(ERROR_OBJECT_WITH_ID_TO_SAVE);
    }

    user.setId(UUID.randomUUID().toString());
    return user;
  }
}
