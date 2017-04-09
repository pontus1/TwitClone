'use strict';

/**
 * @ngdoc function
 * @name twitterCloneApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the twitterCloneApp
 */
angular.module('twitterCloneApp')
  .controller('MainCtrl', ['$scope', '$sessionStorage', '$location', 'authenticationService', 'utilityService',
    function($scope, $sessionStorage, $location, authenticationService, utilityService) {

      /*******************
         Init controller
       *******************/

      if ($sessionStorage.logged_in_user !== undefined) {
        $scope.loggedInUserName = $sessionStorage.logged_in_user.username;
      }

      /*******************
          Load partials
       *******************/

      /* Load partial view - menu */
      $scope.usercard = utilityService.getPartialView('usercard');

      /*******************
            Watchers
       *******************/

      /* Listen to emit from LoginCtrl when logged in user changes */
      $scope.$on('changeLoggedInUsername', function(event, data) {
        $scope.loggedInUserName = data;
      });

      /*******************
            Functions
       *******************/

      /* Check if logged out */
      $scope.isLoggedOut = function isLoggedOut() {
        if (!authenticationService.isAuthenticated && $sessionStorage.token === undefined) {
          return true;
        } else {
          return false;
        }
      };

      /* Log out */
      $scope.logOut = function() {
        /* Set authentication for the user to false */
        authenticationService.isAuthenticated = false;

        /* Reroute to default route (login) */
        $location.path('/');

        /* Reset sessionstorage */
        $sessionStorage.$reset();
      };

    }
  ]);
