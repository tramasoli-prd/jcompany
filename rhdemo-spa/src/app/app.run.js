(function() {
	'use strict';

	angular
	.module('rhdemo')
	.run(runBlock);


	function runBlock(PlcAuthService) {  
		//aplica segurança
		PlcAuthService.load();
	}

})();
