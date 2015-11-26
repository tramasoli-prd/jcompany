
(function() {
	'use strict';

	var jcompanyModule = angular.module('jcompany-view');

	jcompanyModule.DepartamentoControllerConstructor = DepartamentoController;
	angular.module('rhdemo').controller('DepartamentoController', DepartamentoController);
	DepartamentoController.$inject = ['$injector', '$scope', '$state', '$stateParams','DepartamentoService', 'PlcNotificationService', 'PlcUtils'];
	
	function DepartamentoController($injector, $scope, $state, $stateParams, DepartamentoService, PlcNotificationService, PlcUtils) {

	    // Using the injector for inheritance.
	    $injector.invoke(jcompanyModule.PlcBaseControllerConstructor, this, {
	      $scope: $scope,
	      $state: $state,
	      $stateParams: $stateParams,
	      $baseService: DepartamentoService,
	      $baseRoute: 'departamento',
	      $notification : PlcNotificationService, 
	      $utils : PlcUtils
	    });
	

		$scope.columnDefs = [
		                     { field: 'id', displayName: 'Id', width: '10%'},
		                     { field: 'descricao', displayName: 'Descrição', width: '50%'},
		                     { field: 'departamentoPai.descricao', displayName: 'Departamento Pai', width: '40%'}
		                    ]

	}

})();


