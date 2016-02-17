(function() {
  'use strict';

  angular
    .module('rhdemo')
    .factory('FuncionarioService', FuncionarioService);

  FuncionarioService.$inject = ['PlcEntityService', 'PlcUtils'];

  /** @ngInject */
  function FuncionarioService(PlcEntityService, PlcUtils) {

  	 
  	var Service = $class(PlcEntityService, {

        constructor: function() {
            PlcEntityService.call(this, {
                 type: 'funcionario',
                 applyMetadata: false
            });
        },

        /* METODOS REST */
        _all: function(funcionario) {
            var url = PlcUtils.encodeURIParams(funcionario);
            return this._get('/all?'+url);
        },

        save: function(data) {
            return this._post('/savefunc', data);
        },

       
    });


    return new Service();
  }
  
})();
