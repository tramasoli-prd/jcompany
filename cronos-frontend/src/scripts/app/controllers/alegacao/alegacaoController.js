angular
    .module('cronos')
    .controller('AlegacaoController', ['$window', '$contextUrl', 'notificationService', 'tipoAlegacaoService', 'alegacaoGrupoTrabalhoService', 'authService', 'Utils',
        function AlegacaoController($window, $contextUrl, notificationService, tipoAlegacaoService, alegacaoGrupoTrabalhoService, authService, Utils) {
        'use strict';

        var controller = this;

        /* ------------------
         * Atributos Gerais
         * -----------------*/
        controller.loading = false;
        controller.alegacoes = [];

        /* ------------------
         * Metodos
         * -----------------*/
        controller.isLoading = function() {
            return alegacaoGrupoTrabalhoService.isWorking();
        };

        controller.save = function() {
            controller.alegacoes.forEach( function (alegacao) {
                alegacao.usuarioAtualizacao = authService.getUsername();

                Utils.sanitizeObject(alegacao,['dataAtualizacao','dataCriacao']);

                alegacaoGrupoTrabalhoService
                    .save( alegacao )
                    .then( function (response) {
                        alegacao.versao = response.data.versao;
                        alegacaoGrupoTrabalhoService.carregarAlegacoes(authService.getIdGrupoTrabalho());
                    });
            });
            notificationService.success("DADOS_SALVOS_SUCESSO_000");

        };

        controller.clean = function(alegacao) {
            alegacao.textoAlegacao = "";
            alegacao.usuarioAtualizacao = authService.getUsername();
        };

        controller.loadAlegacoes = function() {
            alegacaoGrupoTrabalhoService
                ._findAllByGrupoTrabalho( authService.getIdGrupoTrabalho() )
                .then( function (response) {
                    controller.alegacoes = response.data;
                });
        };

        /* ------------------
         * Carrega Dados
         * -----------------*/
        controller.loadAlegacoes();

        if (!authService.isAdmin())
            $window.location.href = $contextUrl + '#/acesso-negado';
    }]);
