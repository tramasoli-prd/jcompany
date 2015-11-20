
(function() {
	'use strict';

	angular
	.module('rhdemo')
	.controller('FuncionarioController', FuncionarioController );

	FuncionarioController.$inject = ['$scope', '$state', '$backendUrl', 'FuncionarioService', 'PlcNotificationService', 'FileUploader', '$stateParams'];

	/** @ngInject */
	function FuncionarioController($scope, $state, $backendUrl, FuncionarioService, PlcNotificationService, FileUploader, $stateParams) {


		/* ------------------
		 * Atributos Gerais
		 * -----------------*/

		$scope.backendUrl = $backendUrl;
		$scope.uploader = new FileUploader({
			url: $backendUrl+'/uploadFiles',
			queueLimit: 1,
			withCredentials: true
		});

		// FILTERS
		$scope.uploader.filters.push({
			name: 'imageFilter',
			fn: function(item /*{File|FileLikeObject}*/, options) {
				var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
				return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
			}
		});

		// CALLBACKS
		$scope.uploader.onCompleteItem = function(fileItem, response, status, headers) {
			$scope._fotoFileName = fileItem._file.name;
		};


		var init = function(){

			if ($state.current.name === 'funcionario.mdt' && $stateParams.id){
				$scope.edit($stateParams.id);
			}

		}

		$scope.find = function(){
			FuncionarioService._all($scope.funcionarioArg).then( function (response) {
				$scope.gridOptions.data = response.data.entity;
			});
		};

		$scope.clear = function(){
			$scope.funcionarioArg =  new Object();
			$scope.gridOptions.data = [];
		};

		$scope.edit = function(id){
			FuncionarioService.edit(id).then( function (response) {
				$state.go( 'funcionario.mdt' );
				$scope.funcionario = response.data.entity;
			});
		};

		$scope.save = function(){
			$scope.funcionario.fotoFileName = $scope._fotoFileName;
			FuncionarioService.save($scope.funcionario).then( function (response) {
				$scope.funcionario = response.data.entity;
				$scope.uploader.clearQueue();
			});
		};

		$scope.remove = function(){
			funcionarioService.remove($scope.funcionario).then( function (response) {
				$scope.funcionario = response.data.entity;
			});
		};

		$scope.new = function () {
			$scope.funcionario = new Object();

			$scope.funcionario.dependente = [];

			$scope.funcionario.historicoProfissional = [];

			$state.go( 'funcionario.mdt' );
		};

		$scope.list = function () {
			$scope.funcionarioArg =  new Object();
			$state.go( 'funcionario.sel' );
		};


		$scope.oneAtATime = true;

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


		init();

	}

})();


