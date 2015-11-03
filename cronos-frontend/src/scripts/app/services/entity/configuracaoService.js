angular.module('cronos')
    .factory('configService', ['EntityService', function confServiceFactory(EntityService) {
    'use strict';

    var ConfiguracaoService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'configuracao'
            });
            this.configuracao = false;
        },
        /* METODOS REST */
        _findConfiguracaoByGrupoTrabalho: function(idGrupoTrabalho) {
            return this._get('/findConfiguracaoByGrupoTrabalho/' + idGrupoTrabalho);
        },
        _permitirEditarMenu: function(obj) {
            return this._post('/permitirEditarMenu', obj);
        },
        /* METODOS DE INTERFACE */
        carregarConfiguracao: function(idGrupoTrabalho) {
            var service = this;
            service._findConfiguracaoByGrupoTrabalho(idGrupoTrabalho)
                .then(function(response) {
                    service.setConfiguracao(response && response.data);
                });
        },
        permitirEditarMenu: function(permitir, idGrupoTrabalho, usuarioAtualizacao) {
            var service = this;

            var obj = {
                permiteEditarMenu: permitir,
                idGrupoTrabalho: idGrupoTrabalho,
                usuarioAtualizacao: usuarioAtualizacao
            };

            service._permitirEditarMenu(obj)
                .then(function(response) {
                    service.setPermitirEditarMenu(permitir);
                });
        },
        /*METODO DE ATRIBUTOS */
        getConfiguracao : function () {
            return this.configuracao;
        },
        setConfiguracao : function (resposta) {
            this.configuracao = resposta;
        },
        getPermitirEditarMenu : function () {
            return this.configuracao ? this.configuracao.permiteEditarMenu : null;
        },
        setPermitirEditarMenu : function (permite) {
            if (this.configuracao) {
                this.configuracao.permiteEditarMenu = permite;
            }
        }
    });

    return new ConfiguracaoService();
}]);
