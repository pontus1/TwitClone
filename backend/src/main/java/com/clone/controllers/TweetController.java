package com.clone.controllers;

import com.clone.entities.Tweet;
import com.clone.entities.User;
import com.clone.exceptions.TweetNotFoundException;
import com.clone.exceptions.UnauthorizedException;
import com.clone.repositories.TweetRepository;
import com.clone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Update tweet

/**
 * Created by pontusellboj on 2017-01-25.
 */
@RestController
@RequestMapping(value = "/api/tweets")
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserService userService;

    /**
     * Constructor
     * @param tweetRepository
     * @param userService
     */
    @Autowired
    TweetController(TweetRepository tweetRepository, UserService userService) {
        this.tweetRepository = tweetRepository;
        this.userService = userService;
    }

    /**
     * Returns all tweets in database
     * @return status 200 and all tweets (from everyone)
     */
    @RequestMapping(value = "/all_tweets", method = RequestMethod.GET)
    public List<Tweet> getAllTweets() {
        return this.tweetRepository.findAll();
    }

    /**
     * Returns a tweet with specified id.
     *
     * If no tweet with specified id exist in database, a TweetNotFoundException will be thrown.
     *
     * @param tweetId
     * @return status 200 and the tweet with specified id
     * @see TweetNotFoundException
     */
    @RequestMapping(value = "/{tweetId}", method = RequestMethod.GET)
    public Tweet getTweetById(@PathVariable int tweetId) {
        vaidateTweet(tweetId);
        return this.tweetRepository.findOne(tweetId);
    }

    /**
     * Returns all tweets written by user specified by id.
     *
     * If no user exist with specified id, no content will be returned
     *
     * @param userId
     * @return status 200 and all tweets written by user specified with id
     */
    @RequestMapping(value = "/author/{userId}", method = RequestMethod.GET)
    public List<Tweet> getTweetsByUser(@PathVariable int userId) {
        return this.tweetRepository.findByAuthorId(userId);
    }

    /**
     * Returns all tweets posted by followees of user specified by.
     *
     * All tweets written by user + all tweets written by users that this user is following will be returned
     *
     * @param followerId
     * @return status 200 and all tweets written by
     */
    @RequestMapping(value = "/follower/{followerId}", method = RequestMethod.GET)
    public List<Tweet> getTweetsByFollowees(@PathVariable int followerId) {
        List<Tweet> followeeTweets = this.tweetRepository.findAllByFollowees(followerId);
        List<Tweet> ownTweets = this.tweetRepository.findByAuthorId(followerId);
        followeeTweets.addAll(ownTweets);
        return followeeTweets;
    }

    /**
     * Returns the created tweet
     *
     * This will create a new tweet with content specified in the request.
     * The tweet param should only have one key-value pair - content - e.g:
     *
     *  { "content": "This is an example text" }
     *
     * The following will be created automatically:
     * authorId - will be the same as currently logged in users id
     * messageId - last posted tweet + 1
     * pubDate - the time of creation (as Timestamp)
     *
     * @param tweet
     * @return status 201 and created tweet
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Tweet> saveTweet(@RequestBody Tweet tweet) {
        User author = this.userService.getLoggedInUser();
        tweet.setAuthorId(author.getUserId());
        this.tweetRepository.save(tweet);
        return new ResponseEntity<Tweet>(tweet, HttpStatus.CREATED);
    }

    /**
     * Returns no content
     *
     * Deletes tweet specified by id from the database (can't be recreated).
     *
     * If the tweet doesn't exist in database a TweetNotFoundException will be thrown.
     * If this method is called by anyone other than the author of the tweet, an UnauthorizedException will be thrown.
     *
     *
     * @param tweetId
     * @return status 204 (no content)
     * @see TweetNotFoundException
     * @see UnauthorizedException
     */
    @RequestMapping(value = "/{tweetId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTweetById(@PathVariable int tweetId) {
        vaidateTweet(tweetId);
        int authorId = this.tweetRepository.getOne(tweetId).getAuthorId();
        authorizeUser(authorId);
        this.tweetRepository.delete(tweetId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
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
