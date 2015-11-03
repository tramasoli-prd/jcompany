angular.module('cronos').factory('alegacaoGrupoTrabalhoService', ['EntityService', function alegacaoGrupoTrabalhoServiceFactory(EntityService) {
    'use strict';

    var AlegacaoGrupoTrabalhoService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                 type: 'alegacaoGT'
            });
            this.alegacoes = [];
            this.alegacoesPreenchidas = [];
        },
        /* METODOS REST */
        _all: function() {
            return this._get('/all');
        },
        _findAllByGrupoTrabalho : function( idGrupoTrabalho ) {
            return this._get('/findAllByGrupoTrabalho/' + idGrupoTrabalho);
        },
        /* METODOS DE INTERFACE */
        carregarAlegacoes : function( idGrupoTrabalho ) {
            var service = this;
            service._findAllByGrupoTrabalho(idGrupoTrabalho).then(function(response) {
                service.setAlegacoes(response && response.data);
            });
        },
        /*METODO DE ATRIBUTOS */
        getAlegacoes : function () {
            return this.alegacoes;
        },
        getAlegacoesPreenchidas : function () {
            return this.alegacoesPreenchidas;
        },
        setAlegacoes : function (lista) {
            this.alegacoes = lista;
            this.setAlegacoesPreenchidas(this.alegacoes);
        },
        setAlegacoesPreenchidas : function (lista) {
            var listaNova = [];
            lista.forEach(function(elemento){
                if (elemento.textoAlegacao) {
                    listaNova.push(elemento);
                }
            });
            listaNova.sort(function(a, b){
                if(a.tipoAlegacao.label < b.tipoAlegacao.label) return -1;
                if(a.tipoAlegacao.label > b.tipoAlegacao.label) return 1;
                return 0;
            });
            this.alegacoesPreenchidas = listaNova;
        }
    });
    return new AlegacaoGrupoTrabalhoService();
}]);
