angular.module('cronos')
    .factory('etiquetaGTService', ['EntityService', function etiquetaGTServiceFactory(EntityService) {
    'use strict';

    var EtiquetaGTService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'etiquetaGT'
            });
        },

        listByPartDescription: function(listPartEtiqueta) {
            return this._post('/listByPartDescription/', listPartEtiqueta);
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

        getAll: function() {
            return this._get('/all');
        },

        /**
         * @param
         * {
         * 		id,
         * 		icone,
         * 		cor,
         * 		imagem = {
         * 			filesize,
         * 			filetype,
         * 			filename,
         * 			base64
         * 		}
         * }
         *
         * @returns
         * <EtiquetaGTEntity> {
         * 		id,
         * 		etiqueta,
         * 		icone,
         * 		idIcone,
         * 		cor,
         * 		iconePersonal = <IconesPersonalEntity> {
         * 			id,
         * 			imagem,
         * 			tipo
         * 		}
         * }
         * */
        alterarIcone: function(icone) {
            return this._post('/alterarIcone', icone);
        }
    });

    return new EtiquetaGTService();
}]);
