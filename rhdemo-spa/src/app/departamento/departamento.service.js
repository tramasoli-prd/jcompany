(function() {
	'use strict';

	angular
	.module('rhdemo')
	.factory('DepartamentoService', DepartamentoService);

	DepartamentoService.$inject = ['PlcEntityService', 'PlcUtils'];

	/** @ngInject */
	function DepartamentoService(PlcEntityService, PlcUtils) {


		var Service = $class(PlcEntityService, {

			constructor: function() {
				PlcEntityService.call(this, {
					type: 'departamento'
				});
			}

		});


		return new Service();
	}

})();
