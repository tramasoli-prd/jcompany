angular.module('cronos')
    .directive('autoCompleteModelo',['$http', 'ciService', 'authService', 'modalService', 'sentencaService', 'Utils', '$timeout', function ($http, ciService, authService, modalService, sentencaService, Utils, $timeout){
    return {
        restrict:'AE',
        scope:{
            vinculoTMmodel:'=modelo',
            classe: '@classe',
            readonly: '='
        },
        templateUrl:'./views/directives/autocomplete/autocomplete-modelo.html',
        link:function(scope,elem,attrs){

            scope.hasWriteAccess = function() {
                return (!scope.readonly && authService.hasWriteAccess());
            };

            scope.isPreviewMode = function() {
                return scope.readonly;
            };

            scope.addModeloSentenca = function(data) {
                if (data.tipoArvore === 'M') {
                    var modelo = {
                        id : data.id,
                        titulo : data.label
                    };
                    sentencaService.addModelos(modelo);
                }
            };

            scope.suggestions = [];
            scope.selectedLinks = [];

            // sentencaService.modelos.forEach(function(elemento){
            //     scope.selectedLinks.push(elemento);
            // });

            scope.selectedIndex=-1;

            scope.removeVinculoTM=function(element){
                var removerModelos = false;
                modalService.open('confirm', {
                    message: 'Deseja excluir os t&oacute;picos relacionados ao modelo?',
                    confirmButton: 'Sim',
                    cancelButton: 'N&atilde;o',
                    abortButton: 'Cancelar',
                    abortButtonActive: true
                })
                .then( function( confirmActionResponse ){
                    var idModelo = sentencaService.modelos[0].id;
                    sentencaService.getTopicosByModelo(idModelo).then(function (response) {
                        angular.forEach( response.data, function (topico) {
                            Utils.removeElementById(sentencaService.topicos, topico);
                        });
                    });
                    removerModelos = true;
                }, function( cancelActionResponse ){
                    removerModelos = true;
                }, function( abortActionResponse ){
                    removerModelos = false;
                }).finally(function () {
                    if (removerModelos) {
                        Utils.removeElementById(scope.selectedLinks, element);
                        Utils.removeElementById(sentencaService.modelos, element);
                    }
                });
            };

            scope.search=function(){
                if (scope.vinculoTM.length >= 3 && scope.selectedLinks.length === 0) {
                    var autoCompleteDTO = {
                        "idGrupoTrabalho" : authService.getIdGrupoTrabalho(),
                        "part" : scope.vinculoTM,
                        "tipoArvore" : 'M'
                    };

                    ciService.listByPartDescription(autoCompleteDTO)
                        .then(function(response) {
                            var data = response.data;
                            var arrayModelos = [];
                            var arrayModelosT = [];
                            var arrayLinks = [];

                            var modelosarr = Array.prototype.slice.call(data);
                            modelosarr.forEach(function(elementoM) {
                                arrayModelos.push(_.pick(elementoM, 'id', 'titulo'));
                            });

                            var linksarr = Array.prototype.slice.call(sentencaService.modelos);
                            linksarr.forEach(function(elementoL){
                                arrayLinks.push(_.pick(elementoL, 'id', 'titulo'));
                            });

                            arrayModelos.forEach(function(tes){
                                if(_.where(arrayLinks, tes).length <= 0){
                                    arrayModelosT.push(tes);
                                }
                            });

                            scope.suggestions = arrayModelosT;

                            scope.selectedIndex=-1;
                        });

                } else {
                    scope.suggestions = [];
                }
            };

            scope.clearSuggestions = function() {
                $timeout(function () {
                    scope.suggestions = [];
                    scope.searchText = '';
                    scope.selectedIndex= 0;
                }, 200);
            };

            scope.addToSelectedLinks=function(index){
                var suggestionTag = _.pick(scope.suggestions[index], 'id', 'titulo');

                if(scope.selectedLinks.indexOf(suggestionTag)===-1 && suggestionTag !== undefined){
                    scope.selectedLinks.push(suggestionTag);
                    sentencaService.addModelos(suggestionTag);

                    scope.vinculoTM='';
                    scope.suggestions=[];
                }
                scope.selectedIndex=-1;
            };

            scope.checkKeyDown=function(event){
                if(event.keyCode===40){
                    event.preventDefault();
                    if(scope.selectedIndex+1 !== scope.suggestions.length){
                        scope.selectedIndex++;
                    }
                }
                else if(event.keyCode===38){
                    event.preventDefault();
                    if(scope.selectedIndex-1 !== -1){
                        scope.selectedIndex--;
                    }
                }
                else if(event.keyCode===13){
                    event.preventDefault();
                    if (scope.selectedIndex === -1) {
                        scope.selectedIndex = 0;
                    }
                    scope.addToSelectedLinks(scope.selectedIndex);
                }
                else if(event.keyCode===27 || event.keyCode===9){
                    scope.clearSuggestions();
                }
            };
        },
        controller: function($scope) {

            $scope.hasWriteAccess = function() {
                return (!$scope.readonly && authService.hasWriteAccess());
            };

            $scope.isPreviewMode = function() {
                return $scope.readonly;
            };

            $scope.preview = function(selectedLink) {
                modalService.open( 'previewGt', {
                    confirmButton: "Fechar",
                    previewGtId: selectedLink.id
                });
            };
        }
    };
}]);
