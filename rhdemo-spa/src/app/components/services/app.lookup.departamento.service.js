/**=========================================================
 * Provides a static lookup from a EstadoCivil Enum
 * @leandro.lima
 =========================================================*/
(function() {
  'use strict';

  angular
    .module('rhdemo')
    .factory('appLookupDepartamentoService', AppLookupDepartamentoService);

  AppLookupDepartamentoService.$inject = ['PlcEntityService'];

  /** @ngInject */
  function AppLookupDepartamentoService(PlcEntityService) {
  	 
  	var service = $class(PlcEntityService, {

        constructor: function() {
            PlcEntityService.call(this, {
                 type: 'departamento'
            });
        },

        /* METODOS REST */
        _all: function() {
            return this._get('/all');
        },
       
    });


    return new service();
  }
  
})();
