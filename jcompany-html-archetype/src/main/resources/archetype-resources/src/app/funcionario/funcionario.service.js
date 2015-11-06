(function() {
  'use strict';

  angular
    .module('${artifactId}')
    .factory('funcionarioService', FuncionarioService);

  FuncionarioService.$inject = ['PlcEntityService', 'PlcUtils'];

  /** @ngInject */
  function FuncionarioService(PlcEntityService, PlcUtils) {

  	 
  	var Service = $class(PlcEntityService, {

        constructor: function() {
            PlcEntityService.call(this, {
                 type: 'funcionario'
            });
        },

        /* METODOS REST */
        _all: function(funcionario) {
            var url = PlcUtils.encodeURIParams(funcionario);
            return this._get('/all?'+url);
        },

        find: function(filter, pagination) {

            pagination = pagination || {};

            var path = this.rest.toMatrixPath(this._entityPath('/find'), filter);

            var config = {
                params: {
                    page: pagination.page || 0,
                    size: pagination.size || 20,
                    dir: pagination.dir || 'ASC', // ASC|DESC
                    order: pagination.order || ''
                }
            };

            return this.rest.get(path, config);
        },

        create: function() {
            return this._get('/create');
        },

        edit: function(id) {
            return this._get('/get/' + id);
        },

        save: function(data) {
            return this._post('/savefunc', data);
        },

        remove: function(data) {
            return this._post('/remove', data);
        }
       
    });


    return new Service();
  }
  
})();
