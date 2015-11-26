(function() {
  'use strict';

  angular
    .module('rhdemo')
    .factory('UfService', UfService);

  UfService.$inject = ['PlcEntityService'];

  function UfService(PlcEntityService) {

  	 
  	var Service = $class(PlcEntityService, {

        constructor: function() {
            PlcEntityService.call(this, {
                 type: 'unidadefederativa'
            });
        }
       
    });


    return new Service();
  }
  
})();
