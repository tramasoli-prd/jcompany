(function() {
  'use strict';

  angular
    .module('rhdemo')
    .factory('UfService', UfService);

  /** @ngInject */
  function UfService(PlcEntityService, PlcInherit) {


    var Service = PlcInherit.createClass(PlcEntityService, {

      constructor: function() {
        PlcEntityService.call(this, {
          type: 'unidadefederativa'
        });
      }

    });


    return new Service();
  }

})();
