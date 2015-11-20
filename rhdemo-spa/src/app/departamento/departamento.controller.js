
(function() {
	'use strict';

	angular
	.module('rhdemo')
	.controller('DepartamentoController', DepartamentoController );

	DepartamentoController.$inject = ['$scope', '$state', 'DepartamentoService', 'PlcNotificationService', '$stateParams', 'PlcUtils'];

	/** @ngInject */
	function DepartamentoController($scope, $state, DepartamentoService, PlcNotificationService, $stateParams, PlcUtils) {


		$scope.init = function(){

			if ($state.current.name === 'departamento.man' && $stateParams.id){ 
				$scope.edit($stateParams.id);    
			} 

		}


		$scope.find = function(){
			DepartamentoService._all($scope.departamentoArg).then( function (response) {
				$scope.gridOptions.data = response.data.entity;
			});
		};

		$scope.clear = function(){
			$scope.departamentoArg = new Object();
			$scope.gridOptions.data = [];
		};

		$scope.edit = function(id){
			DepartamentoService.edit(id).then( function (response) {
				$scope.departamento = response.data.entity;
			});
		};


		$scope.save = function(){
			var temErro = PlcUtils.validaObrigatorio();
			if (temErro) {    
				PlcNotificationService.error("CAMPOS_OBRIGATORIOS_TOPICO_024");
				return;
			}
			DepartamentoService.save($scope.departamento).then( function (response) {
				$scope.departamento = response.data.entity;
			});
		};

		$scope.remove = function(){
			DepartamentoService.remove($scope.departamento).then( function (response) {
				$scope.departamento = response.data.entity;
			});
		};

		$scope.new = function () {
			$scope.departamento = new Object();
			$state.go( 'departamento.man' );
		};

		$scope.list = function () {
			$scope.departamentoArg = new Object();
			$state.go( 'departamento.sel' );
		};


		$scope.columnDefs = [
		                     { field: 'id', displayName: 'Id', width: '10%'},
		                     { field: 'descricao', displayName: 'Descrição', width: '50%'},
		                     { field: 'departamentoPai.descricao', displayName: 'Departamento Pai', width: '40%'}
		                    ]

		$scope.init();

	}

})();


