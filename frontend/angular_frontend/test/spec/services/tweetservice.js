'use strict';

describe('Service: tweetService', function () {

  // load the service's module
  beforeEach(module('twitterCloneApp'));

  // instantiate service
  var tweetService;
  beforeEach(inject(function (_tweetService_) {
    tweetService = _tweetService_;
  }));

  it('should do something', function () {
    expect(!!tweetService).toBe(true);
  });

});
