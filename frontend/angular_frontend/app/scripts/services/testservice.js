'use strict';

/**
 * @ngdoc service
 * @name twitterCloneApp.testService
 * @description
 * # testService
 * Factory in the twitterCloneApp.
 */
angular.module('twitterCloneApp')
  .factory('testService', function($rootScope) {

    return {
      subscribe: function(scope, callback) {
        var handler = $rootScope.$on('notifying-service-event', callback)
            scope.$on('$destroy', handler);
      },
      notify: function() {
        $rootScope.$emit('notifying-service-event');
      }
    };
  });

/*
  // PUT IN CONTROLLER:
  testService.subscribe($scope, function(somethingChanged) {
    // Handle notification
  });
*/
