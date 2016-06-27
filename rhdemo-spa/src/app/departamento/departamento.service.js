(function() {
  'use strict';

  angular
    .module('rhdemo')
    .factory('DepartamentoService', DepartamentoService);

  /** @ngInject */
  function DepartamentoService(PlcEntityService, PlcInherit) {


    var Service = PlcInherit.createClass(PlcEntityService, {

      constructor: function() {
        PlcEntityService.call(this, {
          type: 'departamento',
          applyMetadata: false
        });
      }

    });


    return new Service();
  }

})();
