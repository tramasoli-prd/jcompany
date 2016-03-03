(function() {
	'use strict';

	angular
	.module('rhdemo')
	.config(UfRouterConfig);


	function UfRouterConfig($stateProvider, $urlRouterProvider, $contextUrl) {


		$stateProvider
		.state('uf', {
			abstract: true,
			url: $contextUrl+'/uf',
			templateUrl: 'app/app.html'
		});
		
		$stateProvider
		.state('uf.tab', {
			url: '/uftab',
			templateUrl: 'app/uf/uf.html',
			access: 'private'
		});
	}

})();


