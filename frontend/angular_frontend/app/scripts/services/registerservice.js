'use strict';

/**
 * @ngdoc service
 * @name twitterCloneApp.registerService
 * @description
 * # registerService
 * Factory in the twitterCloneApp.
 */
angular.module('twitterCloneApp')
  .factory('registerService', function($http, endpointService) {

    return {

      registerUser: function(username, email, password) {
        return $http({
          url: endpointService.getUrl('registerUser'),
          method: 'POST',
          data: {
            username: username,
            email: email,
            password: password
          }
        }).then(function successCallback(response) {
          /* Check if there is no return data */
          // if (!Object.keys(response.data).length > 0) {
          //   response.data = null;
          // }
          console.log('in service: ' + response)
          return response;
        }, function errorCallback(response) {
          console.log('in service error: ' + response)
          return response;
        })
      }

    };
  });
