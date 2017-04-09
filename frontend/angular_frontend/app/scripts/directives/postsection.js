'use strict';

/**
 * @ngdoc directive
 * @name twitterCloneApp.directive:postSection
 * @description
 * # postSection
 */
angular.module('twitterCloneApp')
  .directive('postSection', function() {

    var controller = ['$scope', 'tweetService',
      function($scope, tweetService) {

        /*******************
           Init controller
         *******************/

        $scope.tweetText = '';
        $scope.charactersLeft = 140;

        /*******************
              Functions
         *******************/

        $scope.postTweet = function() {
          /* only post if tweetText contains at least 2 characters */
          if ($scope.tweetText !== undefined && $scope.tweetText.length > 1) {
            tweetService.postTweet($scope.tweetText).then(function(data) {
              if (data !== null) {
                // update variable!!
                $scope.lastTweet = $scope.tweetText;
                $scope.tweetText = '';
              } else {
                // TODO: Handle error
              }
            })
          } else {
            alert('Your tweet must contain at least 2 characters')
          }
        };

        /*******************
              Watchers
         *******************/

         $scope.$watch('tweetText', function(newVal, oldVal) {
           if (oldVal.length !== newVal.length) {
             $scope.charactersLeft = 140 - newVal.length;
           }
         });

      }
    ];

    return {
      scope: {
        lastTweet: '='
      },
      controller: controller,
      templateUrl: 'views/directives/postsection.html',
      restrict: 'E',

      // link: function postLink(scope, element, attrs) {
      //   element.text('this is the postSection directive');
      // }
    };
  });
