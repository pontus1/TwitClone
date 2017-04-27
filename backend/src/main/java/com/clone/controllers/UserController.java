package com.clone.controllers;

import com.clone.entities.Follow;
import com.clone.entities.User;
import com.clone.exceptions.UnauthorizedException;
import com.clone.exceptions.UserNotFoundException;
import com.clone.repositories.FollowRepository;
import com.clone.repositories.UserRepository;
import com.clone.repositories.UserRoleRepository;
import com.clone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final FollowRepository followRepository;
    private final UserService userService;

    /**
     * Constructor
     * @param userRepository
     * @param userRoleRepository
     * @param followRepository
     * @param userService
     */
    @Autowired
    UserController(UserRepository userRepository, UserRoleRepository userRoleRepository, FollowRepository followRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.followRepository = followRepository;
        this.userService = userService;
    }

    /**
     * Returns the currently logged in user
     * @return status 200 and the logged in user
     */
    @RequestMapping(value = "/loggedInUser", method = RequestMethod.GET)
    public User getLoggedInUser() {
        User loggedInUser = userService.getLoggedInUser();
        return loggedInUser;
    }

    /**
     * Returns all users in database
     * @return status 200 and all users
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    /**
     * Returns a user with specified id.
     *
     * If no user with specified id exist in database, a UserNotFoundException will be thrown.
     *
     * @param userId
     * @return status 200 and the user with specified id
     * @see UserNotFoundException
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int userId) {
        validateUser(userId);
        User user = this.userRepository.findOne(userId);
        return user;
    }

    /**
     * Returns a user with specified username.
     *
     * If no user with specified username exist in database, a UserNotFoundException will be thrown.
     *
     * @param username
     * @return status 200 and the user with specified username
     * @see UserNotFoundException
     */
    @RequestMapping(value = "/name/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username) {
        User user = this.userRepository.findByUsername(username);  // TODO: Handle propperly
        validateUser(user.getUserId());
        return user;
    }

    /**
     * Returns all users following specified user.
     *
     * If no user with specified id exist in database, a UserNotFoundException will be thrown.
     *
     * @param userId
     * @return status 200 and followers of user with specified id
     * @see UserNotFoundException
     */
    @RequestMapping(value = "/{userId}/followers", method = RequestMethod.GET)
    public List<User> getAllFollowersOf(@PathVariable int userId) {
        validateUser(userId);
        return this.userRepository.findAllFollowersOf(userId);
    }

    /**
     * Returns all users specified user is following.
     *
     * If no user with specified id exist in database, a UserNotFoundException will be thrown.
     *
     * @param userId
     * @return status 200 and all users followed by user with specified id
     * @see UserNotFoundException
     */
    @RequestMapping(value = "/{userId}/followees", method = RequestMethod.GET)
    public List<User> getAllFolloweesOf(@PathVariable int userId) {
        validateUser(userId);
        return this.userRepository.findAllFolloweesOf(userId);
    }

    /**
     *  Returns updated user
     *
     *  Update properties on user with specified id. The following properties will be updated if found in request-body:
     *  username
     *  email
     *  password
     *
     *  If no user with specified id exist in database, a UserNotFoundException will be thrown.
     *  If this method is called by anyone other then the user itself, an UnauthorizedException will be thrown.
     *
     * @param userId
     * @param updatedUser
     * @return status 200 and updated user
     * @see UserNotFoundException
     * @see UnauthorizedException
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User updatedUser) {
        validateUser(userId);
        authorizeUser(userId);
        User user = this.userRepository.findOne(userId);
        updatePropertiesFoundInRequestBody(updatedUser, user);
        this.userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * Returns no content
     *
     * Remove user specified by id from database. This is used to unregister.
     *
     * If no user with specified id exist in database, a UserNotFoundException will be thrown.
     * If this method is called by anyone other then the user itself, an UnauthorizedException will be thrown.
     *
     * @param userId
     * @return status 204 (no content)
     * @see UserNotFoundException
     * @see UnauthorizedException
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserById(@PathVariable int userId) {
        validateUser(userId);
        authorizeUser(userId);
        this.userRepository.delete(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Returns newly created follow
     *
     * Make user follow another user (both specified by id). If this is already the case, nothing happens.
     *
     * If either of the specified users doesn't exist in database, a UserNotFoundException will be thrown.
     * If specified follower is anyone other then the user itself, an UnauthorizedException will be thrown.
     *
     * @param followerId the follower
     * @param followeeId the user to follow
     * @return status 200 and follow
     * @see UserNotFoundException
     * @see UnauthorizedException
     */
    @RequestMapping(value = "/{followerId}/follow/{followeeId}", method = RequestMethod.PUT)
    public ResponseEntity followUser(@PathVariable("followerId") int followerId, @PathVariable("followeeId") int followeeId) {

        validateUser(followeeId);
        validateUser(followerId);
        authorizeUser(followerId);

        Follow follow = createNewFollow(followerId, followeeId);
        return new ResponseEntity<Follow>(follow, HttpStatus.OK);
    }

    /**
     * Returns no content
     *
     * Make user unfollow another user (both specified by id). If this is already the case, nothing happens.
     *
     * If either of the specified users doesn't exist in database, a UserNotFoundException will be thrown.
     * If specified follower is anyone other then the user itself, an UnauthorizedException will be thrown.
     *
     * @param followerId
     * @param followeeId
     * @return status 204 (no content)
     * @see UserNotFoundException
     * @see UnauthorizedException
     */
    @RequestMapping(value = "/{followerId}/unfollow/{followeeId}", method = RequestMethod.PUT)
    public ResponseEntity unfollowUser(@PathVariable("followerId") int followerId, @PathVariable("followeeId") int followeeId) {

        validateUser(followeeId);
        validateUser(followerId);
        authorizeUser(followerId);

        Follow follow = this.followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId);
        if (follow != null) {
            this.followRepository.delete(follow);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Returns newly created follow entity
     *
     * Creates a follow entity and join users by adding one of them to the others list of followers
     * and the other to the first ones list of followees (users followed by the first user)
     *
     * @param followerId
     * @param followeeId
     * @return Follow
     */
    private Follow createNewFollow(int followerId, int followeeId) {
        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setFolloweeId(followeeId);
        this.followRepository.save(follow);
        return follow;
    }

    /**
     * Takes two user objects and updates the properties of the second one that differs from the first (unless they have value null).
     * (Only username, email and password can be updated this way).
     *
     * @param updatedUser
     * @param user
     */
    private void updatePropertiesFoundInRequestBody(@RequestBody User updatedUser, User user) {
        if (updatedUser.getUsername() != null) user.setUsername(updatedUser.getUsername());
        if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null) user.setPassword(updatedUser.getPassword());
    }

    /**
     * Check whether a user specified by id exist in database, otherwise throw UserNotFoundException
     * @param userId
     * @throws UserNotFoundException
     */
    private void validateUser(int userId) {
        this.userRepository.findByUserId(userId).orElseThrow (
                () -> new UserNotFoundException(userId));
    }

    /**
     * Check whether user specified by id is the currently logged in user, otherwise throw UnauthorizedException
     * @param userId
     * @throws UnauthorizedException
     */
    private void authorizeUser(int userId) {
        if (!(this.userService.getLoggedInUser().getUserId() == userId)) {
            throw new UnauthorizedException(userId);
        }
    }

}
