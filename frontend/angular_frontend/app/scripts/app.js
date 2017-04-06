'use strict';

/**
 * @ngdoc overview
 * @name twitterCloneApp
 * @description
 * # twitterCloneApp
 *
 * Main module of the application.
 */
angular
  .module('twitterCloneApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ngStorage',
    'ui.bootstrap'
  ])
  .config(function($routeProvider, $httpProvider, $locationProvider) {

    $routeProvider
      .when('/', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        controllerAs: 'login',
        title: 'login',
        needAuthentication: false
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        controllerAs: 'login',
        title: 'login',
        needAuthentication: false
      })
      .when('/register', {
        templateUrl: 'views/register.html',
        controller: 'RegisterCtrl',
        controllerAs: 'register',
        title: 'register',
        needAuthentication: false
      })
      .when('/home', {
        templateUrl: 'views/home.html',
        controller: 'HomeCtrl',
        controllerAs: 'home',
        title: 'home',
        needAuthentication: true,
        resolve: {
          LoggedInUser: ['$sessionStorage', 'userService', function($sessionStorage, userService) {
            return userService.getLoggedInUser($sessionStorage.logged_in_user)
          }]
        }
      })
      .when('/main', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main',
        title: 'main',
        needAuthentication: true
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about',
        title: 'about',
        needAuthentication: true
      })
      .when('/users', {
        templateUrl: 'views/users.html',
        controller: 'UsersCtrl',
        controllerAs: 'users',
        title: 'users',
        needAuthentication: true
      })
      .when('/user/:username', {
        templateUrl: 'views/user.html',
        controller: 'UserCtrl',
        controllerAs: 'user',
        title: 'user',
        needAuthentication: true
      })
      .otherwise({
        redirectTo: '/home'
      });

    /* Enable HTML5 mode to remove # from URL */
    // $locationProvider.html5Mode(true);

    /* Create a token interceptor for request headers */
    $httpProvider.interceptors.push('TokenInterceptor');

  }).run(function($sessionStorage, $rootScope, $location, authenticationService) {

    /* Handle route changes */
    $rootScope.$on("$routeChangeStart", function(event, next, current) {

      console.log('sessionStorage: ' + $sessionStorage.token);

      console.log(next.$$route);
      console.log('needAuthentication:' + next.needAuthentication);

      /* If a logged in user tries to go to log in or register he/she will be rerouted to "Home" */
      // if (authenticationService.isAuthenticated && $sessionStorage.token) {
      //   if (next.controllerAs === 'login' || next.controllerAs === 'register') {
      //     $location.path('/home');
      //   }
      // }

      console.log('auth: ' + authenticationService.isAuthenticated);
      /* If user is unauthorized and tries to reach a protected view, reroute to login */
      // if (next.needAuthentication && !authenticationService.isAuthenticated) {
      //   $location.path('/login');
      // }




    });
  });
