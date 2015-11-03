angular.module('cronos')
    .factory('exportarService', ['DeferService', '$window', '$backendUrl', function exportarServiceFactory(DeferService, $window, $backendUrl) {
    'use strict';

    var ExportarService =  $class(DeferService, {

        constructor: function() {
            DeferService.call(this);
            this.baseUrl = $backendUrl;
            this.exportarSentencaUrl = '/exportarSentenca';
        },

        /**
        * @param numeroProcesso
        * @param extensao
        */
        exportarSentenca: function(numeroProcesso, extensao) {
            $window.location = (this.baseUrl + this.exportarSentencaUrl + '?numeroProcesso=' + numeroProcesso +'&extensao=' + extensao);
        }
    });

    return new ExportarService();
}]);