'use strict';

/**
 * @ngdoc service
 * @name twitterCloneApp.loginService
 * @description
 * # loginService
 * Factory in the twitterCloneApp.
 */
 angular.module('twitterCloneApp')
 .factory('loginService', function ($sessionStorage, $http, endpointService) {

  return {
    /* Sign in */
    signIn: function(username, password) {
      console.log(username, password);
      return $http({
        url: endpointService.getUrl('signIn'),
        method: 'POST',
        xhrFields: {
          withCredentials: false
        },
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          'Authorization': 'Basic dHdpdHRlci1jbG9uZS1jbGllbnQ6MTIzNDU2'
        },
        data: $.param({
          client_id: 'twitter-clone-client',
          client_secret: '123456',
          grant_type: 'password',
          username: username,
          password: password
        })
      }).then(function successCallback(response) {
        /* Check if there is no retrun data */
        if (!Object.keys(response.data).length > 0) {
          response.data = null;
        }
        return response.data;
      }, function errorCallback(response) {
        return null;
      });
    },

    /* Refresh token */
    refreshToken: function() {
      return $http({
        url: endpointService.getUrl('signIn'),
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: $.param({
          client_id: '1_2o8itx4l34cg44g4sgcc8o4ogokckcoggkocgcws448kgkco4o',
          client_secret: '1os5nrb31a74k08c848kkkc8wo84s8sk4wsokw808o8o0gwokc',
          grant_type: 'refresh_token',
          refresh_token: $sessionStorage.refresh_token
        })
      });
    }
  };
});
