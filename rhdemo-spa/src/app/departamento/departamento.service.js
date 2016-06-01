(function() {
  'use strict';

  angular
    .module('rhdemo')
    .factory('DepartamentoService', DepartamentoService);

  /** @ngInject */
  function DepartamentoService(PlcEntityService, $class) {


    var Service = $class.createClass(PlcEntityService, {

      constructor: function() {
        PlcEntityService.call(this, {
          type: 'departamento'
        });
      }

    });


    return new Service();
  }

})();
