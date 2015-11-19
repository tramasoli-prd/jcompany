(function() {
	'use strict';

	angular
	.module('rhdemo')
	.config(UfRouterConfig);


	function UfRouterConfig($stateProvider, $urlRouterProvider) {


		$stateProvider
		.state('uf', {
			url: '/uf',
			templateUrl: 'app/uf/uf.html',
			controller: 'UfController',
			controllerAs: 'ufController'
		});
	}

})();


