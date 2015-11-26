
(function() {
	'use strict';

	var jcompanyModule = angular.module('jcompany-view');
  	jcompanyModule.FuncionarioControllerConstructor = FuncionarioController;

	angular.module('rhdemo').controller('FuncionarioController', FuncionarioController );
	FuncionarioController.$inject = ['$injector', '$scope', '$state', '$stateParams', 'FuncionarioService', 'PlcNotificationService', 'PlcUtils', 'FileUploader'];
	
	function FuncionarioController($injector, $scope, $state, $stateParams, FuncionarioService, PlcNotificationService, PlcUtils, FileUploader) {


		// Using the injector for inheritance.
	    $injector.invoke(jcompanyModule.PlcBaseControllerConstructor, this, {
	        $scope: $scope,
	        $state: $state,
	        $stateParams: $stateParams,
	        $baseService: FuncionarioService,
	        $baseRoute: 'funcionario',
	        $notification : PlcNotificationService, 
	        $utils : PlcUtils
	    });


		$scope.detalhes = [
		                   {
		                	   titulo: "Dependente",
		                	   template: "app/funcionario/funcionariodet.html"
		                   },
		                   {
		                	   titulo: "Histórico Profissional",
		                	   template: "app/funcionario/funcionariodet2.html"
		                   }
		                   ];


		$scope.addItemDependente = function() {
			if(!$scope.funcionario.dependente){
				$scope.funcionario.dependente = [];
			}

			$scope.funcionario.dependente.push(new Object());
		};

		$scope.addItemHistoricoProfissional = function() {
			if(!$scope.funcionario.historicoProfissional){
				$scope.funcionario.historicoProfissional = [];
			}
			$scope.funcionario.historicoProfissional.push(new Object());
		};


		$scope.removeRowDependente = function(index){
			$scope.funcionario.dependente.splice(index, 1);
		};

		$scope.removeRowHistoricoProfissional = function(index){
			$scope.funcionario.historicoProfissional.splice(index, 1);
		};


		$scope.columnDefs = [
		                     { field: 'id', displayName: 'Id', width: '10%'},
		                     { field: 'nome', displayName: 'Nome', width: '25%'},
		                     { field: 'email', displayName: 'E-mail', width: '20%'},
		                     { field: 'cpf', displayName: 'CPF', width: '15%'},
		                     { field: 'dataNascimento', displayName: 'Nascimento', width: '10%', cellFilter: 'date:\'dd/MM/yyyy\''},
		                     { field: 'departamento.descricao', displayName: 'Departamento', width: '20%'}
		                     ]

		$scope.uploaderFilter =[

		                        {
		                        	name: 'imageFilter',
		                        	fn: function(item /*{File|FileLikeObject}*/, options) {
		                        		var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
		                        		return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
		                        	}
		                        }]

		

	}

})();


