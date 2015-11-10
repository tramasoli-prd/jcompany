
(function() {
  'use strict';

  angular
    .module('rhdemo')
    .controller('funcionarioController', FuncionarioController );

  FuncionarioController.$inject = ['$rootScope', '$scope', '$state', '$backendUrl', 'funcionarioService', 'PlcNotificationService', 'appLookupSexoService', 'appLookupEstadoCivilService', 'appLookupDepartamentoService', 'FileUploader', '$stateParams'];

  /** @ngInject */
  function FuncionarioController($rootScope, $scope, $state, $backendUrl, funcionarioService, PlcNotificationService, appLookupSexoService, appLookupEstadoCivilService, appLookupDepartamentoService,  FileUploader, $stateParams) {


    /* ------------------
     * Atributos Gerais
     * -----------------*/
    $scope.staticLookupSexo = [];
    $scope.staticLookupEstadoCivil = [];
    $scope.dynamicLookupDepartamento = [];
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
      appLookupSexoService._all().then( function (response) {
        $scope.staticLookupSexo = response.data;
      });

      appLookupEstadoCivilService._all().then( function (response) {
        $scope.staticLookupEstadoCivil = response.data;
      });

      appLookupDepartamentoService._all().then( function (response) {
        $scope.dynamicLookupDepartamento = response.data;
      });

      if ($state.current.name === 'funcionariomdt' && $stateParams.id){
        $scope.edit($stateParams.id);
      }

    }

    $scope.find = function(){
      funcionarioService._all($scope.funcionarioArg).then( function (response) {
        $scope.gridOptions.data = response.data.entity;
      });
    };

    $scope.clear = function(){
       $scope.funcionarioArg =  new Object();
       $scope.gridOptions.data = [];
    };

    $scope.edit = function(id){
      funcionarioService.edit(id).then( function (response) {
        $state.go( 'funcionariomdt' );
        $rootScope.funcionario = response.data.entity;
      });
    };

    $scope.save = function(){
      $scope.funcionario.fotoFileName = $scope._fotoFileName;
      funcionarioService.save($scope.funcionario).then( function (response) {
          $rootScope.funcionario = response.data.entity;
          $scope.uploader.clearQueue();
      });
    };

    $scope.remove = function(){
      funcionarioService.remove($scope.funcionario).then( function (response) {
          $rootScope.funcionario = response.data.entity;
      });
    };

    $scope.new = function () {
      $rootScope.funcionario = new Object();

      $rootScope.funcionario.dependente = [];

      $rootScope.funcionario.historicoProfissional = [];

      $state.go( 'funcionariomdt' );
    };

    $scope.list = function () {
      $scope.funcionarioArg =  new Object();
      $state.go( 'funcionariosel' );
    };


    function rowTemplate() {
      return '<div ui-sref="funcionariomdt({id: row.entity.id})" >' +
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


    $scope.addItemDependente = function() {
      if(!$rootScope.funcionario.dependente){
        $rootScope.funcionario.dependente = [];
      }

      $rootScope.funcionario.dependente.push(new Object());
    };

    $scope.addItemHistoricoProfissional = function() {
      if(!$rootScope.funcionario.historicoProfissional){
        $rootScope.funcionario.historicoProfissional = [];
      }
      $rootScope.funcionario.historicoProfissional.push(new Object());
    };


    $scope.removeRowDependente = function(index){
        $rootScope.funcionario.dependente.splice(index, 1);
    };

    $scope.removeRowHistoricoProfissional = function(index){
        $rootScope.funcionario.historicoProfissional.splice(index, 1);
    };

    $scope.gridOptions = {
      paginationPageSizes: [25, 50, 75],
      paginationPageSize: 25,
      rowTemplate: rowTemplate(),
      columnDefs: [
        { field: 'id', displayName: 'Id', width: '10%'},
        { field: 'nome', displayName: 'Nome', width: '25%'},
        { field: 'email', displayName: 'E-mail', width: '20%'},
        { field: 'cpf', displayName: 'CPF', width: '15%'},
        { field: 'dataNascimento', displayName: 'Nascimento', width: '10%', cellFilter: 'date:\'dd/MM/yyyy\''},
        { field: 'departamento.descricao', displayName: 'Departamento', width: '20%'}
      ]
    };

    init();

  }

})();


