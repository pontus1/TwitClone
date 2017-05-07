package com.clone.controllers;

import com.clone.entities.User;
import com.clone.exceptions.TweetNotFoundException;
import com.clone.exceptions.UserNotFoundException;
import com.clone.repositories.TweetRepository;
import com.clone.repositories.UserRepository;
import com.clone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by pontusellboj on 2017-01-28.
 */
@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * Constructor
     * @param tweetRepository
     * @param userService
     */
    @Autowired
    AdminController(TweetRepository tweetRepository, UserRepository userRepository, UserService userService) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Returns no content
     *
     * Block user specified by id.
     *
     * If no user with specified id exist in database, a UserNotFoundException will be thrown.
     *
     * @param userId
     * @return status 204 (no content)
     * @see UserNotFoundException
     */
    @RequestMapping(value = "/users/{userId}/block", method = PUT)
    public ResponseEntity blockUserById(@PathVariable int userId) {
        validateUser(userId);
       User userToBlock = this.userRepository.findOne(userId);
       this.userService.blockUser(userToBlock);
       return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Returns no content
     *
     * Unblock user specified by id.
     *
     * If no user with specified id exist in database, a UserNotFoundException will be thrown.
     *
     * @param userId
     * @return status 204 (no content)
     * @see UserNotFoundException
     */
    @RequestMapping(value = "/users/{userId}/unblock", method = PUT)
    public ResponseEntity unblockUserById(@PathVariable int userId) {
        validateUser(userId);
        User userToUnblock = this.userRepository.findOne(userId);
        this.userService.unblockUser(userToUnblock);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Returns no content
     *
     * Change authorities to admin for user specified by id
     *
     * @param userId
     * @return status 204 (no content)
     * @see UserNotFoundException
     */
    @RequestMapping(value = "/users/{userId}/make_admin", method = PUT)
    public ResponseEntity makeUserAdmin(@PathVariable int userId) {
        validateUser(userId);
        User user = this.userRepository.findOne(userId);
        this.userService.setUserRole(user, "ADMIN");
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Returns no content
     *
     * Delete tweet specified by id from the database (can't be recreated).
     *
     * If the tweet doesn't exist in database a TweetNotFoundException will be thrown.
     *
     * @param tweetId
     * @return status 204 (no content)
     * @see TweetNotFoundException
     */
    @RequestMapping(value = "/tweets/{tweetId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTweetById(@PathVariable int tweetId) {
        vaidateTweet(tweetId);
        this.tweetRepository.delete(tweetId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
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
     * Check whether a tweet specified by id exist in database, otherwise throw TweetNotFoundException
     * @param tweetId
     * @throws TweetNotFoundException
     */
    private void vaidateTweet(int tweetId) {
        this.tweetRepository.findByMessageId(tweetId).orElseThrow (
                () -> new TweetNotFoundException(tweetId));
    }


}
