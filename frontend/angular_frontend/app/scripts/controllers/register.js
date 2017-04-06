'use strict';

/**
 * @ngdoc function
 * @name twitterCloneApp.controller:RegisterCtrl
 * @description
 * # RegisterCtrl
 * Controller of the twitterCloneApp
 */
angular.module('twitterCloneApp')
  .controller('RegisterCtrl', function($scope, $uibModal, $log, registerService) {

    /*******************
       Init controller
     *******************/

    /* Input variables */
    $scope.username = '';
    $scope.email = '';
    $scope.password = '';
    $scope.confirmPassword = '';

    /* Regex for a minimum of 2 characters */
    $scope.regexUsername = /^.{4,}$/;

    /* Regex for a minimum of 6 characters */
    $scope.regexPassword = /^.{6,}$/;

    /* Regex for email adresses */
    $scope.regexEmail = /[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}/;

    /* Variable for coherens between password input fields */
    $scope.matchingPasswords = false;

    /* Variable for button 'Skapa konto' */
    $scope.isValidated = false;

    /*******************
          Watchers
     *******************/

    /* Watch for matching passwords */
    $scope.$watch('[password, confirmPassword]', function(newVal, oldVal) {
      if ($scope.password === $scope.confirmPassword) {
        $scope.matchingPasswords = true;
      } else {
        $scope.matchingPasswords = false;
      }
    }, true);

    /*******************
          Functions
     *******************/

    /* validate signup form and register if all is OK */
    $scope.signup = function() {
      if ($scope.signupForm.$invalid || $scope.signupForm.$pristine) {
        $scope.isValidated = true;
      } else {
        if ($scope.matchingPasswords) {
          registerUser();
        }
      }
    };

    /* Register user */
    function registerUser() {
      registerService.registerUser($scope.username, $scope.email, $scope.password).then(function(data) {
        switch (data.status) {
          /* Created */
          case 201:
            $scope.modalTitleText = 'Grattis ' + $scope.username + '! Du har nu ett konto på twitterClone.';
            $scope.modalBodyText = 'Logga in genom att trycka på "logga in" och ange ditt användarnamn och lösenord.';
            break;
          /* Conflict */
          case 409:
            if (data.data.message === "Email already taken") {
              $scope.modalTitleText = 'Error: ' + data.status;
              $scope.modalBodyText = 'Emailadressen ' + '"' + $scope.email + '"' + ' Är tyvärr upptagen. Vänligen försök igen.';
            }
            if (data.data.message === "Username already taken") {
              $scope.modalTitleText = 'Error: ' + data.status;
              $scope.modalBodyText = 'Användarnamnet ' + '"' + $scope.username + '"' + ' Är tyvärr upptaget. Vänligen försök igen.'
            }
            break;
          /* Internal server error */
          case 500:
            $scope.modalTitleText = 'Error: ' + data.status;
            $scope.modalBodyText = data;
            break;
          /* Other */
          default:
            $scope.modalTitleText = 'Error: ' + data.status;
            $scope.modalBodyText = data;
            break;
        }
        $scope.openModalPopup();
      });
    }

    /* Clear all input fields */
    function clearInputFields() {
      $scope.username = '';
      $scope.email = '';
      $scope.password = '';
      $scope.confirmPassword = '';
    }

    /*******************
            Modal
     *******************/

    $scope.modalTitleText = '';
    $scope.modalBodyText = '';

    // Instantiate the modal window
    var modalPopup = function() {
      return $scope.modalInstance = $uibModal.open({
        templateUrl: 'views/partials/modals/register.html',
        scope: $scope
      });
    };

    // Modal window popup trigger
    $scope.openModalPopup = function() {
      modalPopup().result
        .then(function(data) {
          $scope.handleSuccess(data);
          clearInputFields();
        })
        .then(null, function(reason) {
          $scope.handleDismiss(reason);
        });
    };

    // Close the modal if Yes button click
    $scope.yes = function() {
      $scope.modalInstance.close('Yes Button Clicked')
    };

    // Dismiss the modal if No button click
    $scope.no = function() {
      $scope.modalInstance.dismiss('No Button Clicked')
    };

    // Log Success message
    $scope.handleSuccess = function(data) {
      $log.info('Modal closed: ' + data);
    };

    // Log Dismiss message
    $scope.handleDismiss = function(reason) {
      $log.info('Modal dismissed: ' + reason);
    }


  });
