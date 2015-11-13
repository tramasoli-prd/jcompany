
(function() {
	'use strict';

	angular
	.module('rhdemo')
	.controller('DepartamentoController', DepartamentoController );

	DepartamentoController.$inject = ['$rootScope', '$scope', '$state', 'ngDialog', 'DepartamentoService', 'PlcNotificationService', '$stateParams'];

	/** @ngInject */
	function DepartamentoController($rootScope, $scope, $state, ngDialog, DepartamentoService, PlcNotificationService, $stateParams) {

		$scope.ehVinculado = false;



		$scope.init = function(){

			if ($state.current.name === 'departamentoman' && $stateParams.id){ 
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
			$scope.departamentoPai = '';
			$scope.departamentoPaiDescricao = '';
		};

		$scope.edit = function(id){
			DepartamentoService.edit(id).then( function (response) {

				$rootScope.departamento = response.data.entity;
				if (response.data.entity.departamentoPai){
					$rootScope.departamentoPai = response.data.entity.departamentoPai.id;
					$rootScope.departamentoPaiDescricao = response.data.entity.departamentoPai.descricao;
				}else{
					$rootScope.departamentoPai = null;
					$rootScope.departamentoPaiDescricao = null;
				}

			});
		};

		$scope.retornaVinculado = function(row){
			$scope.departamentoPai = row.entity.id;
			$scope.departamentoPaiDescricao = row.entity.descricao;
		};

		$scope.save = function(){

			if ($scope.departamentoPai != ''){
				$scope.departamento.departamentoPai =  $scope.departamentoPai;
			}

			DepartamentoService.save($scope.departamento).then( function (response) {
				$rootScope.departamento = response.data.entity;
			});
		};

		$scope.remove = function(){
			DepartamentoService.remove($scope.departamento).then( function (response) {
				$rootScope.departamento = response.data.entity;
			});
		};

		$scope.new = function () {
			$rootScope.departamento = new Object();
			$rootScope.departamentoPai = '';
			$rootScope.departamentoPaiDescricao = '';
			$state.go( 'departamentoman' );
		};

		$scope.list = function () {
			$scope.departamentoArg = new Object();
			$state.go( 'departamentosel' );
		};

		$scope.openConfirmWithPreCloseCallbackOnScope = function () {
			$scope.ehVinculado = true;
			ngDialog.openConfirm({
				template: 'app/departamento/departamentosel.html',
				preCloseCallback: 'preCloseCallbackOnScope',
				scope: $scope
			}).then(function (value) {
				$scope.ehVinculado = false;
			}, function (reason) {
				$scope.ehVinculado = false;
			});
		};

		$scope.rowTemplate =  function() {
			return '<div ng-if="!grid.appScope.ehVinculado" ui-sref="departamentoman({id: row.entity.id})" >' +
			'  <div ng-repeat="(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name" class="ui-grid-cell" ng-class="{ \'ui-grid-row-header-cell\': col.isRowHeader }"  ui-grid-cell></div>' +
			'</div>' +
			'<div ng-if="grid.appScope.ehVinculado"  ng-click="grid.appScope.retornaVinculado(row); grid.appScope.closeThisDialog()" >' +
			'  <div ng-repeat="(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name" class="ui-grid-cell" ng-class="{ \'ui-grid-row-header-cell\': col.isRowHeader }"  ui-grid-cell></div>' +
			'</div>';
		}

		$scope.gridOptions = {
				paginationPageSizes: [25, 50, 75],
				paginationPageSize: 25,
				rowTemplate: $scope.rowTemplate(),
				columnDefs: [
				             { field: 'id', displayName: 'Id', width: '10%'},
				             { field: 'descricao', displayName: 'Descrição', width: '50%'},
				             { field: 'departamentoPai.descricao', displayName: 'Departamento Pai', width: '40%'}
				             ]
		};

		$scope.init();

	}

})();


