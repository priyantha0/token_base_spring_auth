package com.clab.tbaseauth.controller;

import com.clab.tbaseauth.model.dto.RegistrationRequestDTO;
import com.clab.tbaseauth.model.exception.RegistrationRequestInvalidException;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    // test api
    // do not exclude from security check
    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }


    @PostMapping("/register")
    public String register(@Valid @RequestBody RegistrationRequestDTO registrationRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RegistrationRequestInvalidException(bindingResult);
        }

        return "OK";
    }
}
