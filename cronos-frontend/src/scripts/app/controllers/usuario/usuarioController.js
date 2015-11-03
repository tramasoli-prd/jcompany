angular
    .module('cronos')
    .controller('UserFormController', ['$scope', '$routeParams', '$window', '$contextUrl', 'notificationService', 'usuarioService', 'usuarioCTJService', 'authService', 'modalService', 'Utils',
        function UserFormController($scope, $routeParams, $window, $contextUrl, notificationService, usuarioService, usuarioCTJService, authService, modalService, Utils) {
        'use strict';

        var controller = this;

        /* ------------------
         * Atributos Gerais
         * -----------------*/
        // Pesquisa Usuarios CTJ
        controller.username = null;
        controller.usuariosCTJ = [];
        controller.usuariosSelecionados = [];
        controller.usuariosSubordinados = [];

        /* ------------------
         * Metodos Privados
         * -----------------*/
        var _carregarUsuariosSubordinados = function() {
            usuarioService
                ._findByGrupoTrabalho(authService.getIdGrupoTrabalho())
                .then(function(response){
                    controller.usuariosSubordinados = response.data;
                });
        };

        /* ------------------
         * Metodos Publicos
         * -----------------*/
        controller.loadingUsuarios = function(){
            return usuarioCTJService.isWorking();
        };

        controller.loadingSubordinados = function(){
            return usuarioService.isWorking();
        };

        controller.pesquisaUsuarioCTJ = function() {
            controller.selectAll = false;

            if (!controller.username) {
                notificationService.error("PESQUISA_VAZIA_015");
                return;
            }
            if (controller.username.length < 3) {
                notificationService.warning("PESQUISA_MINIMA_014");
                return;
            }

            usuarioCTJService
                .listaPessoas(controller.username)
                .then(function(response) {
                    controller.usuariosSubordinados.forEach(function(usuario){
                        var result = _.find(response.data, {cpf: usuario.cpf});
                        if (result) {
                            var idx = response.data.indexOf(result);
                            response.data[idx].disabled = true;
                        }
                    });
                    controller.usuariosCTJ = response.data;
                }, function() {
                    controller.usuariosCTJ = [];
                });
        };

        controller.selecionarTodos = function() {
            controller.usuariosCTJ.forEach(function(usuario) {
                usuario.selecionado = controller.selectAll;
                controller.adicionarRemover(usuario);
            });
        };

        controller.adicionarRemover = function(usuario) {
            if (usuario.selecionado) {
                if (!controller.usuariosSelecionados.search(usuario))
                    controller.usuariosSelecionados.push(usuario);
            } else {
                if (controller.usuariosSelecionados.search(usuario))
                    controller.usuariosSelecionados.delete(usuario);
            }
        };

        controller.adicionarSelecionados = function() {
            // TODO: Refatorar para salvar multiplos.
            controller.usuariosSelecionados.forEach(function(usuario) {

                Utils.sanitizeObject(usuario, ["$$hashKey","selecionado","magistrado"]);

                usuario.usuarioAtualizacao = authService.getUsername();
                usuario.idGrupoTrabalho = authService.getIdGrupoTrabalho();
                usuario.login = usuario.cpf;

                usuarioService._salvarUsuario(usuario)
                    .then(function(response){
                        controller.usuariosSubordinados.push(response.data);
                        controller.usuariosSubordinados.sort(function(a, b){
                            if(a.nome < b.nome) return -1;
                            if(a.nome > b.nome) return 1;
                            return 0;
                        });
                        controller.usuariosSelecionados.delete(usuario);
                        controller.usuariosCTJ.delete(usuario);
                    });
            });
        };

        controller.mudaPermissoes = function( usuario ) {
            usuario.usuarioAtualizacao = authService.getUsername();
            usuarioService
                ._mudaPermissoes(usuario)
                .then(function(response){
                    controller.usuariosSubordinados.replace(usuario,response.data);
                });
        };

        controller.excluirSubordinado = function( usuario ) {
            modalService.open('confirm', {
                message: 'Tem certeza de que deseja remover esse usuário?',
                confirmButton: 'Confirmar',
                cancelButton: 'Cancelar'
            })
            .then(function(){
                usuario.usuarioAtualizacao = authService.getUsername();
                usuario.idGrupoTrabalho = authService.getIdGrupoTrabalho();
                usuarioService
                    ._removerUsuario(usuario)
                    .then(function(response){
                        if (response.data) {
                            controller.usuariosSubordinados.delete(usuario);
                        }
                    });
            });
        };

        /* ------------------
         * Carrega Dados
         * -----------------*/
        _carregarUsuariosSubordinados();

        if (!authService.isAdmin())
            $window.location.href = $contextUrl + '#/acesso-negado';
    }]);
