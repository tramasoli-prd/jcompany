(function() {
	'use strict';

	angular
	.module('rhdemo')
	.config(UfConfig);

	UfConfig.$inject = ['PlcMenuLoaderProvider']

	function UfConfig(PlcMenuLoaderProvider) {
		PlcMenuLoaderProvider.addMenuPath('app/uf/uf.menu.json');
	}

})();
