(function() {
  'use strict';

  angular
    .module('rhdemo')
    .config(RouterConfig);

    /** @ngInject */
  function RouterConfig($stateProvider, $urlRouterProvider) {
    
    $stateProvider
      .state('inicial', {
        url: '/',
        templateUrl: 'app/inicial/inicial.html',
        controller: 'InicialController',
        controllerAs: 'inicial'
      });

    $stateProvider
      .state('uf', {
        url: '/uf',
        templateUrl: 'app/uf/uf.html',
        controller: 'ufController',
        controllerAs: 'ufController'
      });

    $urlRouterProvider.otherwise('/');
  }

})();


