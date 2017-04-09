'use strict';

/**
 * @ngdoc service
 * @name twitterCloneApp.utilityService
 * @description
 * # utilityService
 * Factory in the twitterCloneApp.
 */
angular.module('twitterCloneApp')
  .factory('utilityService', function() {

    return {
      /* Get partialview based on parameters */
      getPartialView: function(view) {
        /* Check if view string is empty, null or undefined */
        if (!view || 0 === view.length) {
          return null;
        } else {
          return 'views/partials/' + view + '.html';
        }
      }
    };

  });
