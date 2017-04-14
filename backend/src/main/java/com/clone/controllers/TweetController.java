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

/**
 * Created by pontusellboj on 2017-01-25.
 */
@RestController
@RequestMapping(value = "/api/tweets")
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserService userService;

    @Autowired
    TweetController(TweetRepository tweetRepository, UserService userService) {
        this.tweetRepository = tweetRepository;
        this.userService = userService;
    }

    // GET all tweets from everyone
    @RequestMapping(value = "/all_tweets", method = RequestMethod.GET)
    public List<Tweet> getAllTweets() {
        return this.tweetRepository.findAll();
    }

    // GET tweet by id
    @RequestMapping(value = "/{tweetId}", method = RequestMethod.GET)
    public Tweet getTweetById(@PathVariable int tweetId) {
        vaidateTweet(tweetId);
        return this.tweetRepository.findOne(tweetId);
    }

    // GET all tweets by author id
    @RequestMapping(value = "/author/{userId}", method = RequestMethod.GET)
    public List<Tweet> getTweetsByUser(@PathVariable int userId) {
        return this.tweetRepository.findByAuthorId(userId);
    }

    // ----- IMPORTANT METHOD ------
    // GET all tweets from followees by follower id
    @RequestMapping(value = "/follower/{followerId}", method = RequestMethod.GET)
    public List<Tweet> getTweetsByFollowees(@PathVariable int followerId) {
        List<Tweet> followeeTweets = this.tweetRepository.findAllByFollowees(followerId);
        List<Tweet> ownTweets = this.tweetRepository.findByAuthorId(followerId);
        followeeTweets.addAll(ownTweets);
        return followeeTweets;
    }

    // POST new tweet by logged in user
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Tweet> saveTweet(@RequestBody Tweet tweet) {
        User author = this.userService.getLoggedInUser();
        tweet.setAuthorId(author.getUserId());
        this.tweetRepository.save(tweet);
        return new ResponseEntity<Tweet>(tweet, HttpStatus.CREATED);
    }

    // DELETE tweet by id (only logged in user = author)
    @RequestMapping(value = "/{tweetId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTweetById(@PathVariable int tweetId) {
        vaidateTweet(tweetId);
        int authorId = this.tweetRepository.getOne(tweetId).getAuthorId();
        authorizeUser(authorId);
        this.tweetRepository.delete(tweetId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // TODO: Update tweet??


    // Check if a tweet exists, otherwise throw exception
    private void vaidateTweet(int tweetId) {
        this.tweetRepository.findByMessageId(tweetId).orElseThrow (
                () -> new TweetNotFoundException(tweetId));
    }

    // Check if requested by logged in user, otherwise throw exception
    private void authorizeUser(int userId) {
        if (!(this.userService.getLoggedInUser().getUserId() == userId)) {
            throw new UnauthorizedException(userId);
        }
    }



}
