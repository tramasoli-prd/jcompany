angular.module('cronos')
    .factory('tipoAlegacaoService', ['EntityService', function tipoAlegacaoServiceFactory(EntityService) {
    'use strict';

    var TipoAlegacaoService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                 type: 'tipoAlegacao'
            });
        },

        all: function() {
            return this._get('/all');
        }
    });

    return new TipoAlegacaoService();
}]);
