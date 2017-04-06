'use strict';

/**
 * @ngdoc function
 * @name twitterCloneApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the twitterCloneApp
 */
angular.module('twitterCloneApp')
  .controller('LoginCtrl', function($scope, $location, loginService, userService, $sessionStorage) {

    /*******************
          Functions
     *******************/

    $scope.signIn = function signIn(username, password) {
      if ((username !== null && password !== null) && (username !== '' && password !== '')) {
        loginService.signIn(username, password).then(function(data) {
          if (data !== null) { // TODO: Check what is returned on error

            /* Save tokens in session storage */
            $sessionStorage.token = data.access_token;
            $sessionStorage.refresh_token = data.refresh_token;

            $scope.setLoggedInUser();
            /* Reroute to default route */
            $location.path('/home');
          } else {
            // TODO: Handle error
            console.log('error logging in');
          }
        });
      }
    };

    $scope.setLoggedInUser = function() {
      userService.getLoggedInUser().then(function(data) {
        if (data !== null) {
          $sessionStorage.logged_in_user = data;
          changeLoggedInUsername(data.username);
        } else {
          // TODO: Handle error
          console.log('error getting logged in user');
        }
      });
    };

    /*******************
          Emitters
     *******************/

    function changeLoggedInUsername(username) {
      $scope.$emit('changeLoggedInUsername', username);
    }


  });
