package com.clone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pontusellboj on 2017-01-28.
 */
@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

    // TODO: Block User By ID

    /**
     * Returns a dummy string
     *
     * Test for users logged in as admins
     *
     * @return String
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "Only admins can se me...";
    }
}
