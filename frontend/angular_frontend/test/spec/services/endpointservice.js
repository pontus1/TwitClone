'use strict';

describe('Service: endpointService', function () {

  // load the service's module
  beforeEach(module('twitterCloneApp'));

  // instantiate service
  var endpointService;
  beforeEach(inject(function (_endpointService_) {
    endpointService = _endpointService_;
  }));

  it('should do something', function () {
    expect(!!endpointService).toBe(true);
  });

});
