
(function() {
  'use strict';

  angular.module('rhdemo').controller('UfController', UfController );

  /** @ngInject */
  function UfController($scope, $controller, UfService) {

    var vm = this;

    $scope.$baseService = UfService;
    $scope.$baseRoute = 'uf';

    

    angular.extend(vm, $controller('PlcBaseController', {$scope: $scope}));
    vm.allTabular();
  }

})();


