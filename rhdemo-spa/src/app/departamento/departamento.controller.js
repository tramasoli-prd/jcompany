
(function() {
	'use strict';

	var jcompanyModule = angular.module('jcompany-view');

	jcompanyModule.DepartamentoControllerConstructor = DepartamentoController;
	angular.module('rhdemo').controller('DepartamentoController', DepartamentoController);
	DepartamentoController.$inject = ['$injector', '$scope', '$compile', '$state', '$stateParams','DepartamentoService', 'PlcNotificationService', 'PlcUtils'];
	
	function DepartamentoController($injector, $scope, $compile,  $state, $stateParams, DepartamentoService, PlcNotificationService, PlcUtils) {

	  	
		// required atributes in scope for inheritance.
		$scope.$baseService = DepartamentoService;
		$scope.$baseRoute = 'departamento';

 		// Using the injector for inheritance.
		$injector.invoke(jcompanyModule.PlcBaseControllerConstructor, this, {
        	$scope: $scope,
        	$compile: $compile,
        	$state: $state,
        	$stateParams: $stateParams,
        	PlcNotificationService: PlcNotificationService,
        	PlcUtils: PlcUtils
    	});	

		$scope.columnDefs = [
		                     { field: 'id', displayName: 'Id', width: '10%'},
		                     { field: 'descricao', displayName: 'Descrição', width: '50%'},
		                     { field: 'departamentoPai.descricao', displayName: 'Departamento Pai', width: '40%'}
		                    ]

	}

})();


