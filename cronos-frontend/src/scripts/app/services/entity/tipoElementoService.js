angular.module('cronos')
    .factory('tipoElementoService', ['EntityService', function tipoElementoServiceFactory(EntityService) {
    'use strict';

    var TipoElementoService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                 type: 'tipoElemento'
            });
        },

        all: function() {
            return this._get('/all');
        }
    });

    return new TipoElementoService();
}]);
