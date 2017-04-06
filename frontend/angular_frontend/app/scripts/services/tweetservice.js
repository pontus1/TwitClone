'use strict';

/**
 * @ngdoc service
 * @name twitterCloneApp.tweetService
 * @description
 * # tweetService
 * Factory in the twitterCloneApp.
 */
angular.module('twitterCloneApp')
  .factory('tweetService', function($http, $sessionStorage, endpointService) {

    return {
      /* Get all tweets from followees (the users that loggedin user follows) */
      getTweetsByFollowee: function(userId) {
        return $http({
          url: endpointService.getUrl('getTweetsByFollowee', userId),
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

      /* Get tweets by author */
      getTweetsByAuthor: function(userId) {
        return $http({
          url: endpointService.getUrl('getTweetsByAuthor', userId),
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

      /* Get all tweets from from everyone */
      getAllTweets: function() {
        return $http({
          url: endpointService.getUrl('getAllTweets'),
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

      /* Post tweet */
      postTweet: function(tweetText) {
        return $http({
          url: endpointService.getUrl('postTweet'),
          method: 'POST',
          data: {
            content: tweetText
          }
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

    };
  });
