package com.clone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such User")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int userId) {
        super("could not find user '" + userId + "'.");
    }
    public UserNotFoundException(String username) {
        super("could not find user '" + username + "'.");
    }
}
