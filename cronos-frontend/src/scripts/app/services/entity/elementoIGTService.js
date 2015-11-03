angular.module('cronos')
    .factory('elementoItemGrupoTrabalhoService', ['EntityService', function ElementoItemGrupoTrabalhoServiceFactory(EntityService) {
    'use strict';

    var ElementoItemGrupoTrabalhoService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'elementoIGT'
            });
        },

        listByPartDescription : function(part) {
            return this._get('/listByPartDescription/' + part);
        },

        /**
         * @param
         * {
         * 		part,
         * 		tipoArvore = ('T'/'M'/'S'),
         * 		idGrupoTrabalho,
         * }
         *
         * @returns
         * [{
         * 		id,
         * 		titulo,
         *  	arvore = ('T'/'M'/'S'),
         *  	label = [{String}],
         *		excluir = (true/false),
         *		editar = (true/false),
         *		revisar = (true/false),
         *		adicionar = (true/false),
         *		visualizar = (true/false),
         * }]
         * */
        buscarPesquisaGeral : function(pesquisaGeralDTO) {
            return this._post('/buscarPesquisaGeral', pesquisaGeralDTO);
        },

    });

    return new ElementoItemGrupoTrabalhoService();
}]);
