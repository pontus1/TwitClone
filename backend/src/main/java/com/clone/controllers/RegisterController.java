package com.clone.controllers;

import com.clone.entities.User;
import com.clone.exceptions.EmailExistsException;
import com.clone.exceptions.UsernameExistsException;
import com.clone.repositories.UserRepository;
import com.clone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@RestController
@RequestMapping(value = "/register")
public class RegisterController {

    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * Constructor
     * @param userRepository
     * @param userService
     */
    @Autowired
    RegisterController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Returns the created user
     *
     * This will create a new user with content specified in the request.
     *
     * The user param should have the following key-value pairs:
     * - username
     * - email
     * - password
     *
     * e.g: { "username": "John Doe", "email": "john.doe@example.com", "password": "password" }
     *
     * The following will be created automatically:
     * userId - last registered user + 1
     * enabled - 1 (not blocked)
     * userRole - an object containing the user role (the role will be set to "user", not "admin")
     * followers - null
     * followees - null
     * tweets - null
     *
     * If a user is already registered with the email being sent in the request an EmailExistsException will be thrown
     * If a user is already registered with the username being sent in the request a UsernameExistsException will be thrown
     *
     * @param user
     * @return status 201 and created user
     * @throws EmailExistsException
     * @throws UsernameExistsException
     */
    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.POST)           
    public ResponseEntity<User> registerUser(@RequestBody User user) throws EmailExistsException, UsernameExistsException {

        if(emailExist(user.getEmail())) {
            throw new EmailExistsException(user.getEmail());
        }

        if(usernameExist(user.getUsername())) {
            throw new UsernameExistsException(user.getUsername());
        }

        user.setEnabled((byte) 1);  // TODO: Check why enabled = 0 by default
        this.userRepository.save(user);
        User newUser = this.userRepository.findByUsername(user.getUsername());
        this.userService.setUserRole(newUser, "USER");

        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    /**
     * Returns true if a user in database already has the specified email, otherwise false.
     *
     * @param email
     * @return boolean
     */
    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if a user in database already has the specified username, otherwise false.
     *
     * @param username
     * @return boolean
     */
    private boolean usernameExist(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return true;
        }
        return false;
    }
}

