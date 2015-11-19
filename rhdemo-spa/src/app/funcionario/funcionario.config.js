(function() {
  'use strict';

  angular
    .module('rhdemo')
    .config(FuncionarioConfig);

	FuncionarioConfig.$inject = ['PlcMenuLoaderProvider']
 
  function FuncionarioConfig(PlcMenuLoaderProvider) {

  	PlcMenuLoaderProvider.addMenuPath('app/funcionario/funcionario.menu.json');
    
  }

})();
