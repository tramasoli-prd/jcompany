(function() {
	'use strict';

	angular
	.module('rhdemo')
	.config(RouterConfig);

	/** @ngInject */
	function RouterConfig($stateProvider, $urlRouterProvider, $locationProvider) {

		// the log-on screen
		$stateProvider.state('login',{
			url : '/plc-login',
			templateUrl: 'app/login/login.html',
			controller: 'PlcLoginController',
			controllerAs: 'plcLoginController',
			access: 'public'
		})
		

		$stateProvider
		.state('app', {
			url: '/app',
			templateUrl: 'app/app.html',
			controller: 'AppController',
			controllerAs: 'appController'
		});
		
		$stateProvider
		.state('app.inicial', {
			url: '/inicial',
			parent: 'app',
			templateUrl: 'app/inicial/inicial.html',
			controller: 'InicialController',
			controllerAs: 'inicialController'
		});
		

		$urlRouterProvider.otherwise('/app/inicial');
	}

})();


