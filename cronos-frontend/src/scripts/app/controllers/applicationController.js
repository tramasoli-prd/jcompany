angular
    .module('cronos')
    .controller('AppController', ['authService', '$window', '$scope', '$location', 'usuarioService', 'notificationService', 'iconesPersonalService', 'pesquisaGeralService', 'modalService', 'registerController', 'tokenService', 'sentencaService', 'Utils', 'gtService',
        function ApplicationController(authService, $window, $scope, $location, usuarioService, notificationService, iconesPersonalService, pesquisaGeralService, modalService, registerController, tokenService, sentencaService, Utils, gtService) {
        'use strict';

        var controller = this;

        var menuOpened = false,
            sentencaOpened = false;

        controller.pesquisa = null;
        controller.tipoPesquisa = "TU";
        controller.listaPesquisaGeralTopico = [];
        controller.listaPesquisaGeralModelo = [];
        controller.listaPesquisaGeralSentenca = [];
        // controller.loading = false;

        controller.loadingPesquisaGeral = function() {
            return pesquisaGeralService.isWorking();
        };

        controller.isMobile = function() {
            return $window.innerWidth<768;
        };

        controller.sentencaIsOpen = function() {
            return sentencaService.isOpen();
        };

        controller.sentencaIsLoaded = function() {
            return sentencaService.isLoaded();
        };

        controller.loadIndex = function() {
            sentencaService.setClose();
            $window.location.href = '#/';
        };

        controller.menuIsOpen = function() {
            return menuOpened;
        };

        controller.setOpenClose = function( status ) {
            if (!Utils.isUndefinedOrNull(status))
                menuOpened = status;
            else
                menuOpened = !menuOpened;
        };

        controller.isLogged = function() {
            return authService.isLogged();
        };

        controller.getUsername = function() {
            return authService.getName();
        };

        controller.isMagistrate = function() {
            return authService.isMagistrate();
        };

        controller.isAdmin = function() {
            return authService.isAdmin();
        };

        controller.hasWriteAccess = function() {
            return authService.hasWriteAccess();
        };

        controller.getPerfil = function() {
            return authService.getPerfil();
        };

        controller.logout = function() {
            authService
                .logout()
                .then(function() { // not logged
                    $window.location.href = 'login.html';
                });
        };

        // controller.changeView = function(view) {
        //     $location.path(view);
        // };

        controller.carregarCSS = function() {
            iconesPersonalService.css()
                .then(function(response){
                    Utils.generateDynamicCSS(response.data.css);
                })
                .finally(function(response){
                    notificationService.push(response);
                });
        };

        controller.pesquisaGeral = function() {
            controller.listaPesquisaGeralTopico = [];
            controller.listaPesquisaGeralModelo = [];
            controller.listaPesquisaGeralSentenca = [];
            if(controller.pesquisa){
                pesquisaGeralService.pesquisarTopico(controller.pesquisa, controller.tipoPesquisa).then(function (response) {
                    var resultado = response.data;
                    if (resultado) {
                        resultado.forEach(function (elemento) {
                            controller.listaPesquisaGeralTopico.push(elemento);
                        });
                    }
                });
                pesquisaGeralService.pesquisarModelo(controller.pesquisa, controller.tipoPesquisa).then(function (response) {
                    var resultado = response.data;
                    if (resultado) {
                        resultado.forEach(function (elemento) {
                            controller.listaPesquisaGeralModelo.push(elemento);
                        });
                    }
                });
                $location.path("/pesquisa-geral");
            }
        };

        controller.showTooltip = function(item) {
            var valor = '';
            angular.forEach(item, function(value) {
                switch (value.tipo) {
                    case 'ET':
                        valor += 'Etiqueta: ';
                        break;
                    case 'EL':
                        valor += 'Elemento: ';
                        break;
                    case 'TI':
                        valor += 'Título: ';
                        break;
                }

                valor += value.label + ' <br/> ';
            });

            return valor;
        };

        $(window).resize(function(){
            $scope.$apply(function(){
                controller.setOpenClose(!controller.isMobile());
            });
        });

        controller.setOpenClose(!controller.isMobile());
        controller.carregarCSS();
    }]);
