
(function() {
	'use strict';

	angular.module('rhdemo').controller('DepartamentoController', DepartamentoController);

  /** @ngInject */
	function DepartamentoController($scope, $controller, DepartamentoService) {

		var vm = this;

    // required atributes in scope for inheritance.
    $scope.$baseService = DepartamentoService;
    $scope.$baseRoute = 'departamento';

    vm.beforeEdit  =  function (id) {
      console.log(id);
    };

    angular.extend(vm, $controller('PlcBaseController', {$scope: $scope}));

		$scope.columnDefs = [
		                     { field: 'id', displayName: 'label.departamento.id', headerCellFilter:'translate', width: '10%'},
		                     { field: 'descricao',  displayName:'label.departamento.descricao', headerCellFilter:'translate', width: '50%'},
		                     { field: 'departamentoPai.descricao', displayName: 'label.departamento.departamentoPai', headerCellFilter:'translate', width: '40%'}
		                    ]


	}

})();


