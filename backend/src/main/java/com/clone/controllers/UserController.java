package com.clone.controllers;

import com.clone.entities.User;
import com.clone.entities.UserRole;
import com.clone.exceptions.UserNotFoundException;
import com.clone.repositories.UserRepository;
import com.clone.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    //TODO: Handle errors
    // TODO: findByUserId() to be able to throw exception if not found

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    UserController(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @RequestMapping(value = "/loggedInUser", method = RequestMethod.GET)
    public String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.toString();
        return currentPrincipalName;
    }

    // GET all users
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    // GET user by id
    @RequestMapping(value = "/admin/user/{userId}", method = RequestMethod.GET)  // TODO: Only allow admin
    public User getUserById(@PathVariable int userId) {
        User user = this.userRepository.findOne(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        return user;
    }

    @RequestMapping(value = "/name/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }
        return user;
    }

    // GET all followers of user by id
    @RequestMapping(value = "/{userId}/followers", method = RequestMethod.GET)  // TODO: Change to name
    public List<User> getAllFollowersOf(@PathVariable int userId) {
        return this.userRepository.findAllFollowersOf(userId);
    }

    // GET all followees of user by id
    @RequestMapping(value = "/{userId}/followees", method = RequestMethod.GET)  // TODO: Change to name
    public List<User> getAllFolloweesOf(@PathVariable int userId) {
        return this.userRepository.findAllFolloweesOf(userId);
    }

    // PUT, update user by id
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)            // TODO: Only allow loged in user
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User updatedUser) {
        User user = this.userRepository.findOne(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        updatePropertiesFoundInRequestBody(updatedUser, user);
        this.userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // DELETE user by id
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)     // TODO: Only allow admin
    public ResponseEntity deleteUserById(@PathVariable int userId) {
        this.userRepository.delete(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // Update only not-null values
    private void updatePropertiesFoundInRequestBody(@RequestBody User updatedUser, User user) {
        if (updatedUser.getUsername() != null) user.setUsername(updatedUser.getUsername());
        if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null) user.setPassword(updatedUser.getPassword());
    }

    // TODO : use method!
    private void validateUser(int userId) {
        this.userRepository.findByUserId(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }

}
