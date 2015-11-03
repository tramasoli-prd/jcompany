angular
    .module('cronos')
    .controller('GrupoTrabalhoLoginController', ['$scope', '$location', '$routeParams', '$window', 'gtService', '$contextUrl', 'authService', 'Utils',
        function GrupoTrabalhoLoginController($scope, $location, $routeParams, $window, gtService, $contextUrl, authService, Utils) {
        'use strict';

        var controller = this;

        controller.itens = [];

        controller.loadingGTLogin = function(){
            return gtService.isWorking();
        };

        controller.carregar = function() {
            authService.check().then(function(userInfo){
                gtService._findByUsuario(userInfo.usuario.id).then(function(response){
                    if(response.data.length > 1){
                        controller.itens = response.data;
                    } else {
                        $window.location.href = $contextUrl;
                    }
                });
            });
        };

        controller.selecionaGrupo = function () {
            $window.location.href = $contextUrl;
        };

        controller.setGrupoPadrao = function (elemento) {
            gtService._alteraPadrao(elemento)
            .then(function(response){

            },function(error){
                console.log(error);
            });
        };

        controller.carregar();
}]);
