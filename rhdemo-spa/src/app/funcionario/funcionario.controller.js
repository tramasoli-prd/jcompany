
(function() {
	'use strict';

	var jcompanyModule = angular.module('jcompany-view');
  	jcompanyModule.FuncionarioControllerConstructor = FuncionarioController;

	angular.module('rhdemo').controller('FuncionarioController', FuncionarioController );
	FuncionarioController.$inject = ['$injector', '$scope', '$state', '$stateParams', 'FuncionarioService', 'PlcNotificationService', 'PlcUtils', 'FileUploader', '$compile'];
	function FuncionarioController($injector, $scope, $state, $stateParams, FuncionarioService, PlcNotificationService, PlcUtils, FileUploader, $compile) {

		var vm = this;


		// required atributes in scope for inheritance.
		$scope.$baseService = FuncionarioService;
		$scope.$baseRoute = 'funcionario';
		// Using the injector for inheritance.
	    $injector.invoke(jcompanyModule.PlcBaseControllerConstructor, this, {
	        $scope: $scope,
	        $compile: $compile,
	        $state: $state,
	        $stateParams: $stateParams,
	        PlcNotificationService: PlcNotificationService,
	        PlcUtils: PlcUtils
	    }); 


 		$scope.$validationOptions = { hideErrorUnderInputs: true }; 
	   
	   
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
		                     { field: 'id', displayName: 'Id', width: '5%'},
		                     { field: 'nome', displayName: 'Nome', width: '20%'},
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


