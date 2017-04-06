'use strict';

describe('Directive: postSection', function () {

  // load the directive's module
  beforeEach(module('twitterCloneApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<post-section></post-section>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the postSection directive');
  }));
});
