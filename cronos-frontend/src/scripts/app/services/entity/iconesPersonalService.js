angular.module('cronos')
    .factory('iconesPersonalService', ['EntityService', function iconesPersonalServiceFactory(EntityService) {
    'use strict';

    var IconesPersonalService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'iconesPersonal'
            });
        },

        css: function() {
            return this._get('/css');
        }
    });

    return new IconesPersonalService();
}]);
