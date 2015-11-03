angular.module('cronos')
    .directive('autoComplete',['$http', 'etiquetaGTService', 'authService', '$timeout', 'Utils', function($http, etiquetaGTService, authService, $timeout, Utils){
    return {
        restrict:'AE',
        scope:{
            listaEtiqueta:'=model',
            readonly: '='
        },
        templateUrl:'./views/directives/autocomplete/autocomplete.html',
        link:function(scope,elem,attrs){

            scope.hasWriteAccess = !scope.readonly && authService.hasWriteAccess();

            scope.suggestions = [];
            scope.selectedTags = [];

            scope.listaEtiqueta.forEach(function(elemento){
                scope.selectedTags.push(elemento);
            });
            scope.selectedIndex= 0;

            scope.textoCriarNovo = ' (Criar novo)';

            scope.removeTag=function(elemento) {
                Utils.removeElementById(scope.selectedTags, elemento);
                Utils.removeElementById(scope.listaEtiqueta, elemento);
            };
            scope.trocaSelecionado=function(indice){
                scope.selectedIndex=indice;
            };

            scope.isActive=function(indice){
                return scope.selectedIndex === indice;
            };

            scope.search=function(){
                scope.selectedTags = [];
                scope.listaEtiqueta.forEach(function(elemento){
                    scope.selectedTags.push(elemento);
                });

                if (scope.searchText.length >= 3) {
                    var listPartEtiqueta = {
                        idGrupoTrabalho : authService.getIdGrupoTrabalho(),
                        part : scope.searchText
                    };
                    etiquetaGTService.listByPartDescription(listPartEtiqueta)
                        .then(function(response){
                            var data = response.data;
                            var arrayEtiqueta = [];
                            var arrayEtiquetaT = [];
                            var arrayLinks = [];

                            var etiquetaarr = Array.prototype.slice.call(data);
                            etiquetaarr.forEach(function(elementoE) {
                                arrayEtiqueta.push(_.pick(elementoE, 'id', 'etiqueta', 'idIcone', 'icone', 'cor'));
                            });
                            //--
                            var linksarr = Array.prototype.slice.call(scope.selectedTags);
                            linksarr.forEach(function(elementoL){
                                arrayLinks.push(_.pick(elementoL, 'id', 'etiqueta', 'id', 'etiqueta', 'idIcone', 'icone', 'cor'));
                            });
                            //--
                            arrayEtiqueta.forEach(function(tes){
                                if(_.where(arrayLinks, tes).length <= 0){
                                    arrayEtiquetaT.push(tes);
                                }
                            });
                            /* Verifica se existe uma etiqueta com a string digitada */
                            var achou = false;
                            linksarr.forEach(function(eti) {
                                if (eti.etiqueta === scope.searchText) {
                                    achou = true;
                                }
                            });
                            /* Verifica se existe uma etiqueta com a string digitada */
                            arrayEtiquetaT.forEach(function(eti) {
                                if (eti.etiqueta === scope.searchText) {
                                    achou = true;
                                }
                            });
                            if (!achou) {
                                arrayEtiquetaT.push({id: 0, etiqueta: scope.searchText + scope.textoCriarNovo});
                            }
                            scope.suggestions = arrayEtiquetaT;
                            scope.selectedIndex=0;

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

            scope.addToSelectedTags=function(index){
                var suggestionTag = _.pick(scope.suggestions[index], 'id', 'etiqueta', 'id', 'etiqueta', 'idIcone', 'icone', 'cor');

                var sanitizedTag = suggestionTag.etiqueta.replace(scope.textoCriarNovo, "").trim();

                if(scope.selectedTags.indexOf(suggestionTag)===-1 && suggestionTag !== undefined){
                    if (suggestionTag.etiqueta.indexOf(scope.textoCriarNovo) > -1) {
                        scope.salvarEtiqueta(sanitizedTag).success(function(response){
                            scope.selectedTags.push({id:response.id, etiqueta:sanitizedTag});
                            scope.listaEtiqueta.push({id:response.id, etiqueta:sanitizedTag});
                        });
                    } else {
                        scope.selectedTags.push(suggestionTag);
                        scope.listaEtiqueta.push(suggestionTag);
                    }

                    scope.searchText='';
                    scope.suggestions=[];
                }
                scope.selectedIndex = 0;
            };

            scope.checkKeyDown=function(event){
                if(event.keyCode===40){
                    event.preventDefault();
                    if(scope.selectedIndex !== (scope.suggestions.length -1)) {
                        scope.selectedIndex++;
                    }
                }
                else if(event.keyCode===38){
                    event.preventDefault();
                    if(scope.selectedIndex !== 0){
                        scope.selectedIndex--;
                    }
                }
                else if(event.keyCode===13){
                    event.preventDefault();
                    scope.addToSelectedTags(scope.selectedIndex);
                }
                else if(event.keyCode===27 || event.keyCode===9){
                    scope.clearSuggestions();
                }
            };

            scope.salvarEtiqueta = function( nomeEtiqueta ){
                var etiqueta = {
                    usuarioAtualizacao: authService.getUsername(),
                    idGrupoTrabalho : authService.getIdGrupoTrabalho(),
                    etiqueta: nomeEtiqueta
                };
                return etiquetaGTService.save(etiqueta);
            };
        }
    };
}]);
