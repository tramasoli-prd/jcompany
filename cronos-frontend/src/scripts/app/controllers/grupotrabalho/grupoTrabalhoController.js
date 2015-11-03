angular
    .module('cronos')
    .controller('GtFormController', ['$scope', '$routeParams', '$window', 'notificationService', 'gtService', 'ciService', 'authService', '$contextUrl', 'modalService', 'sentencaService', 'Utils',
        function GtFormController($scope, $routeParams, $window, notificationService, gtService, ciService, authService, $contextUrl, modalService, sentencaService, Utils) {
        'use strict';

        var TEMPO = 5;
        var controller = this;

        /* ------------------
         * Atributos Gerais
         * -----------------*/
        controller.excluir = false;
        controller.item = null;
        controller.compartilhar = false;
        controller.readonly = false;
        controller.stop = false;
        controller.timer = TEMPO;

        /* ------------------
         * Metodos
         * -----------------*/
        if($routeParams.tipo === 'M'){
            gtService.setModeloScreen();
        }

        controller.isLoading = function() {
            return ciService.isWorking();
        };

        controller.montarTela = function(retorno) {
            controller.categoriaItem = retorno;
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
                    gtService.addTopicos(modelo);
                }
                var elementosAntigo = [];
                for (var indEleAnt = 0; indEleAnt < controller.item.elementos.length; indEleAnt++) {
                    var elementoAntigo = controller.item.elementos[indEleAnt];
                    elementoAntigo = Utils.sanitizeObject(elementoAntigo, ["$$hashKey"]);
                    controller.categoriaItem.elementos.push(elementoAntigo);
                }

                var pai = controller.categoriaItem.pai;
                if(pai){
                    controller.categoriasTopicos.push(pai);
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
        };

        controller.carregaElementoPadrao = function(tipo, id, readonly) {
            controller.categoriaItem = false;
            controller.etiquetas = [];
            gtService.clear();
            controller.categorias = [];
            controller.categoriasTopicos = [];

            var carregarCategoria = {
                    "idCategoria" : id,
                    "tipoArvore" : tipo,
                    "idGrupoTrabalho" : authService.getIdGrupoTrabalho()
            };

            if (readonly)
                controller.readonly = readonly;

            ciService
                .carregarCategoria(carregarCategoria)
                .then(function(response){
                    controller.montarTela(response.data);
                })
                .finally(function(response){
                    notificationService.push(response);
                });
        };

        controller.salvar = function() {
            var temErro = Utils.validaObrigatorio();
            var temErroEtiqueta = Utils.validaEtiquetas(controller.etiquetas.length === 0);
            var temElementoPreenchido = false;
            var temErroElemento = false;
            if (controller.item.arvore == "M") {
                if (controller.item.elementos.length === 0 && gtService.getTopicos().length === 0) {
                    temElementoPreenchido = true;
                }
                temErroElemento = Utils.validaModelo(temElementoPreenchido);
            } else {
                for (var iEL = 0; iEL < controller.item.elementos.length; iEL++) {
                    var elemento = controller.item.elementos[iEL];
                    if (elemento.texto) {
                        temElementoPreenchido = true;
                    }
                }
                temErroElemento = Utils.validaElementos(!temElementoPreenchido);
            }

            if (temErro || temErroEtiqueta || temErroElemento) {
                if (temErroElemento) {
                    if (controller.item.arvore == "T" || controller.item.arvore == "S") {
                        notificationService.error("ELEMENTOS_OBRIGATORIOS_018");
                    } else {
                        notificationService.error("ELEMENTOS_OBRIGATORIOS_019");
                    }
                }
                notificationService.error("CAMPOS_OBRIGATORIOS_TOPICO_017");
                return;
            }

            if (controller.item.titulo.length < 3 ) {
                 notificationService.error('CAMPO_TAMANHO_MINIMO_025', ['Título', '3']);
                return;
            }

            if (controller.excluir) {
                ciService
                    .remove(controller.item)
                    .then(function(response){
                        $window.location.href = $contextUrl;
                    })
                    .finally(function(response){
                        notificationService.push(response);
                    });
            } else {
                var taxonomiasEtiquetaInseridas = [];
                for(var indEti = 0; indEti < controller.etiquetas.length; indEti++) {
                    var taxonomiaEtiqueta = null;
                    for(var indTaxEnt = 0; indTaxEnt < controller.item.taxonomiasEtiqueta.length; indTaxEnt++) {
                        if (controller.item.taxonomiasEtiqueta[indTaxEnt].etiquetaGT && controller.etiquetas[indEti].id == controller.item.taxonomiasEtiqueta[indTaxEnt].etiquetaGT.id) {
                            taxonomiaEtiqueta = controller.item.taxonomiasEtiqueta[indTaxEnt];
                            break;
                        }
                    }
                    if (!taxonomiaEtiqueta) {
                        taxonomiaEtiqueta = {
                            "idEtiquetaGT" : controller.etiquetas[indEti].id,
                            "idCategoria" : controller.item.id,
                            "ordem" : indEti
                        };
                    }
                    taxonomiasEtiquetaInseridas.push(taxonomiaEtiqueta);
                }
                controller.categoriaItem.categoria.taxonomiasEtiqueta = taxonomiasEtiquetaInseridas;

                var taxonomiasCategoriaInseridas = [];
                for(var indTop = 0; indTop < gtService.getTopicos().length; indTop++) {
                    var topicoIn = gtService.topicos[indTop];
                    var taxonomiaCategoria = null;
                    for(var indTaxoCat = 0; indTaxoCat < controller.categoriaItem.taxonomiasCategoriaNovas.length; indTaxoCat++) {
                        var taxCatIn = controller.categoriaItem.taxonomiasCategoriaNovas[indTaxoCat];
                        if (taxCatIn.categoriaItem && topicoIn.id == taxCatIn.categoriaItem.id) {
                            taxonomiaCategoria = taxCatIn;
                            break;
                        }
                    }
                    if (!taxonomiaCategoria) {
                        taxonomiaCategoria = {
                            "idModeloSentenca" : controller.item.id,
                            "idCategoria" : topicoIn.id,
                            "ordem" : indTop
                        };
                    }
                    taxonomiasCategoriaInseridas.push(taxonomiaCategoria);
                }
                controller.categoriaItem.taxonomiasCategoriaNovas = taxonomiasCategoriaInseridas;

                if (controller.compartilhar) {
                    controller.item.compartilhar = "S";
                } else {
                    controller.item.compartilhar = "N";
                }

                if (controller.item.arvore == "T") {
                    if (controller.categoriasTopicos.length > 0) {
                        controller.item.idPai = controller.categoriasTopicos[0].id;
                    } else {
                        controller.item.idPai = controller.categoriaItem.paiGeral.id;
                    }
                }

                controller.categoriaItem.categoria = controller.item;

                for(var indEleNov = 0; indEleNov < controller.categoriaItem.categoria.elementos.length; indEleNov++) {
                    var elementoNovo = controller.categoriaItem.categoria.elementos[indEleNov];
                    elementoNovo.ordem = indEleNov + 1;
                }
                ciService
                    .salvarTopico(controller.categoriaItem)
                    .then(function(response){
                        var retorno = response.data;
                        notificationService.success("DADOS_SALVOS_SUCESSO_000");
                        gtService.carregarMenu();
                        controller.atualizaRegistro(retorno);
                    });
            }
        };

        controller.atualizaRegistro = function(categoriaNova) {
            var data = new Date();
            $window.location.href = "#/grupo-trabalho/" + categoriaNova.arvore + "/" + categoriaNova.id + "/" + data.getMilliseconds();
        };

        controller.cloneItem = function(item){
            var elemento = {
                "usuarioAtualizacao": authService.getUsername(),
                "id": item.id,
                "idGrupoTrabalho" : authService.getIdGrupoTrabalho()
            };

            ciService
                .cloneItem(elemento)
                .then(function(response){
                    $window.location.href = $contextUrl;
                })
                .finally(function(response){
                    notificationService.push(response);
                });
        };

        controller.excluirItem = function(item) {
            var elemento = {
                "usuarioAtualizacao": authService.getUsername(),
                "id": item.id,
                "versao" : item.versao
            };

            ciService
                .remove(elemento)
                .then(function(response){
                    $window.location.href = $contextUrl;
                })
                .finally(function(response){
                    notificationService.push(response);
                });
        };

        controller.preview = function(id) {
            modalService.open( 'previewGt', {
                confirmButton: "Fechar",
                previewGtId: id
            });
        };

        controller.addSentenca = function(item){
            var sentenca = sentencaService.getSentenca();

            if(item.arvore == 'T') {
                item = _.pick(item, 'id', 'titulo');
                sentencaService.addTopicos(item);
            }
            if(item.arvore == 'M') {
                item = _.pick(item, 'id', 'titulo');
                sentencaService.addModelos(item);
            }
        };

        controller.getTopicos = function(){
            return gtService.getTopicos();
        };

    /* TODO Comentado para posterior ajuste.

		controller.startTimer = function() {
            if (angular.isDefined(controller.stop)) return;
            controller.stop = $interval(function() {
                if (controller.timer > 0) {
                    controller.timer--;
                } else {
                    controller.timer = TEMPO;
                    controller.stopTimer();
                }
            }, 1000);
        };

        controller.stopTimer = function() {
            if (angular.isDefined(controller.stop)) {
                $interval.cancel(controller.stop);
                controller.stop = undefined;
                //controller.salvar();
                controller.autoSalvar();
            }
        };

        controller.autoSalvar = function(){
            if (!controller.item.titulo) {
                controller.item.titulo = "RASCUNHO MARCELO";
            }
            var taxonomiasEtiquetaInseridas = [];
            var etiquetaGT = {
                "id" : 1
            };
            controller.etiquetas.push(etiquetaGT);

            for(var indEti = 0; indEti < controller.etiquetas.length; indEti++) {
                var taxonomiaEtiqueta = null;
                for(var indTaxEnt = 0; indTaxEnt < controller.item.taxonomiasEtiqueta.length; indTaxEnt++) {
                    var etiquetaAutosalvar = controller.item.taxonomiasEtiqueta[indTaxEnt];
                    if (etiquetaAutosalvar.etiquetaGT && controller.etiquetas[indEti].id == etiquetaAutosalvar.etiquetaGT.id) {
                        taxonomiaEtiqueta = etiquetaAutosalvar;
                        break;
                    }
                }
                if (!taxonomiaEtiqueta) {
                    taxonomiaEtiqueta = {
                        "idEtiquetaGT" : controller.etiquetas[indEti].id,
                        "idCategoria" : controller.item.id,
                        "ordem" : indEti
                    };
                }
                taxonomiasEtiquetaInseridas.push(taxonomiaEtiqueta);
            }
            controller.categoriaItem.categoria.taxonomiasEtiqueta = taxonomiasEtiquetaInseridas;

            var taxonomiasCategoriaInseridas = [];
            for(var indTop = 0; indTop < controller.topicos.length; indTop++) {
                var topicoIn = controller.topicos[indTop];
                var taxonomiaCategoria = null;
                for(var indTaxoCat = 0; indTaxoCat < controller.categoriaItem.taxonomiasCategoriaNovas.length; indTaxoCat++) {
                    var taxCatIn = controller.categoriaItem.taxonomiasCategoriaNovas[indTaxoCat];
                    if (taxCatIn.categoriaItem && topicoIn.id == taxCatIn.categoriaItem.id) {
                        taxonomiaCategoria = taxCatIn;
                        break;
                    }
                }
                if (!taxonomiaCategoria) {
                    taxonomiaCategoria = {
                        "idModelo" : controller.item.id,
                        "idCategoria" : topicoIn.id,
                        "ordem" : indTop
                    };
                }
                taxonomiasCategoriaInseridas.push(taxonomiaCategoria);
            }
            controller.categoriaItem.taxonomiasCategoriaNovas = taxonomiasCategoriaInseridas;

            if (controller.compartilhar) {
                controller.item.compartilhar = "S";
            } else {
                controller.item.compartilhar = "N";
            }
            controller.categoriaItem.categoria = controller.item;

            for(var indEleNov = 0; indEleNov < controller.categoriaItem.categoria.elementos.length; indEleNov++) {
                var elementoNovo = controller.categoriaItem.categoria.elementos[indEleNov];
                elementoNovo.ordem = indEleNov + 1;
            }
            ciService
                .salvarTopico(controller.categoriaItem)
                .then(function(response){
                    //controller.item = response.data;
                    var item = response.data;
                    controller.item = null;
                    controller.categoriaItem = false;
                    controller.etiquetas = [];
                    controller.topicos = [];
                    controller.categorias = [];
                    controller.carregaElementoPadrao(item.arvore, item.id);
                });
        }*/

        /* ------------------
         * Carrega Dados
         * -----------------*/
         if ($routeParams.tipo) {
             controller.carregaElementoPadrao($routeParams.tipo, $routeParams.id);
         }
    }]);
