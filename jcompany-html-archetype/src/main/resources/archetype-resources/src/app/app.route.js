#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
(function() {
	'use strict';

	angular
	.module('${artifactId}')
	.config(RouterConfig);

	/** @ngInject */
	function RouterConfig($stateProvider, $urlRouterProvider) {

		$stateProvider
		.state('home', {
			url: '/',
			templateUrl: 'app/inicial/inicial.html'
		});

		$urlRouterProvider.otherwise('/');
	}

})();


