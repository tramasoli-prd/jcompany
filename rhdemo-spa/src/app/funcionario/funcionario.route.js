(function() {
	'use strict';

	angular
	.module('rhdemo')
	.config(FuncionarioRouterConfig);


	function FuncionarioRouterConfig($stateProvider, $urlRouterProvider, $contextUrl) {

		$stateProvider
		.state('funcionario', {
			abstract: true,
			url: $contextUrl+'/funcionario',
			templateUrl: 'app/app.html',
			controller: 'AppController',
			controllerAs: 'appController'	
		});

		$stateProvider
		.state('funcionario.sel', {
			url: '/funcionariosel',
	        templateUrl: 'app/funcionario/funcionariosel.html',
			controller: 'FuncionarioController',
			controllerAs: 'funcionarioController'
		});

		$stateProvider
		.state('funcionario.man', {
			url: '/funcionariomdt/:id',
	        templateUrl: 'app/funcionario/funcionariomdt.html',
			controller: 'FuncionarioController',
			controllerAs: 'funcionarioController'
		});
	}
	
})();


