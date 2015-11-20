#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
	(function() {
		'use strict';

		angular
		.module('${artifactId}')
		.config(AppConfig);

		function AppConfig(PlcMenuLoaderProvider) {

			PlcMenuLoaderProvider.addMenuPath('app/components/json/menu.json');

		}

	})();
