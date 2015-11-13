#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
(function() {
	'use strict';

	angular
	.module('${artifactId}')
	.run(AppRun);

	function AppRun(PlcAuthService) {  
		//aplica segurança
		PlcAuthService.load();
	}

})();
