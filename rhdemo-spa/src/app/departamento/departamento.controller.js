
(function() {
  'use strict';

  angular
    .module('rhdemo')
    .controller('DepartamentoController', DepartamentoController );

  DepartamentoController.$inject = ['$rootScope', '$scope', '$state', 'ngDialog', 'DepartamentoService', 'notificationService', '$stateParams'];

  /** @ngInject */
  function DepartamentoController($rootScope, $scope, $state, ngDialog, DepartamentoService, notificationService, $stateParams) {

    $scope.ehVinculado = false;

    $scope.init();


    $scope.find = function(){
      DepartamentoService._all($scope.departamentoArg).then( function (response) {
        if (response.data.length == 0){
          notificationService.info("NENHUM_REGISTRO_ENCONTRADO_022");
        }
        $scope.gridOptions.data = response.data;
      });
    };

    $scope.clear = function(){
      $scope.departamentoArg = new Object();
      $scope.gridOptions.data = [];
    };

    $scope.edit = function(id){
      DepartamentoService.edit(id).then( function (response) {
        $rootScope.departamento = response.data;
        if (response.data.departamentoPai){
           $rootScope.departamentoPai = response.data.departamentoPai.id;
           $rootScope.departamentoPaiDescricao = response.data.departamentoPai.descricao;
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
      $scope.departamento.departamentoPai =  $scope.departamentoPai;
      DepartamentoService.save($scope.departamento).then( function (response) {
          $rootScope.departamento = response.data;
          notificationService.success("DADOS_SALVOS_SUCESSO_000");
      });
    };

    $scope.remove = function(){
      DepartamentoService.remove($scope.departamento).then( function (response) {
          $rootScope.departamento = response.data;
          notificationService.success("REGISTRO_EXCLUIDO_SUCESSO_021");
      });
    };

    $scope.new = function () {
      $rootScope.departamento = new Object();
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
        className: 'ngdialog-theme-default',
        preCloseCallback: 'preCloseCallbackOnScope',
        scope: $scope
      }).then(function (value) {
        console.log('Modal promise resolved. Value: ', value);
        $scope.ehVinculado = false;
      }, function (reason) {
        console.log('Modal promise rejected. Reason: ', reason);
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

  }

})();


