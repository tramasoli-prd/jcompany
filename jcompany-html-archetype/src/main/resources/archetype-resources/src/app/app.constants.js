#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/* global malarkey:false, moment:false */
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
