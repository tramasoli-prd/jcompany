angular
    .module('cronos')
    .controller('DragDropController', ['authService',
        function MenuController(authService) {
        'use strict';

        var controller = this;

        //TODO Método a ser implementado quando a arvore for corrigida no banco
        controller.moveItem = function(data, nodo) {
            return "";
        };

        controller.hasWriteAccess = function() {
            return authService.hasWriteAccess();
        };



}]);
