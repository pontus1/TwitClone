'use strict';

/**
 * @ngdoc function
 * @name twitterCloneApp.controller:HomeCtrl
 * @description
 * # HomeCtrl
 * Controller of the twitterCloneApp
 */
angular.module('twitterCloneApp')
  .controller('HomeCtrl', function($scope, $sessionStorage, LoggedInUser, tweetService) {

    /*******************
       Init controller
     *******************/

    $scope.loggedInUserId = LoggedInUser.userId;

    $scope.tweets = [];
    $scope.lastTweet = '';

    getTweets();

    /*******************
          Watchers
     *******************/

    /* If a new tweet is beeing posted, get tweets */
    $scope.$watch('lastTweet', function(newVal, oldVal) {
      if (newVal !== oldVal) {
        getTweets();
      }
    });

    /*******************
          Functions
     *******************/

    /* Get all tweets from users followed by logged in user */
    function getTweets() {
      tweetService.getTweetsByFollowee($scope.loggedInUserId).then(function(data) {
        if (data !== null) {
          $scope.tweets = data;
        } else {
          $scope.tweets = [];
        }
      });
    };

    /* Delete tweet by id */
    $scope.deleteTweet = function(tweetId) {
      console.log('tweet: ' + tweetId)
      tweetService.deleteTweet(tweetId).then(function(data) {
        if (data !== null) {
          getTweets();
        } else {
          // TODO: Handle error
        }
      })
    };




    /* Get all tweets from everyone */
    // tweetService.getAllTweets().then(function(data) {
    //   if (data === null) {
    //     $scope.tweets = [];
    //   } else {
    //     $scope.tweets = data;
    //   }
    // });


  });
