
(function() {
	'use strict';

	angular.module('rhdemo').controller('FuncionarioController', FuncionarioController );

	/** @ngInject */
	function FuncionarioController($scope, $controller, FuncionarioService) {

		var vm = this;

		$scope.$baseService = FuncionarioService;
		$scope.$baseRoute = 'funcionario';
    

		$scope.detalhes = [
		                   {
		                	   titulo: "Dependente",
		                	   name: "dependente",
		                	   template: "app/funcionario/funcionariodet.html"
		                   },
		                   {
		                	   titulo: "Histórico Profissional",
		                	   name: "historicoProfissional",
		                	   template: "app/funcionario/funcionariodet2.html"
		                   }
		                   ];



		$scope.columnDefs = [
		                     { field: 'id', displayName: 'label.funcionario.id',  headerCellFilter:'translate', width: '5%'},
		                     { field: 'nome', displayName: 'label.funcionario.nome',headerCellFilter:'translate', width: '20%'},
		                     { field: 'email', displayName: 'label.funcionario.email', headerCellFilter:'translate', width: '20%'},
		                     { field: 'cpf', displayName: 'label.funcionario.cpf', headerCellFilter:'translate', width: '15%'},
		                     { field: 'dataNascimento', displayName: 'label.funcionario.dataNascimento', headerCellFilter:'translate', width: '15%', cellFilter: 'date:\'dd/MM/yyyy\''},
		                     { field: 'departamento.descricao', displayName: 'label.funcionario.departamento', headerCellFilter:'translate', width: '20%'}
		                     ]

		$scope.uploaderFilter =[

		                        {
		                        	name: 'imageFilter',
		                        	fn: function(item /*{File|FileLikeObject}*/, options) {
		                        		var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
		                        		return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
		                        	}
		                        }]

    angular.extend(vm, $controller('PlcBaseController', {$scope: $scope}));

	}

})();


