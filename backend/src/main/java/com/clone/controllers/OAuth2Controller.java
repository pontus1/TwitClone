package com.clone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@Controller
public class OAuth2Controller {

    @Autowired
    private DefaultTokenServices tokenStore;

    // LOGOUT  // TODO: Return proper HTTP-message
    @RequestMapping(value = "/oauth/revoke-token", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            tokenStore.revokeToken(tokenValue);
        }
    }
}
