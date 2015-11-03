angular
    .module('cronos')
    .factory('sentencaService', ['EntityService', 'authService', 'ciService', 'Utils', function sentencaServiceFactory(EntityService, authService, ciService, Utils) {
    'use strict';

    var SentencaService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'sentenca'
            });
            this.openStatus = false;
            this.loadedStatus = false;
            this.visibleStatus = false;
            this.abaSentenca = true;
            this.abaDados = false;
            //Objeto principal do servico
            this.sentenca = {
                numeroProcesso : null,
                finalizada : 'N',
                dadosProcesso : {},
                modelo : {},
                topicos : []
            };
            //--
            this.dadosProcesso = {
                assuntoCNJ: null,
                classeCNJ: null,
                codigoComarca: null,
                juizado: null,
                numeroCNJ: null,
                numeroFormatado : null,
                reu: [],
                autor: [],
                valorAcao: null,
                vara: null
            };

            this.modelos = [];
            this.topicos = [];
        },

        /* METODOS REST */
        testarGerarDocumentoSentenca: function(id) {
            return this._get('/testarGerarDocumentoSentenca/' + id);
        },
        _carregarSentencaId : function(sentencaDTO){
            return this._post('/carregarSentencaId', sentencaDTO);
        },
        _salvarSentenca : function(sentencaDTO){
            return this._post('/salvarSentenca', sentencaDTO);
        },
        _finalizarSentenca : function(sentencaDTO){
            return this._post('/finalizarSentenca', sentencaDTO);
        },
        _removerSentenca : function(sentencaDTO) {
            return this._post('/removerSentenca', sentencaDTO);
        },
        _findByStatusFinalizada : function(sentencaDTO) {
            return this._post('/findByStatusFinalizada/', sentencaDTO);
        },

        /* METODOS DE INTERFACE */
        gerarSentenca : function(){
            var service = this;
            if (service.getModelos()[0]) {
                service.sentenca.modelo = service.getModelos()[0];
            } else {
                service.sentenca.modelo = {};
            }
            service.sentenca.topicos = service.getTopicos();
            service.sentenca.idGrupoTrabalho = (service.sentenca.idGrupoTrabalho ? service.sentenca.idGrupoTrabalho : authService.getIdGrupoTrabalho());
            service.sentenca.usuarioAtualizacao = authService.getUsername();
            service.sentenca.numeroProcesso = (service.sentenca.numeroProcesso ? service.sentenca.numeroProcesso.replace(/\D/g,'') : "");
            return service._salvarSentenca(service.getSentenca());
        },
        carregarSentenca : function(id, open){
            var service = this;
            var dto = { id: id };
            if (id) {
                service._carregarSentencaId(dto).then(function (response){
                    service.setSentenca(response && response.data);
                    if (!Utils.isUndefinedOrNull(open))
                        service.setOpen(open);
                    else
                        service.setOpen(true);

                });
            }
        },
        clear: function() {
            //Objeto principal do servico
            this.sentenca = {
                numeroProcesso : null,
                finalizada : 'N',
                dadosProcesso : {},
                modelo : {},
                topicos : []
            };
            //--
            this.dadosProcesso = {
                assuntoCNJ: null,
                classeCNJ: null,
                codigoComarca: null,
                juizado: null,
                numeroCNJ: null,
                numeroFormatado : null,
                reu: [],
                autor: [],
                valorAcao: null,
                vara: null
            };
            this.modelos = [];
            this.topicos = [];
        },
        isOpen : function() {
            return this.openStatus;
        },
        isLoaded : function() {
            return this.loadedStatus;
        },
        isVisible : function() {
            return this.visibleStatus;
        },
        setOpen : function( status ) {
            this.visibleStatus = true;
            this.loadedStatus = true;
            this.openStatus = status;
        },
        setClose : function() {
            this.visibleStatus = false;
            this.openStatus = false;
        },
        isAbaSentenca : function() {
            return this.abaSentenca;
        },
        isAbaDados : function() {
            return this.abaDados;
        },
        setAbaSentenca : function(valor) {
            this.abaSentenca = valor;
        },
        setAbaDados : function(valor) {
            this.abaDados = valor;
        },

        getTopicosByModelo : function (idModelo) {
            return ciService.listTopicosByIdModelo(idModelo);
        },
        /* METODO DE ATRIBUTOS */
        getSentenca: function() {
            return this.sentenca;
        },
        setSentenca: function(dto) {
            var service = this;
            service.sentenca = dto;
            if (service.sentenca.modelo) {
                service.addModelos(service.sentenca.modelo);
            }
            service.sentenca.topicos.forEach(function(elemento){
                service.addTopicos(elemento);
            });
            service.setDadosProcesso(service.sentenca.dadosProcesso);
        },
        getDadosProcesso: function() {
            return this.dadosProcesso;
        },
        setDadosProcesso: function(dto) {
            this.dadosProcesso = dto;
        },
        getTopicos: function() {
            return this.topicos;
        },
        setTopicos: function(lista) {
            this.topicos = lista;
        },
        addTopicos: function(topico) {
            var top = _.pick(topico, 'id', 'titulo');
            if(_.where(this.topicos, top).length === 0){
                this.topicos.push(top);
            }
        },
        getModelos: function() {
            return this.modelos;
        },
        setModelos: function(lista) {
            this.modelos = lista;
        },
        addModelos: function(modelo) {
            var sentenca = this;
            if(sentenca.modelos.length === 0){
                sentenca.modelos.push(_.pick(modelo, 'id', 'titulo'));

                ciService.listTopicosByIdModelo(modelo.id)
                .then(function(response) {
                    var topicos = response.data;

                    topicos.forEach(function(elemento){
                        elemento = _.pick(elemento, 'id', 'titulo');
                        if(_.where(sentenca.topicos, elemento).length === 0){
                            sentenca.topicos.push(elemento);
                        }
                    });
                });
            }
        },

        /* REGRAS E AÇOES */
        verificaSeExisteFinalizada: function() {
            var service = this;
            return service._findByStatusFinalizada( service.getSentenca() );
        },
        removerSentenca: function ( sentencaDTO ) {
            var service = this;
            console.log(sentencaDTO);
            return service._removerSentenca( sentencaDTO );
        },
        finalizarSentenca: function() {
            var service = this;
            var sentenca = service.getSentenca();
            return service._finalizarSentenca( sentenca );
        }

    });
    return new SentencaService();
}]);
