package com.clab.tbaseauth.service;

import com.clab.tbaseauth.model.RegistrationStatus;
import com.clab.tbaseauth.model.User;
import com.clab.tbaseauth.model.dto.RegistrationRequestDTO;
import com.clab.tbaseauth.model.dto.RegistrationResponseDTO;
import com.clab.tbaseauth.model.exception.UserDataProcessingException;
import com.clab.tbaseauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

import static com.clab.tbaseauth.model.constants.UserHandlingExceptionConstants.*;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final UserRepository userRepository;

  public RegistrationResponseDTO register(RegistrationRequestDTO validRequestDTO) {
    User userToSave = validRequestDTO.toEntity();
    userToSave.setId(UUID.randomUUID().toString());
    saveUser(userToSave);
    return new RegistrationResponseDTO(RegistrationStatus.CREATED, "ttt", "rtt");
  }

  public User saveUser(User user) {
    validateUserDataBeforeSaving(user);
    return userRepository.save(user);
  }

  private void validateUserDataBeforeSaving(User user) {
    if (ObjectUtils.isEmpty(user)) {
      throw new UserDataProcessingException(ERROR_NULL_OBJECT_TO_SAVE);
    }

    if (ObjectUtils.isEmpty(user.getId())) {
      throw new UserDataProcessingException(ERROR_USER_ID_IS_MISSING);
    }
  }
}
