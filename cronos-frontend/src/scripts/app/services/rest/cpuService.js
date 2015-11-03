angular.module('cronos')
    .factory('cpuService', ['$q', 'RestSupport', function cpuServiceFactory($q, RestSupport) {
    'use strict';

    var CpuService = $class(RestSupport, {

        constructor: function() {
            RestSupport.call(this, {
                path: '/rest/cpu'
            });
        },

        /**
        * {
        * numeroProcesso,
        * comarca
        * }
        */
        carregarProcesso: function(carregarProcessoDTO) {
            return this.rest.post('/carregarProcesso', carregarProcessoDTO);
        }
    });

    return new CpuService();
}]);
