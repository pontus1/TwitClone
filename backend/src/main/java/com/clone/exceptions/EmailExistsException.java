package com.clone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pontusellboj on 2017-02-12.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Email already taken")
public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String email) {
        super("An account with this email address already exist: "  + email);
    }
}
