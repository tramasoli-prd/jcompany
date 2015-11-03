angular.module('cronos')
    .factory('autoCompleteService', ['$q', 'RestService', function autoCompleteFactory($q, RestService) {
    'use strict';

    var AutoCompleteService = $class({

        constructor: function() {
            this.rest = new RestService({
                path: '/rest/autocomplete'
            });
        }
    });

    return new AutoCompleteService();
}]);
