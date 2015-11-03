angular.module('cronos')
    .factory('RestSupport', ['RestService', function restServiceFactory(RestService) {
    'use strict';

    var RestSupport = $class({

        constructor: function(restConfig) {
            this.rest = new RestService(restConfig);
        },

        bindThen: function(http, ok, error) {
            // http already have deferWork from DeferService
            return http.then(angular.bind(this, ok), angular.bind(this, error));
        },

        isWorking: function() {
            return this.rest.isWorking();
        }
    });

    return RestSupport;
}]);