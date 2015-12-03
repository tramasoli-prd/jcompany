
(function() {
  'use strict';

  var jcompanyModule = angular.module('jcompany-view');
  jcompanyModule.UfControllerConstructor = UfController;

  angular.module('rhdemo').controller('UfController', UfController );
  UfController.$inject = ['$injector', '$scope', '$compile', '$state', '$stateParams', 'UfService', 'PlcNotificationService', 'PlcUtils'];

  /** @ngInject */
  function UfController($injector, $scope, $compile, $state, $stateParams, UfService, PlcNotificationService, PlcUtils) {

    // required atributes in scope for inheritance.
    $scope.$baseService = UfService;
    $scope.$baseRoute = 'uf';

    // Using the injector for inheritance.
    $injector.invoke(jcompanyModule.PlcBaseControllerConstructor, this, {
        $scope: $scope,
        $compile: $compile,
        $state: $state,
        $stateParams: $stateParams,
        PlcNotificationService: PlcNotificationService,
        PlcUtils: PlcUtils
    }); 

    this.allTabular();

  }

})();


