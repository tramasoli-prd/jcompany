
(function() {
  'use strict';

  angular
    .module('rhdemo')
    .controller('funcionarioController', FuncionarioController );

  FuncionarioController.$inject = ['$rootScope', '$scope', '$state', 'funcionarioService', 'notificationService', 'appLookupSexoService', 'appLookupEstadoCivilService', 'appLookupDepartamentoService', 'FileUploader'];

  /** @ngInject */
  function FuncionarioController($rootScope, $scope, $state, funcionarioService, notificationService, appLookupSexoService, appLookupEstadoCivilService, appLookupDepartamentoService,  FileUploader) {


    /* ------------------
     * Atributos Gerais
     * -----------------*/
    $scope.staticLookupSexo = [];
    $scope.staticLookupEstadoCivil = [];
    $scope.dynamicLookupDepartamento = [];
    $scope.uploader = new FileUploader({
        url: 'http://localhost:7001/rhdemo-service/uploadFiles',
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
        $scope.foto = fileItem._file.name;
    };
          

    var init = function(){
      appLookupSexoService._all().then( function (response) {
        $scope.staticLookupSexo = response.data;
      });

      appLookupEstadoCivilService._all().then( function (response) {
        $scope.staticLookupEstadoCivil = response.data;
      });

      appLookupDepartamentoService._all().then( function (response) {
        $scope.dynamicLookupDepartamento = response.data;
      });
    }

    $scope.find = function(){
      funcionarioService._all().then( function (response) {
        $scope.gridOptions.data = response.data;
      });
    };

    $scope.clear = function(){
       $scope.gridOptions.data = [];
    };

    $scope.edit = function(row){
      funcionarioService.edit(row.entity.id).then( function (response) {
        $state.go( 'funcionariomdt' );
        $rootScope.funcionario = response.data;
      });
    };

    $scope.save = function(){
      console.log($scope.funcionario);
      $scope.funcionario.foto = $scope.foto;
      console.log($scope.funcionario.foto);
      funcionarioService.save($scope.funcionario).then( function (response) {
          $rootScope.funcionario = response.data;
          notificationService.success("DADOS_SALVOS_SUCESSO_000");
      });
    };

    $scope.remove = function(){
      funcionarioService.remove($scope.funcionario).then( function (response) {
          $rootScope.funcionario = response.data;
          notificationService.success("REGISTRO_EXCLUIDO_SUCESSO_021");
      });
    };

    $scope.new = function () {
      $state.go( 'funcionariomdt' );
    };

    $scope.list = function () {
      $state.go( 'funcionariosel' );
    };

    
    function rowTemplate() {
      return '<div ng-click="grid.appScope.edit(row)" >' +
             '  <div ng-repeat="(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name" class="ui-grid-cell" ng-class="{ \'ui-grid-row-header-cell\': col.isRowHeader }"  ui-grid-cell></div>' +
             '</div>';
    }

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


    $scope.addItem = function() {
      var newItemNo = $scope.items.length + 1;
     // $scope.items.push('Camera ' + newItemNo);
    };

    $scope.gridOptions = {
      paginationPageSizes: [25, 50, 75],
      paginationPageSize: 25,
      rowTemplate: rowTemplate(),
      columnDefs: [
        { field: 'id', displayName: 'Id', width: '10%'},
        { field: 'nome', displayName: 'nome', width: '90%'}
      ]
    };

    init();

  }

})();


