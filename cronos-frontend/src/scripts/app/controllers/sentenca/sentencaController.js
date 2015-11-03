angular
    .module('cronos')
    .controller('SentencaController', ['$window','$scope', '$contextUrl', 'notificationService', 'cpuService', 'modalService', 'sentencaService', 'gtService', 'exportarService', 'Utils',
        function SentencaController($window, $scope, $contextUrl, notificationService, cpuService, modalService, sentencaService, gtService, exportarService, Utils) {
        'use strict';

        var controller = this;
        var isVisible = false;
        var isChanged = false;
        var sentencaModalTemplate = './views/pages/sentenca/sidebar-sentenca-modal.html';

        // 9000002-78.2013.8.21.0039
        // 9000352-70.2014.8.21.3001
        // 9000001-93.2013.8.21.0039
        // 00008610520038210039
        
        var getBaseUrl = function () {
            return $contextUrl + '#/sentenca/';
        };

        var buscaDadosProcesso = function (numeroProcesso) {
            var dto = {
                numeroProcesso: numeroProcesso.replace(/\D/g,'')
            };

            cpuService
                .carregarProcesso(dto)
                .then(function(response) {
                    var data = response && response.data;
                    controller.setDadosProcesso(data);
                    sentencaService.getSentenca().numeroProcesso = data.numeroCNJ;
                },function(error){
                    sentencaService.getSentenca().numeroProcesso = sentencaService.getDadosProcesso().numeroCNJ;
                });
        };

        controller.isVisible = function() {
            return sentencaService.isVisible();
        };

        controller.isChanged = function() {
            return isChanged;
        };

        controller.setIsChanged = function (status) {
            isChanged = status;
        };

        controller.setIsChangedOnce = function(status) {
            if (!isChanged) isChanged = status;
        };

        controller.isOpen = function() {
            return sentencaService.isOpen();
        };

        controller.setOpenClose = function( status ) {
            if (!Utils.isUndefinedOrNull(status))
                sentencaService.setOpen(status);
            else
                sentencaService.setOpen(!sentencaService.isOpen());
        };

        var _isAbaSentenca = function(){
            return sentencaService.isAbaSentenca();
        };

        controller.setAbaSentenca = function(){
            sentencaService.setAbaDados(false);
            sentencaService.setAbaSentenca(true);
            controller.isAbaSentencaActive = _isAbaSentenca();
        };

        var _isAbaDados = function(){
            return sentencaService.isAbaDados();
        };
        controller.setAbaDados = function(){
            sentencaService.setAbaDados(true);
            sentencaService.setAbaSentenca(false);
            controller.isAbaDadosActive = _isAbaDados();
        };

        controller.isLoadingCPU = function() {
            return cpuService.isWorking();
        };

        controller.isLoading = function() {
            return sentencaService.isWorking();
        };

        controller.getSentenca = function() {
            return sentencaService.getSentenca();
        };

        controller.getDadosProcesso = function() {
            return sentencaService.getDadosProcesso();
        };

        controller.setDadosProcesso = function(dto) {
            return sentencaService.setDadosProcesso(dto);
        };

        controller.getSentenca = function() {
            return sentencaService.getSentenca();
        };

        controller.isSentencaGerada = function() {
            var sentencaGerada = sentencaService.getSentenca().sentencaGerada;
            return !Utils.isUndefinedOrNull(sentencaGerada) ? (sentencaGerada.length > 0) : false;
        };

        controller.isSentencaFinalizada = function() {
            var statusFinalizada = sentencaService.getSentenca().finalizada;
            return statusFinalizada.equals(Utils.SimNao.S) || false;
        };

        controller.pesquisaProcesso = function (numeroProcesso) {
            if ( !numeroProcesso || numeroProcesso === "" ) {
                notificationService.error('CAMPO_OBRIGATORIO_026', ['Número Processo']);
                return;
            }

            controller.setAbaDados();
            if ( controller.getDadosProcesso().numeroCNJ && (numeroProcesso != controller.getDadosProcesso().numeroCNJ) ) {
                modalService.open('confirm', {
                    message: 'Ao alterar o número do processo, todas as informações serão substituídas.',
                    confirmButton: 'Confirmar',
                    cancelButton: 'Cancelar'
                })
                .then( function(){
                    buscaDadosProcesso(numeroProcesso);
                },function(){
                    sentencaService.getSentenca().numeroProcesso = sentencaService.getDadosProcesso().numeroCNJ;
                });
            } else {
                buscaDadosProcesso(numeroProcesso);
            }
        };

        controller.getTopicos = function() {
            return sentencaService.getTopicos();
        };

        controller.getModelos = function() {
            return sentencaService.getModelos();
        };

        controller.getAutores = function() {
            var autores = [];
            angular.forEach(controller.getDadosProcesso().autor, function(autor, key) {
                this.push(autor.nome);
            }, autores);
            return autores.join('\n');
        };

        controller.getReus = function() {
            var reus = [];
            angular.forEach(controller.getDadosProcesso().reu, function(reu, key) {
                this.push(reu.nome);
            }, reus);
            return reus.join('\n');
        };

        controller.visualizarAutores = function() {
            modalService.open('preview', {
                headerText: 'Listagem de Autores do Processo: ' + controller.getDadosProcesso().numeroCNJ,
                confirmButton: 'Fechar',
                contentPreviewPath: sentencaModalTemplate,
                contentPreviewModel: controller.getDadosProcesso().autor
            });
        };

        controller.visualizarReus = function() {
            modalService.open('preview', {
                headerText: 'Listagem de R&eacute;us do Processo: ' + controller.getDadosProcesso().numeroCNJ,
                confirmButton: 'Fechar',
                contentPreviewPath: sentencaModalTemplate,
                contentPreviewModel: controller.getDadosProcesso().reu
            });
        };

        controller.gerarSentenca = function() {
            var numeroProcesso = sentencaService.getSentenca().numeroProcesso;
            var numeroCNJ = sentencaService.getDadosProcesso().numeroCNJ;
            var modelo = sentencaService.getModelos();

            if(!numeroProcesso || !numeroCNJ || modelo.length === 0){
                if (modelo.length === 0) {
                    notificationService.error('CAMPO_OBRIGATORIO_026', ['Modelos']);
                }
                if (!numeroCNJ) {
                    notificationService.error('CAMPO_OBRIGATORIO_026', ['Dados do Processo']);
                }
                if (!numeroProcesso) {
                    notificationService.error('CAMPO_OBRIGATORIO_026', ['Número Processo']);
                }
            } else {
                sentencaService.gerarSentenca().then(function(response){
                    controller.setIsChanged(false);
                    $window.location.href = getBaseUrl() + response.data.id;
                });
            }

        };

        controller.visualizarSentenca = function () {
            var url = getBaseUrl() + controller.getSentenca().id;
            console.log(url);
            $window.location.href = url;
        };

        controller.exportarParaArquivo = function (extensao) {
            return exportarService.exportarSentenca(controller.getDadosProcesso().numeroCNJ, extensao);
        };

        controller.salvarSentencaFinalizada = function () {
            sentencaService.finalizarSentenca().then( function (response){
                sentencaService.carregarSentenca(response.data.id, true);
                gtService.carregarMenu();
                notificationService.success("DADOS_SALVOS_SUCESSO_000");
            });
        };

        controller.finalizarSentenca = function () {
            sentencaService.verificaSeExisteFinalizada().then( function ( sentencaAntigaFinalizada ) {
                // if (sentencaAntigaFinalizada)
                console.log(sentencaAntigaFinalizada);
                modalService.open('confirm', {
                    message: 'Já existe uma sentença finalizada para este processo, que será substituída.',
                    confirmButton: 'Confirmar',
                    cancelButton: 'Cancelar'
                })
                .then( function(){
                    sentencaService.removerSentenca( sentencaAntigaFinalizada.data ).then( function (response){
                        sentencaService.finalizarSentenca().then( function (response){
                            sentencaService.carregarSentenca(response.data.id, false);
                            gtService.carregarMenu();
                        });
                    });
                });
            }, function () {
                sentencaService.finalizarSentenca().then( function (response){
                    sentencaService.carregarSentenca(response.data.id, false);
                    gtService.carregarMenu();
                });
            });
        };

        controller.copiarParaAreaDeTransferencia = function () {
            Utils.copyDataToClipboard(sentencaService.getSentenca().sentencaGerada);
            notificationService.info('Texto copiado para mem&oacute;ria.<br/>Pressione CTRL+V para colar.');
        };

        controller.verificaDadosSentenca = function () {
            $scope.$watch(function() {
                var modelos = $scope.sentencaCt.getModelos().length;
                var topicos = $scope.sentencaCt.getTopicos().length;
                return (modelos && topicos);
            }, function(newLength, oldLength) {
                controller.setIsChangedOnce((oldLength != newLength) && controller.isSentencaGerada());
            });
        };
        controller.verificaDadosSentenca();

}]);
