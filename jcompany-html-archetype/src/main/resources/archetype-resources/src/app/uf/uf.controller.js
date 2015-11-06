
(function() {
  'use strict';

  angular
    .module('${artifactId}')
    .controller('ufController', UfController );

  UfController.$inject = ['$rootScope', '$scope', '$location', 'ufService', 'PlcNotificationService', 'PlcUtils'];

  /** @ngInject */
  function UfController($rootScope, $scope, $location, ufService, PlcNotificationService, PlcUtils) {

    $scope.all = function(){
      ufService._all().then( function (response) {
        $scope.rows = [];
        angular.forEach(response.data, function(value, key) {
          var o = new Object();
          o.uf = value;
          $scope.rows.push(o);
        });
        $scope.rows.push(new Object());
        
      });
    };

    $scope.saveRow = function(index){
      var rowElement = document.querySelectorAll('.row.ng-scope')[index];
      var temErro = PlcUtils.validaObrigatorio(rowElement);
      if (temErro) {    
        PlcNotificationService.error("CAMPOS_OBRIGATORIOS_TOPICO_024");
        return;
      }

      var incluiNovo = $scope.rows[index].uf && $scope.rows[index].uf.id ;
      ufService.save($scope.rows[index].uf).then( function (response) {
          if (!incluiNovo){
            $scope.rows.push(new Object());
          }
          $scope.rows[index].uf  = response.data;
          PlcNotificationService.success("DADOS_SALVOS_SUCESSO_000");
         
      });
    };

    $scope.removeRow = function(index){
      ufService.remove($scope.rows[index].uf).then( function (response) {
          PlcNotificationService.success("REGISTRO_EXCLUIDO_SUCESSO_021");
          $scope.rows.splice(index, 1);
      });
    };

    $scope.all();

  }

})();


