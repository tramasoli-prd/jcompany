(function() {
	'use strict';

	angular
	.module('rhdemo')
	.run(runBlock);


	function runBlock(PlcAuthService, $rootScope, $window) {  
		//aplica segurança
		PlcAuthService.load();
	}

})();
