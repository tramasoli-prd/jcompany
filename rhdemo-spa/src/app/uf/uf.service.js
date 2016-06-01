(function() {
  'use strict';

  angular
    .module('rhdemo')
    .factory('UfService', UfService);

  /** @ngInject */
  function UfService(PlcEntityService, $class) {


    var Service = $class.createClass(PlcEntityService, {

      constructor: function() {
        PlcEntityService.call(this, {
          type: 'unidadefederativa'
        });
      }

    });


    return new Service();
  }

})();
