package com.clone.controllers;

import com.clone.entities.Tweet;
import com.clone.entities.User;
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

    //TODO: Handle errors / exceptions

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
        return this.tweetRepository.findOne(tweetId);
    }

    // GET all tweets by author id
    @RequestMapping(value = "/author/{userId}", method = RequestMethod.GET)  // TODO: change to name
    public List<Tweet> getTweetsByUser(@PathVariable int userId) {
        return this.tweetRepository.findByAuthorId(userId);
    }

    // ----- IMPORTANT METHOD ------
    // GET all tweets from followees by follower id
    @RequestMapping(value = "/{followerId}/followees", method = RequestMethod.GET)  // TODO: Change to name
    public List<Tweet> getTweetsByFollowees(@PathVariable int followerId) {
        return this.tweetRepository.findAllByFollowees(followerId);
    }

    // POST, new tweet by logged in user
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Tweet> saveTweet(@RequestBody Tweet tweet) {
        User author = this.userService.getLoggedInUser();
        tweet.setAuthorId(author.getUserId());
        this.tweetRepository.save(tweet);
        return new ResponseEntity<Tweet>(tweet, HttpStatus.CREATED);
    }

    // TODO: Delete tweeet?? Change tweet??
}
