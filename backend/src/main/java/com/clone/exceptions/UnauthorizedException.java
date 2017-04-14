package com.clone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pontusellboj on 2017-04-14.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "You are not authorized")
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(int userId) {
        super("You only have authorization to perform this action for user with id: " + userId);
    }
}
