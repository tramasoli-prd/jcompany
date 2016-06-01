
(function() {
	'use strict';

	angular.module('rhdemo').controller('DepartamentoController', DepartamentoController);

  /** @ngInject */
	function DepartamentoController($scope, $controller, DepartamentoService) {

		var vm = this;

    $scope.$baseService = DepartamentoService;
    $scope.$baseRoute = 'departamento';

		$scope.columnDefs = [
		                     { field: 'id', displayName: 'label.departamento.id', headerCellFilter:'translate', width: '10%'},
		                     { field: 'descricao',  displayName:'label.departamento.descricao', headerCellFilter:'translate', width: '50%'},
		                     { field: 'departamentoPai.descricao', displayName: 'label.departamento.departamentoPai', headerCellFilter:'translate', width: '40%'}
		                    ]

    angular.extend(vm, $controller('PlcBaseController', {$scope: $scope}));

	}

})();


