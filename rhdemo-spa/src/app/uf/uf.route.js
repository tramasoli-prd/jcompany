(function() {
	'use strict';

	angular
	.module('rhdemo')
	.config(UfRouterConfig);


	function UfRouterConfig($stateProvider, $urlRouterProvider) {


		$stateProvider
		.state('uf', {
			abstract: true,
			url: '/uf',
			templateUrl: 'app/app.html',
			controller: 'AppController',
			controllerAs: 'appController'	
		});
		
		$stateProvider
		.state('uf.tab', {
			url: '/uftab',
			templateUrl: 'app/uf/uf.html',
			controller: 'UfController',
			controllerAs: 'ufController',
			access: 'private'
		});
	}

})();


