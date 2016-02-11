(function() {
	'use strict';

	angular
	.module('rhdemo')
	.config(DepartamentoRouterConfig);


	function DepartamentoRouterConfig($stateProvider, $urlRouterProvider, $contextUrl) {

		$stateProvider
		.state('departamento', {
			abstract: true,
			url: $contextUrl+'/departamento',
			templateUrl: 'app/app.html',
			controller: 'AppController',
			controllerAs: 'appController'	
		});

		$stateProvider
		.state('departamento.sel', {
			url: '/departamentosel',
			templateUrl: 'app/departamento/departamentosel.html',
			controller: 'DepartamentoController',
			controllerAs: 'departamentoController'
		});

		$stateProvider
		.state('departamento.man', {
			url: '/departamentoman/:id',
			templateUrl: 'app/departamento/departamentoman.html',
			controller: 'DepartamentoController',
			controllerAs: 'departamentoController'
		});
	}
	
})();


