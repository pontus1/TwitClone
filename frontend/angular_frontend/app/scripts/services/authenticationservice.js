'use strict';

/**
 * @ngdoc service
 * @name twitterCloneApp.authenticationService
 * @description
 * # authenticationService
 * Factory in the twitterCloneApp.
 */
angular.module('twitterCloneApp')
  .factory('authenticationService', function () {
    
    var auth = {
      isAuthenticated: false,
      isAdmin: false
    }
    return auth;
    
  }).factory('TokenInterceptor', function($q, $sessionStorage, $location, authenticationService) {
    return {
      /* Request */
      request: function(config) {
        config.headers = config.headers || {};
        if ($sessionStorage.token) {
          config.headers.Authorization = 'Bearer ' + $sessionStorage.token;
        }
        return config;
      },

      /* Request error */
      requestError: function(rejection) {
        return $q.reject(rejection);
      },

      /* Set Authentication.isAuthenticated to true if 200 received */
      response: function(response) {
        if (response !== null && response.status === 200 && $sessionStorage.token && !authenticationService.isAuthenticated) {
          authenticationService.isAuthenticated = true;
        }
        return response || $q.when(response);
      },

      /* Revoke client authentication if 401 is received */
      responseError: function(rejection) {
        if (rejection !== null && rejection.status === 401 && ($sessionStorage.token || authenticationService.isAuthenticated)) {
          delete $sessionStorage.token;
          delete $sessionStorage.loggedin_user_id;
          authenticationService.isAuthenticated = false;

          $location.path('/login');
        }
        return $q.reject(rejection);
      }
    };
  });
