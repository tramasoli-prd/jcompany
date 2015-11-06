/* global malarkey:false, moment:false */
(function() {
	'use strict';

	angular
	.module('${artifactId}')
	.constant('$contextUrl', '/');

	angular
	.module('jcompany-view')
	.constant('$menuPath', 'app/components/json/menu.json')
	.constant('$backendUrl', 'http://localhost:7001/${artifactId}-service');

	
})();
