angular.module('cronos')
    .factory('pesquisaGeralService', ['DeferService', 'ciService', 'authService', function pesquisaGeralServiceFactory(DeferService, ciService, authService) {
    'use strict';

    var PesquisaGeralService = $class(DeferService, {
        isWorking: function() {
            return ciService.isWorking();
        },
        pesquisarTopico: function(pesquisa, tipoPesquisa){
            return this._pesquisar('T', pesquisa, tipoPesquisa);
        },
        pesquisarModelo: function(pesquisa, tipoPesquisa){
            return this._pesquisar('M', pesquisa, tipoPesquisa);
        },
        _pesquisar: function(tipo, pesquisa, tipoPesquisa) {

            var pesquisaGeralTopicoDTO = {
                "part" : pesquisa,
                "tipoArvore" : tipo,
                "idGrupoTrabalho" : authService.getIdGrupoTrabalho(),
                "tipo" : tipoPesquisa
            };

            return ciService.buscarPesquisaGeral(pesquisaGeralTopicoDTO);
        }
    });
    return new PesquisaGeralService();
}]);
