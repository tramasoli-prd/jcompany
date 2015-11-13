(function() {
	'use strict';

	angular
	.module('rhdemo')
	.constant('$contextUrl', '/');

	angular
	.module('jcompany-view')
	.constant('$menuPath', 'app/components/json/menu.json')
	/*.constant('$backendUrl', 'http://localhost:9080/rhdemo-service');*/
	.constant('$backendUrl', 'http://localhost:7001/rhdemo-service');
	/*.constant('$backendUrl', 'http://localhost:8080/rhdemo-service');*/

	
})();
