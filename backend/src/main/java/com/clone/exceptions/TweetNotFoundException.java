package com.clone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Tweet")
public class TweetNotFoundException extends RuntimeException {

    public TweetNotFoundException(int message_id) {
        super("could not find tweet '" + message_id + "'.");
    }
}
