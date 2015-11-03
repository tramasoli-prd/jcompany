angular.module('cronos')
    .directive('autoCompleteTopico',['$http', 'ciService', 'authService', 'modalService', 'sentencaService', 'Utils', '$timeout', 'gtService', function ($http, ciService, authService, modalService, sentencaService, Utils, $timeout, gtService){
    return {
        restrict:'AE',
        scope:{
            vinculoTMmodel:'=model',
            classe: '@classe',
            readonly: '=',
            chamador: '@chamador'
        },
        templateUrl:'./views/directives/autocomplete/autocomplete-topico.html',
        link:function(scope,elem,attrs){

            scope.hasWriteAccess = function() {
                return (!scope.readonly && authService.hasWriteAccess());
            };

            scope.addTopico = function (data, chamador) {
                if (data.tipoArvore === 'T') {
                    var topico = {
                        id : data.id,
                        titulo : data.label
                    };
                    if (chamador === "sentenca") {
                        sentencaService.addTopicos(topico);
                    } else {
                        gtService.addTopicos(topico);
                    }
                }
            };

            scope.getTopicos = function() {
                return gtService.getTopicos();
            };

            scope.isPreviewMode = function() {
                return scope.readonly;
            };

            scope.suggestions = [];
            scope.selectedLinks = [];

            if (scope.chamador === "sentenca") {
                sentencaService.topicos.forEach(function(elemento){
                    scope.selectedLinks.push(elemento);
                });
            } else {
                gtService.topicos.forEach(function(elemento){
                    scope.selectedLinks.push(elemento);
                });
            }

            scope.selectedIndex=-1;

            scope.removeVinculoTM=function(element){
                Utils.removeElementById(scope.selectedLinks, element);

                if (scope.chamador === "sentenca") {
                    Utils.removeElementById(sentencaService.topicos, element);
                } else {
                    Utils.removeElementById(gtService.topicos, element);
                }
            };
            scope.trocaSelecionado=function(indice){
                scope.selectedIndex=indice;
            };
            scope.search=function(){
                if (scope.vinculoTM.length >= 3) {
                    var autoCompleteDTO = {
                        "idGrupoTrabalho" : authService.getIdGrupoTrabalho(),
                        "part" : scope.vinculoTM
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

                            var linksarr = false;
                            if (scope.chamador === "sentenca") {
                                linksarr = Array.prototype.slice.call(sentencaService.topicos);
                            } else {
                                linksarr = Array.prototype.slice.call(gtService.topicos);
                            }

                            linksarr.forEach(function(elementoL){
                                arrayLinks.push(_.pick(elementoL, 'id', 'titulo'));
                            });

                            arrayModelos.forEach(function(tes){
                                if(_.where(arrayLinks, tes).length === 0){
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
                    scope.vinculoTM = '';
                    scope.selectedIndex=-1;
                }, 200);
            };

            scope.addToSelectedLinks=function(index){
                var suggestionTag = scope.suggestions[index];

                if(scope.selectedLinks.indexOf(suggestionTag)===-1 && suggestionTag !== undefined){
                    scope.selectedLinks.push(suggestionTag);
                    if (scope.chamador === "sentenca") {
                        sentencaService.addTopicos(suggestionTag);
                    } else {
                        gtService.addTopicos(suggestionTag);
                    }

                    scope.vinculoTM='';
                    scope.suggestions=[];
                }
                scope.selectedIndex=-1;
            };

            scope.swap = function(index, pos){
                return scope.vinculoTMmodel.swap(index, pos);
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
