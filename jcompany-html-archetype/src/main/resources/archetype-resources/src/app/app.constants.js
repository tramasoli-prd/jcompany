#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
	
	(function() {
		'use strict';

		angular
		.module('${artifactId}')
		.constant('$appVersion', '0.0.1')
		.constant('$contextUrl', '');

		angular
		.module('jcompany-view')
		.constant('$menuPath', 'app/components/json/menu.json')
		.constant('$backendUrl', '${BACKEND-URL}');

		
	})();
