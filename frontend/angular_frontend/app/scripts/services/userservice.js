'use strict';

/**
 * @ngdoc service
 * @name twitterCloneApp.userService
 * @description
 * # userService
 * Factory in the twitterCloneApp.
 */
angular.module('twitterCloneApp')
  .factory('userService', function($http, $sessionStorage, endpointService) {

    return {

      getLoggedInUser: function() {
        return $http({
          url: endpointService.getUrl('getLoggedInUser'),
          method: 'GET'
          // headers: {
          //   'Content-Type': 'application/json',
          //   'Authorization': 'Bearer' + $sessionStorage.token
          // }
        }).then(function successCallback(response) {
          /* Check if there is no return data */
          if (!Object.keys(response.data).length > 0) {
            response.data = null;
          }
          return response.data;
        }, function errorCallback(response) {
          return null;
        });
      },

      getAllUsers: function() {
        return $http({
          url: endpointService.getUrl('getAllUsers'),
          method: 'GET'
        }).then(function successCallback(response) {
          /* Check if there is no return data */
          if (!Object.keys(response.data).length > 0) {
            response.data = null;
          }
          return response.data;
        }, function errorCallback(response) {
          return null;
        });
      },

      getUser: function(username) {
        return $http({
          url: endpointService.getUrl('getUser', null, null, username),
          method: 'GET'
        }).then(function successCallback(response) {
          /* Check if there is no return data */
          if (!Object.keys(response.data).length > 0) {
            response.data = null;
          }
          return response.data;
        }, function errorCallback(response) {
          return null;
        });
      },

      getAllFollowees: function(userId) {
        return $http({
          url: endpointService.getUrl('getAllFollowees', userId),
          method: 'GET'
        }).then(function successCallback(response) {
          /* Check if there is no return data */
          if (!Object.keys(response.data).length > 0) {
            response.data = null;
          }
          return response.data;
        }, function errorCallback(response) {
          return null;
        });
      },

      followUser: function(userId, loggedInUser) {
        return $http({
          url: endpointService.getUrl('followUser', userId, loggedInUser),
          method: 'PUT'
        }).then(function successCallback(response) {
          /* Check if there is no return data */
          if (!Object.keys(response.data).length > 0) {
            response.data = null;
          }
          return response.data;
        }, function errorCallback(response) {
          return null;
        })
      },

      unfollowUser: function(userId, loggedInUser) {
        return $http({
          url: endpointService.getUrl('unfollowUser', userId, loggedInUser),
          method: 'PUT'
        }).then(function successCallback(response) {
          /* Check if right status */
          if (!response.status === 204) {
            response.data = null;
          }
          return response.data;
        }, function errorCallback(response) {
          return null;
        })
      },


    };
  });
