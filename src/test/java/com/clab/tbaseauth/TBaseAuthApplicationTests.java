package com.clab.tbaseauth;

import com.clab.tbaseauth.repository.UserRepository;
import com.clab.tbaseauth.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TBaseAuthApplicationTests {
  @MockBean private UserRepository userRepository;
  @Autowired private AccountService accountService;

  @Test
  void contextLoads() {}
}
