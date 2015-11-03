angular.module('cronos')
    .factory('ciService', ['EntityService', function CategoriaItemServiceFactory(EntityService) {
    'use strict';

    var CategoriaItemService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'categoriaItem'
            });
        },

        /**
         * @param
         * {
         * 		tipoArvore = ('T'/'M'/'S'),
         * 		idCategoria,
         * 		idGrupoTrabalho
         * }
         *
         * @returns
         * {
         *  	categoria = <CategoriaItemEntity> {
         * 			id;
         * 			titulo;
         * 			icone,
         * 			cor,
         * 			idIcone,
         * 			ordem,
         * 			idPai,
         * 			idGrupoTrabalho,
         * 			descricaoArvore,
         * 			compartilhar = ('S'/'N'),
         * 			tipo, ('N'/'E'/'I')
         * 			arvore, ('T'/'M'/'S')
         * 			iconePersonal = <IconesPersonalEntity> {
         * 				id,
         * 				imagem,
         * 				tipo
         * 			},
         * 			tipoCategoria = <TipoCategoriaEntity> {
         * 				id,
         * 				nome
         * 			},
         * 			itens = [<CategoriaItemEntity>],
         * 			taxonomiasEtiqueta = [<TaxonomiaEtiquetaGTEntity>],
         * 			elementos = [<ElementoIGTEntity>]
         * 		},
         *		categoriasModelo = [<CategoriaItemEntity>]
         * }
         * */
        carregarCategoria: function(carregarCategoria) {
            return this._post('/carregarCategoria', carregarCategoria);
        },

        /**
         * @param
         * {
         * 		part,
         * 		idGrupoTrabalho
         * }
         *
         * @returns
         * {
         * 		[<CategoriaItemEntity>]
         * }
         * */
        listByPartDescription : function(autoCompleteDTO) {
            return this._post('/listByPartDescription', autoCompleteDTO);
        },

        /**
         * @param
         * {
         * 		part,
         * 		idGrupoTrabalho
         * }
         *
         * @returns
         * {
         * 		[<CategoriaItemEntity>]
         * }
         * */
        listNodeByPartDescription : function(autoCompleteDTO) {
            return this._post('/listNodeByPartDescription', autoCompleteDTO);
        },

        /**
         * @param
         * {
         * 		titulo,
         * 		idGrupoTrabalho,
         *      usuarioAtualizacao,
         *      tipoArvore, ('T'/'M'/'S')
         * }
         *
         * @returns
         * {
         * 		<CategoriaItemEntity>
         * }
         * */
        saveNode : function(node) {
            return this._post('/saveNode', node);
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

        /**
         * @param
         * {
         *  	categoria = <CategoriaItemEntity>,
         *  	taxonomiasEtiqueta = [<TaxonomiaEtiquetaGTEntity>],
         *  	taxonomiasCategoria = [<TaxonomiaCategoriaEntity>],
         *  	taxonomiasCategoriaNovas = [<TaxonomiaCategoriaEntity>,
         *  	elementos = [<ElementoIGTEntity>]
         * }
         *
         * @returns
         * <CategoriaItemEntity> {
         * 		id;
         * 		titulo;
         * 		icone,
         * 		cor,
         * 		idIcone,
         * 		ordem,
         * 		idPai,
         * 		idGrupoTrabalho,
         * 		descricaoArvore,
         * 		compartilhar = ('S'/'N'),
         * 		TipoNodo tipo, ('N'/'E'/'I')
         * 		TipoArvore arvore, ('T'/'M'/'S')
         * 		iconePersonal = <IconesPersonalEntity> {
         * 			id,
         * 			imagem,
         * 			tipo
         * 		},
         * 		tipoCategoria = <TipoCategoriaEntity> {
         * 			id,
         * 			nome
         * 		},
         * 		itens = [<CategoriaItemEntity>],
         * 		taxonomiasEtiqueta = [<TaxonomiaEtiquetaGTEntity>],
         * 		elementos = [<ElementoIGTEntity>]
         * }
         * */
        salvarTopico: function(topico) {
            return this._post('/salvarTopico', topico);
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
         * <CategoriaItemEntity> {
         * 		id;
         * 		titulo;
         * 		icone,
         * 		cor,
         * 		idIcone,
         * 		ordem,
         * 		idPai,
         * 		idGrupoTrabalho,
         * 		descricaoArvore,
         * 		compartilhar = ('S'/'N'),
         * 		TipoNodo tipo, ('N'/'E'/'I')
         * 		TipoArvore arvore, ('T'/'M'/'S')
         * 		iconePersonal = <IconesPersonalEntity>{
         * 			id,
         * 			imagem,
         * 			tipo
         * 		},
         * 		tipoCategoria = <TipoCategoriaEntity>{
         * 			id,
         * 			nome
         * 		},
         * 		itens = [<CategoriaItemEntity>],
         * 		taxonomiasEtiqueta = [<TaxonomiaEtiquetaGTEntity>],
         * 		elementos = [<ElementoIGTEntity>]
         * }
         * */
        alterarIcone: function(icone) {
            return this._post('/alterarIcone', icone);
        },

        cloneItem: function(item) {
            return this._post('/cloneItem', item);
        },

        listTopicosByIdModelo: function(id) {
            return this._get('/listTopicosByIdModelo/' + id);
        }
    });

    return new CategoriaItemService();
}]);
