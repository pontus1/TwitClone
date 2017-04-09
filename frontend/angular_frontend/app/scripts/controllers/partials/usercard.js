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

    var loggedInUserId = $sessionStorage.logged_in_user.userId;

    $scope.tweets = getNumberOfTweets();
    // $scope.followees = getNumberOfFollowees();

    /*******************
          Functions
     *******************/

    /* Get number of tweets posted by logged in user */
    function getNumberOfTweets() {
      tweetService.getTweetsByAuthor(loggedInUserId).then(function(data) {
        if (data !== null) {
          return data.length;
        } else {
          // TODO: Handle error
          return null;
        }
      });
    };

    /* Get number of users logged in user follows */
    // function getNumberOfFollowees() {
    //   userService.getAllFollowees(loggedInUserId).then(function(data) {
    //     if (data !== null) {
    //       return data.length;
    //     } else {
    //       // TODO: Handle error
    //       return null;
    //     }
    //   });
    // };

    /*******************
          Watchers
     *******************/

    $scope.$watch(function() {
      return tweetService.getNumberOfTweetsByLoggedInUser();
    }, function(newVal, oldVal) {
      // if (newVal !== oldVal) {
        console.log("isLoggedIn changed to " + newVal);
        $scope.tweets = newVal;
      // }
    });

  });
