#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
(function() {
	'use strict';

	angular
	.module('${artifactId}')
	.config(RouterConfig);

	/** @ngInject */
	function RouterConfig($stateProvider, $urlRouterProvider, $locationProvider, $contextUrl) {

		// the log-on screen
		$stateProvider.state('login',{
			url: '/plc-login',
			templateUrl: 'app/login/login.html',
			controller: 'PlcLoginController',
			controllerAs: 'plcLoginController',
			access: 'public'
		});
		
		$stateProvider
		.state('app', {
			url: '/app',
			templateUrl: 'app/app.html'
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
		$urlRouterProvider.otherwise('/app/inicial');
	}
})();


