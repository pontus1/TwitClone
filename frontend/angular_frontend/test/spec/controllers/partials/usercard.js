'use strict';

describe('Controller: PartialsUsercardCtrl', function () {

  // load the controller's module
  beforeEach(module('twitterCloneApp'));

  var PartialsUsercardCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    PartialsUsercardCtrl = $controller('PartialsUsercardCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
