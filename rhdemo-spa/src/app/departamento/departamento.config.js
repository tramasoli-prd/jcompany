(function() {
	'use strict';

	angular
	.module('rhdemo')
	.config(DepartamentoConfig);

	DepartamentoConfig.$inject = ['PlcMenuLoaderProvider']

	function DepartamentoConfig(PlcMenuLoaderProvider) {

		PlcMenuLoaderProvider.addMenuJson(JSON.parse(
				'{'+
				'"text": "Departamento",'+
				'"position" : 3,'+
				'"sref": "departamento.sel",'+
				'"icon": "menu-icon fa fa-table",'+
				'"translate": "label.menu.departamento"'+
				'}'
		));

	}

})();
