package com.clab.tbaseauth.model.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.text.MessageFormat;
import java.util.List;

public class RegistrationRequestInvalidException extends RuntimeException {

    private final transient BindingResult errors;

    public RegistrationRequestInvalidException(BindingResult bindingResult) {
        errors = bindingResult;
    }

    @Override
    public String getMessage() {
        return String.join(",", getMessages());
    }

    public List<String> getMessages() {
        return getValidationMessage(this.errors);
    }

    private List<String> getValidationMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(RegistrationRequestInvalidException::getValidationMessage)
                .toList();
    }

    private static String getValidationMessage(ObjectError error) {
        boolean isNonFieldError = !(error instanceof FieldError);

        if (isNonFieldError) {
            return MessageFormat.format("{0}, {1}", error.getObjectName(), error.getDefaultMessage());
        }

        FieldError fieldError = (FieldError) error;

        String className = fieldError.getObjectName();
        String property = fieldError.getField();
        Object invalidValue = fieldError.getRejectedValue();
        String message = fieldError.getDefaultMessage();

        return MessageFormat.format("{0}.{1} {2}, but was {3} ",
                className, property, message, invalidValue);

    }
}
