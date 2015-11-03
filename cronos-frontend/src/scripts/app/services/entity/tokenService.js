angular.module('cronos')
    .factory('tokenService', ['EntityService', function tokenServiceFactory(EntityService) {
    'use strict';

    var TokenService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                 type: 'token'
            });
            this.listaTokens = false;
            this.token = false;
        },
        /* METODOS REST */
        _all: function() {
            return this._get('/all');
        },
        /* METODOS DE INTERFACE */
        carregarTokens : function() {
            var service = this;
            service._all().then(function(response){
                service.setListaTokens(response && response.data);
            });
        },
        /*METODO DE ATRIBUTOS */
        getListaTokens : function() {
            return this.listaTokens;
        },
        setListaTokens : function(lista) {
            this.listaTokens = lista;
        }
    });

    return new TokenService();
}]);
