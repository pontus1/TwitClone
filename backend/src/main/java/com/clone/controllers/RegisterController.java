package com.clone.controllers;

import com.clone.entities.User;
import com.clone.entities.UserRole;
import com.clone.exceptions.EmailExistsException;
import com.clone.exceptions.UsernameExistsException;
import com.clone.repositories.UserRepository;
import com.clone.repositories.UserRoleRepository;
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

    @Autowired
    RegisterController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @Transactional //????
    @RequestMapping(value = "/", method = RequestMethod.POST)           
    public ResponseEntity<User> registerUser /* saveUser */ (@RequestBody User user) throws EmailExistsException, UsernameExistsException {

        // TODO:
        // Kolla att email följer regex pattern
        // Kolla att email inte redan finns
        // Kolla att username inte redan finns
        // Kolla att username följer regex pattern
        // Kolla att lösenord följer regex pattern
        // Create validation service????

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


    // Check if email exsist
    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    // Check if username exsist
    private boolean usernameExist(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return true;
        }
        return false;
    }
}

