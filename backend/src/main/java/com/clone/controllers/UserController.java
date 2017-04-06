package com.clone.controllers;

import com.clone.entities.Follow;
import com.clone.entities.User;
import com.clone.entities.UserRole;
import com.clone.exceptions.UserNotFoundException;
import com.clone.repositories.FollowRepository;
import com.clone.repositories.UserRepository;
import com.clone.repositories.UserRoleRepository;
import com.clone.services.UserService;
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
    private final FollowRepository followRepository;
    private final UserService userService;

    @Autowired
    UserController(UserRepository userRepository, UserRoleRepository userRoleRepository, FollowRepository followRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.followRepository = followRepository;
        this.userService = userService;
    }

    // Get currently logged in user
    @RequestMapping(value = "/loggedInUser", method = RequestMethod.GET)
    public User getLoggedInUser() {
        User loggedInUser = userService.getLoggedInUser();
        return loggedInUser;
    }

    // GET all users
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    // GET user by id
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)  // TODO: Only allow admin
    public User getUserById(@PathVariable int userId) {
        validateUser(userId);
        User user = this.userRepository.findOne(userId);
        return user;
    }

    /* GET user by username */
    @RequestMapping(value = "/name/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username) {
        User user = this.userRepository.findByUsername(username);
        validateUser(user.getUserId());
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
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)            // TODO: Only allow logged in user
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User updatedUser) {
        validateUser(userId);
        User user = this.userRepository.findOne(userId);
        updatePropertiesFoundInRequestBody(updatedUser, user);
        this.userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // DELETE user by id
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)     // TODO: Only allow admin (and logged in user)
    public ResponseEntity deleteUserById(@PathVariable int userId) {
        this.userRepository.delete(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // PUT, add user to followees (follow a user)
    @RequestMapping(value = "/{followerId}/follow/{followeeId}", method = RequestMethod.PUT)
    public ResponseEntity followUser(@PathVariable("followerId") int followerId, @PathVariable("followeeId") int followeeId) {

        boolean requestedByLoggedInUser = (userService.getLoggedInUser().getUserId() == followerId);
        validateUser(followeeId);

        if (!requestedByLoggedInUser) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED); // TODO: create error for unauthorized requests
        }
        Follow follow = createNewFollow(followerId, followeeId);
        return new ResponseEntity<Follow>(follow, HttpStatus.OK);
    }

    // PUT, remove user from followees (unfollow a user)
    @RequestMapping(value = "/{followerId}/unfollow/{followeeId}", method = RequestMethod.PUT)
    public ResponseEntity unfollowUser(@PathVariable("followerId") int followerId, @PathVariable("followeeId") int followeeId) {

        boolean requestedByLoggedInUser = (userService.getLoggedInUser().getUserId() == followerId);
        validateUser(followeeId);

        if (!requestedByLoggedInUser) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED); // TODO: create error for unauthorized requests
        }
        Follow follow = this.followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId);
        if (follow != null) {
            this.followRepository.delete(follow);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // Create new follow and save it
    private Follow createNewFollow(int followerId, int followeeId) {
        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setFolloweeId(followeeId);
        this.followRepository.save(follow);
        return follow;
    }


    // Update only not-null values
    private void updatePropertiesFoundInRequestBody(@RequestBody User updatedUser, User user) {
        if (updatedUser.getUsername() != null) user.setUsername(updatedUser.getUsername());
        if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null) user.setPassword(updatedUser.getPassword());
    }



    // TODO : use method!
    // Check if a user exists, otherwise throw exception
    private void validateUser(int userId) {
        this.userRepository.findByUserId(userId).orElseThrow (
                () -> new UserNotFoundException(userId));
    }

}
