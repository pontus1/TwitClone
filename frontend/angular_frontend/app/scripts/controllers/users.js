'use strict';

/**
 * @ngdoc function
 * @name twitterCloneApp.controller:UsersCtrl
 * @description
 * # UsersCtrl
 * Controller of the twitterCloneApp
 */
angular.module('twitterCloneApp')
  .controller('UsersCtrl', function($scope, $sessionStorage, $location, userService) {

    /*******************
       Init controller
     *******************/

    var loggedInUser = $sessionStorage.logged_in_user.userId;
    $scope.allUsers = [];

    getAllUsers();

    /*******************
          Functions
     *******************/

    /* Get all users */
    function getAllUsers() {
      userService.getAllUsers().then(function(data) {
        if (data !== null) {
          $scope.allUsers = appendFollowedAttribute(data);
        } else {
          $scope.allUsers = [];
        }
      });
    };

    /* append followed attribute to each user (true if logged in user follow the user)*/
    function appendFollowedAttribute(users) {
      for (var i = 0; i < users.length; i++) {
        users[i].followed = false;
        if (users[i].followers.length > 0) {
          for (var j = 0; j < users[i].followers.length; j++) {
            if (users[i].followers[j].followerId === loggedInUser) {
              users[i].followed = true;
            }
          }
        }
      }
      return users;
    };

    /* follow user by id */
    function followUser(userId) {
      userService.followUser(userId, loggedInUser).then(function(data) {
        if (data !== null) {
          getAllUsers();
        } else {
          // TODO: handle error
        }
      })
    };

    /* unfollow user by id */
    function unfollowUser(userId) {
      userService.unfollowUser(userId, loggedInUser).then(function(data) {
        if (data !== null) {
          getAllUsers();
        } else {
          // TODO: handle error
        }
      })
    };

    /******************
      Scope  Functions
    *******************/

    /* Toggle follow (follow/unfollow user) */
    $scope.toggleFollow = function(index, userId) {
      if ($scope.allUsers[index].followed) {
        unfollowUser(userId);
      } else {
        followUser(userId);
      }
    };

    /* Route to users profile */
    $scope.goToUserProfile = function(username) {
      $location.path("/user/" + username);
    };

  });
