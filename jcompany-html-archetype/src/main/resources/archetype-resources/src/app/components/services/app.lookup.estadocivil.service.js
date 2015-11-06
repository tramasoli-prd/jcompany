/**=========================================================
 * Provides a static lookup from a EstadoCivil Enum
 * @leandro.lima
 =========================================================*/
(function() {
  'use strict';

  angular
    .module('${artifactId}')
    .factory('appLookupEstadoCivilService', AppLookupEstadoCivilService);

  AppLookupEstadoCivilService.$inject = ['PlcEntityService'];

  /** @ngInject */
  function AppLookupEstadoCivilService(PlcEntityService) {
  	 
  	var service = $class(PlcEntityService, {

        constructor: function() {
            PlcEntityService.call(this, {
                 type: 'lookup/estadocivil'
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
