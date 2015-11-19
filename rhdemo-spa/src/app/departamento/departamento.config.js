(function() {
  'use strict';

  angular
    .module('rhdemo')
    .config(DepartamentoConfig);

	DepartamentoConfig.$inject = ['PlcMenuLoaderProvider']
 
  function DepartamentoConfig(PlcMenuLoaderProvider) {

  	PlcMenuLoaderProvider.addMenuPath('app/departamento/departamento.menu.json');
    
  }

})();
