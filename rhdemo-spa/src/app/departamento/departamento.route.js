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
			templateUrl: 'app/app.html'
		});

		$stateProvider
		.state('departamento.sel', {
			url: '/departamentosel',
			templateUrl: 'app/departamento/departamentosel.html'
		});

		$stateProvider
		.state('departamento.man', {
			url: '/departamentoman/:id',
			templateUrl: 'app/departamento/departamentoman.html'
		});
	}
	
})();


