(function() {
	'use strict';

	angular
	.module('rhdemo')
	.config(RouterConfig);

	/** @ngInject */
	function RouterConfig($stateProvider, $urlRouterProvider, $locationProvider, $contextUrl) {

		// the log-on screen
		$stateProvider.state('login',{
			url : $contextUrl+'/plc-login',
			templateUrl: 'app/login/login.html',
			controller: 'PlcLoginController',
			controllerAs: 'plcLoginController',
			access: 'public'
		})
		

		$stateProvider
		.state('app', {
			url: $contextUrl+'/app',
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
		
		
		$locationProvider.html5Mode(false)
		$urlRouterProvider.otherwise($contextUrl+'/app/inicial');
	}

})();


