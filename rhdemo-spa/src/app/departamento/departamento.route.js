(function() {
	'use strict';

	angular
	.module('rhdemo')
	.config(DepartamentoRouterConfig);


	function DepartamentoRouterConfig($stateProvider, $urlRouterProvider) {


		$stateProvider
		.state('departamentosel', {
			url: '/departamentosel',
			templateUrl: 'app/departamento/departamentosel.html',
			controller: 'DepartamentoController',
			controllerAs: 'departamentoController'
		});

		$stateProvider
		.state('departamentoman', {
			url: '/departamentoman/:id',
			templateUrl: 'app/departamento/departamentoman.html',
			controller: 'DepartamentoController',
			controllerAs: 'departamentoController'
		});
	}
	
})();


