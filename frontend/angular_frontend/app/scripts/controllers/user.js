'use strict';

/**
 * @ngdoc function
 * @name twitterCloneApp.controller:UserCtrl
 * @description
 * # UserCtrl
 * Controller of the twitterCloneApp
 */
angular.module('twitterCloneApp')
  .controller('UserCtrl', function($scope, $routeParams, userService, tweetService) {

    /*******************
       Init controller
     *******************/

    $scope.user = {};
    $scope.tweets = [];

    //  getUserAndTweets();

    /*******************
          Functions
     *******************/

    userService.getUser($routeParams.username).then(function(data) {
      if (data !== null) {
        $scope.user = data;
        tweetService.getTweetsByAuthor(data.userId).then(function(data) {
          if (data !== null) {
            $scope.tweets = data;
          } else {
            // TODO: Handle error
          }
        });
      } else {
        // TODO: handle error
      }
    });


  });
