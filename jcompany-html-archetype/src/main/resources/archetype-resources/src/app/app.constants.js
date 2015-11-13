#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

(function() {
	'use strict';

	angular
	.module('${artifactId}')
	.constant('$contextUrl', '/');

	angular
	.module('jcompany-view')
	.constant('$menuPath', 'app/components/json/menu.json')
	.constant('$backendUrl', '${BACKEND-URL}');

	
})();
