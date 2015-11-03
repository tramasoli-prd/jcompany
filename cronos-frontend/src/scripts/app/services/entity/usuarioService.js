angular.module('cronos')
    .factory('usuarioService', ['EntityService', function usuarioServiceFactory(EntityService) {
    'use strict';

    var UsuarioService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'usuario'
            });
        },

        getAll: function() {
            return this._get('/all');
        },
        _findByGrupoTrabalho: function(id) {
            return this._get('/findByGrupoTrabalho/' + id);
        },
        _salvarUsuario: function(dto) {
            return this._post('/salvarUsuario', dto);
        },
        _removerUsuario: function(usuario) {
            return this._post('/removerUsuario', usuario);
        },
        getByCpf: function(cpf) {
            return this._get('/findByCpf?cpf=' + cpf);
        },
        /*
         * {
         * id,
         * usuarioAtualizacao
         * }
         **/
        aceiteTermo: function(aceiteTermoDTO) {
            return this._post('/aceiteTermo', aceiteTermoDTO);
        },
        _mudaPermissoes :function(usuarioDTO) {
            return this._post('/mudaPermissoes', usuarioDTO);
        }
    });

    return new UsuarioService();
}]);
