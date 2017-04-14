'use strict';

/**
 * @ngdoc function
 * @name twitterCloneApp.controller:UserCardCtrl
 * @description
 * # PartialsUsercardCtrl
 * Controller of the twitterCloneApp
 */
angular.module('twitterCloneApp')
  .controller('UserCardCtrl', function($scope, $sessionStorage, userService, tweetService) {

    /*******************
       Init controller
     *******************/

    var loggedInUser = {};
    $scope.username = '-';
    $scope.tweets = '-';
    $scope.followees = '-'

    /* Get logged in user before getting other data (that depends on logged in user) */
    userService.getLoggedInUser().then(function(data) {
      if (data !== null) {

        loggedInUser = data;
        $scope.username = data.username;
        $scope.tweets = getNumberOfTweets(loggedInUser.userId);
        $scope.followees = getNumberOfFollowees(loggedInUser.userId);

      } else {
        // TODO: Handle error
      }
    });

    /*******************
          Functions
     *******************/

    /* Get number of tweets posted by logged in user */
    function getNumberOfTweets(userId) {
      tweetService.getTweetsByAuthor(userId).then(function(data) {
        if (data !== null) {
          return data.length;
        } else {
          // TODO: Handle error
          return null;
        }
      });
    };

    /* Get number of users logged in user follows */
    function getNumberOfFollowees(userId) {
      userService.getAllFollowees(userId).then(function(data) {
        if (data !== null) {
          return data.length;
        } else {
          // TODO: Handle error
          return null;
        }
      });
    };

    /*******************
          Watchers
     *******************/

    $scope.$watch(function() {
      return tweetService.getNumberOfTweetsByLoggedInUser();
    }, function(newVal, oldVal) {
      if (newVal !== oldVal) {
        $scope.tweets = newVal;
      }
    });

    $scope.$watch(function() {
      return userService.getNumberOfFolloweesByLoggedInUser();
    }, function(newVal, oldVal) {
      if (newVal !== oldVal) {
        $scope.followees = newVal;
      }
    });

  });
