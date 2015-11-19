(function() {
	'use strict';

	angular
	.module('rhdemo')
	.config(AppConfig);

	function AppConfig(PlcMenuLoaderProvider) {

		PlcMenuLoaderProvider.addMenuPath('app/components/json/menu.json');

	}

})();
