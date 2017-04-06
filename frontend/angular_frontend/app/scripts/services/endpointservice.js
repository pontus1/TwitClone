'use strict';

/**
 * @ngdoc service
 * @name twitterCloneApp.endpointService
 * @description
 * # endpointService
 * Factory in the twitterCloneApp.
 */
angular.module('twitterCloneApp')
  .factory('endpointService', function() {

    var base_url = 'http://localhost:8080';
    var base_api_url = 'http://localhost:8080/api/';

    return {
      /* Get url */
      getUrl: function(endpoint, userId, loggedInUser, username) {
        /* Check if endpoint string is empty, null or undefined */
        if (!endpoint || 0 === endpoint.length) {
          return '';
        } else {
          switch (endpoint) {
            case 'signIn':
              return base_url + '/oauth/token';
            case 'registerUser':
              return base_url + '/register/';
              /* user */
            case 'getLoggedInUser':
              return base_url + '/api/users/loggedInUser';
              /* follow */
            case 'followUser':
              return base_url + '/api/users/' + loggedInUser + '/follow/' + userId;
            case 'unfollowUser':
              return base_url + '/api/users/' + loggedInUser + '/unfollow/' + userId;
              /* users */
            case 'getUser':
              return base_url + '/api/users/name/' + username;
            case 'getAllUsers':
              return base_url + '/api/users/';
            case 'getAllFollowees':
              return base_url + '/api/users/' + userId + '/followees'
              /* tweets */
            case 'getTweetsByFollowee':
              return base_url + '/api/tweets/' + userId + '/followees';
            case 'getTweetsByAuthor':
              return base_url + '/api/tweets/author/' + userId;
            case 'getAllTweets':
              return base_url + '/api/tweets/all_tweets';
            case 'postTweet':
              return base_url + '/api/tweets/';
            default:
              return '';
          }
        }
      }

    };
  });
