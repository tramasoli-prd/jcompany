angular.module('cronos')
    .factory('gtService', ['EntityService', 'authService', function gtServiceFactory(EntityService, authService) {
    'use strict';

    var GrupoTrabalhoService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'grupoTrabalho'
            });
            this.result = [];
            this.topicos = [];
        },

        /* METODOS REST */
        _findTreeView: function(idGrupoTrabalho) {
            return this._get('/findTreeView/' + idGrupoTrabalho);
        },
        _findByUsuario: function(idUsuario) {
            return this._get('/findByUsuario/' + idUsuario);
        },
        _alteraPadrao: function(grupo) {
            return this._post('/alteraPadrao',  grupo);
        },

        /* METODOS DE INTERFACE */
        carregarMenu : function() {
            var service = this;
            service
                ._findTreeView(authService.getIdGrupoTrabalho(), {})
                .then(function(response) {
                        service.result = response.data;
                    });
        },
        clear: function() {
            this.topicos = [];
        },
        getMenu : function() {
            return this.result.itens;
        },
        setModeloScreen : function() {
            authService.setModeloScreen(true);
        },
        getTopicos: function() {
            return this.topicos;
        },
        addTopicos: function(topico) {
            var top = _.pick(topico, 'id', 'titulo');
            if(_.where(this.topicos, top).length === 0){
                this.topicos.push(top);
            }
        }

        /* METODO DE ATRIBUTOS */
    });

    return new GrupoTrabalhoService();
}]);
