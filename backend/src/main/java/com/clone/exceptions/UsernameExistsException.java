package com.clone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pontusellboj on 2017-02-12.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username already taken")
public class UsernameExistsException extends RuntimeException {

    public UsernameExistsException(String username) {
        super("There is an account with that username: "  + username);
    }
}
