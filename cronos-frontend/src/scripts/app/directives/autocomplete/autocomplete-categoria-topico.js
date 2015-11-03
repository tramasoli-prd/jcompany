angular.module('cronos')
    .directive('autoCompleteCategoriaTopico',['$http', 'ciService', 'authService', function ($http, ciService, authService){
    return {
        restrict:'AE',
        scope:{
            categoriaTopicoModel:'=model',
            readonly: '='
        },
        templateUrl:'./views/directives/autocomplete/autocomplete-categoria-topico.html',
        link:function(scope,elem,attrs){

            scope.hasWriteAccess = !scope.readonly && authService.hasWriteAccess();

            scope.categorias = [];
            scope.selectedCats = [];
            scope.categoriaTopicoModel.forEach(function(elemento){
                scope.selectedCats.push(elemento);
            });

            scope.selectedIndex=-1;

            scope.textoCriarNovo = ' (Criar novo)';

            scope.removeCategoriaTopico=function(element){
                scope.selectedCats = [];
                scope.categoriaTopicoModel = [];
            };

            scope.search=function(){
                if (scope.categoriaTopico.length >= 3 && scope.selectedCats.length === 0) {

                    var autoCompleteDTO = {
                        "part" : scope.categoriaTopico,
                        "idGrupoTrabalho" : authService.getIdGrupoTrabalho(),
                        "tipoArvore" : "T"
                    };
                    ciService.listNodeByPartDescription(autoCompleteDTO)
                        .then(function(response) {

                            var data = response.data;
                            var arrayCategoria = [];
                            var arrayCategoriaT = [];
                            var arrayLinks = [];

                            var topicosarr = Array.prototype.slice.call(data);
                            topicosarr.forEach(function(elementoM) {
                                arrayCategoria.push(_.pick(elementoM, 'id', 'titulo'));
                            });

                            var linksarr = Array.prototype.slice.call(scope.selectedCats);
                            linksarr.forEach(function(elementoL){
                                arrayLinks.push(_.pick(elementoL, 'id', 'titulo'));
                            });

                            if(scope.selectedCats.indexOf(scope.categoriaTopico)===-1){
                                if(scope.categoriaTopicoModel.indexOf(scope.categoriaTopico)===-1){
                                    arrayCategoriaT.push({id: -1, titulo: scope.categoriaTopico + scope.textoCriarNovo});
                                }
                            }

                            arrayCategoria.forEach(function(tes){
                                if(_.where(arrayLinks, tes).length <= 0){
                                    arrayCategoriaT.push(tes);
                                }
                            });

                            scope.categorias = arrayCategoriaT;

                            scope.selectedIndex=-1;
                        });

                } else {
                    scope.categorias = [];
                }
            };

            scope.sanitizeSelectedSuggestionTag=function(titulo){
                return titulo.replace(scope.textoCriarNovo, "").trim();
            };

            scope.addToSelectedCats=function(index){
                var categoriaTag = scope.categorias[index];

                if(scope.selectedCats.indexOf(categoriaTag)===-1 && categoriaTag !== undefined){
                    if(categoriaTag.id < 0){
                        var sanitizedTitulo = scope.sanitizeSelectedSuggestionTag(categoriaTag.titulo);
                        categoriaTag = {
                            titulo: sanitizedTitulo,
                            idGrupoTrabalho: authService.getIdGrupoTrabalho(),
                            usuarioAtualizacao: authService.getUsername(),
                            tipoArvore: 'T'
                        };

                        ciService.saveNode(categoriaTag)
                            .then(function(response){
                                var data = response.data;

                                scope.selectedCats.push({id:data.id, titulo:data.titulo});
                                scope.categoriaTopicoModel.push({id:data.id, titulo:sanitizedTitulo});
                        });
                    } else {
                        scope.selectedCats.push({id:categoriaTag.id, titulo:categoriaTag.titulo});
                        scope.categoriaTopicoModel.push({id:categoriaTag.id, titulo:categoriaTag.titulo});
                    }

                    scope.categoriaTopico='';
                    scope.categorias=[];
                }

                scope.selectedIndex=-1;
            };

            scope.checkKeyDown=function(event){
                if(event.keyCode===40){
                    event.preventDefault();
                    if(scope.selectedIndex+1 !== scope.categorias.length){
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
                    scope.addToSelectedCats(scope.selectedIndex);
                }
            };
        },
        controller: function($scope) {
            //scope.hasWriteAccess = !scope.readonly && authService.hasWriteAccess();
        }
    };
}]);
