angular
    .module('cronos')
    .controller('GtPreviewController', ['ciService', 'authService', 'Utils', function GtPreviewController(ciService, authService, Utils) {
        'use strict';

        var controller = this;

        /* ------------------
         * Atributos Gerais
         * -----------------*/
        controller.item = null;
        controller.categoriaItem = false;
        controller.etiquetas = [];
        controller.topicos = [];
        controller.categorias = [];
        controller.compartilhar = false;
        controller.readonly = true;

        /* ------------------
         * Metodos
         * -----------------*/

        controller.isLoading = function() {
            return ciService.isWorking();
        };

        controller.carregaElementoPadrao = function(id) {
            var carregarCategoria = {
                    "idCategoria" : id,
                    "idGrupoTrabalho" : authService.getIdGrupoTrabalho()
            };

            ciService
                .carregarCategoria(carregarCategoria)
                .then(function(response){
                    controller.categoriaItem = response.data;
                    controller.item = controller.categoriaItem.categoria;
                    controller.categorias = controller.categoriaItem.categoriasModelo;
                    controller.item.usuarioAtualizacao = authService.getUsername();

                    if (controller.item.taxonomiasEtiqueta) {
                        for(var iNT = 0; iNT < controller.item.taxonomiasEtiqueta.length; iNT++) {
                            var etiquetaIn = controller.item.taxonomiasEtiqueta[iNT].etiquetaGT;
                            var etiquetaNova = {
                                'id' : etiquetaIn.id,
                                'etiqueta' : etiquetaIn.etiqueta,
                                'idIcone'  : etiquetaIn.idIcone,
                                'icone'   : etiquetaIn.icone,
                                'cor'   : etiquetaIn.cor
                            };
                            controller.etiquetas.push(etiquetaNova);
                        }
                        for(var indTaxCat = 0; indTaxCat < controller.categoriaItem.taxonomiasCategoriaNovas.length; indTaxCat++) {
                            var modelo = controller.categoriaItem.taxonomiasCategoriaNovas[indTaxCat].categoriaItem;
                            modelo = Utils.sanitizeObject(modelo, ["$$hashKey"]);
                            controller.topicos.push(modelo);
                        }
                        var elementosAntigo = [];
                        for (var indEleAnt = 0; indEleAnt < controller.item.elementos.length; indEleAnt++) {
                            var elementoAntigo = controller.item.elementos[indEleAnt];
                            elementoAntigo = Utils.sanitizeObject(elementoAntigo, ["$$hashKey"]);
                            controller.categoriaItem.elementos.push(elementoAntigo);
                        }
                        controller.categoriaItem.taxonomiasEtiqueta = controller.item.taxonomiasEtiqueta;
                        controller.categoriaItem.taxonomiasCategoria = controller.categoriaItem.taxonomiasCategoriaNovas;
                    } else {
                        controller.categoriaItem.taxonomiasEtiqueta = [];
                        controller.categoriaItem.taxonomiasCategoria = [];
                        controller.categoriaItem.taxonomiasCategoriaNovas = [];
                        controller.categoriaItem.elementos = [];
                        controller.item.taxonomiasEtiqueta = [];
                    }
                    if (controller.item.compartilhar == "S") {
                        controller.compartilhar = true;
                    }
                    if ("T" === controller.item.arvore) {
                        controller.item.descricaoArvore = "Tópico";
                    } else {
                        controller.item.descricaoArvore = "Modelo";
                    }
                });
        };

    }]);
