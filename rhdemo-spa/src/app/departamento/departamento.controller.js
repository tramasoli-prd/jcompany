
(function() {
	'use strict';

	angular
	.module('rhdemo')
	.controller('DepartamentoController', DepartamentoController );

	DepartamentoController.$inject = ['$scope', '$state', 'DepartamentoService', 'PlcNotificationService', '$stateParams'];

	/** @ngInject */
	function DepartamentoController($scope, $state, DepartamentoService, PlcNotificationService, $stateParams) {

		$scope.ehVinculado = false;



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

		$scope.rowTemplate = '<div ng-if="!grid.appScope.isAggregateModal" ui-sref="departamento.man({id: row.entity.id})" >' +
			'  <div ng-repeat="(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name" class="ui-grid-cell" ng-class="{ \'ui-grid-row-header-cell\': col.isRowHeader }"  ui-grid-cell></div>' +
			'</div>'+
			'<div ng-if="grid.appScope.isAggregateModal"  ng-click="grid.appScope.returnValuesAggregate(row); grid.appScope.closeThisDialog()" >' +
					'  <div ng-repeat="(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name" class="ui-grid-cell" ng-class="{ \'ui-grid-row-header-cell\': col.isRowHeader }"  ui-grid-cell></div>' +
					'</div>';

		$scope.getRowTemplate =  function() {
			return $scope.rowTemplate;
		}

		$scope.setRowTemplate =  function(templateAdd) {
			$scope.rowTemplate = templateAdd;
		}

		$scope.gridOptions = {
				paginationPageSizes: [25, 50, 75],
				paginationPageSize: 25,
				rowTemplate: $scope.getRowTemplate(),
				columnDefs: [
				             { field: 'id', displayName: 'Id', width: '10%'},
				             { field: 'descricao', displayName: 'Descrição', width: '50%'},
				             { field: 'departamentoPai.descricao', displayName: 'Departamento Pai', width: '40%'}
				             ]
		};

		$scope.init();

	}

})();


