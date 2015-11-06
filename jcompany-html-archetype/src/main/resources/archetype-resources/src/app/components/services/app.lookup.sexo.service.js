#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**=========================================================
 * Provides a static lookup from a Sexo Enum
 * @leandro.lima
 =========================================================*/
(function() {
  'use strict';

  angular
    .module('${artifactId}')
    .factory('appLookupSexoService', AppLookupSexoService);

  AppLookupSexoService.$inject = ['PlcEntityService'];

  /** @ngInject */
  function AppLookupSexoService(PlcEntityService) {
  	 
  	var service = $class(PlcEntityService, {

        constructor: function() {
            PlcEntityService.call(this, {
                 type: 'lookup/sexo'
            });
        },

        /* METODOS REST */
        _all: function() {
            return this._get('/');
        },
       
    });


    return new service();
  }
  
})();
