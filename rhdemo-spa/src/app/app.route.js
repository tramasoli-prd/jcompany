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
      .state('departamentosel', {
        url: '/departamentosel',
        templateUrl: 'app/departamento/departamentosel.html',
        controller: 'DepartamentoController',
        controllerAs: 'departamentoController'
      });

    $stateProvider
      .state('departamentoman', {
        url: '/departamentoman/:id',
        templateUrl: 'app/departamento/departamentoman.html',
        controller: 'DepartamentoController',
        controllerAs: 'departamentoController'
    });

    $stateProvider
      .state('uf', {
        url: '/uf',
        templateUrl: 'app/uf/uf.html',
        controller: 'ufController',
        controllerAs: 'ufController'
      });

    $stateProvider
      .state('funcionariosel', {
        url: '/funcionariosel',
        templateUrl: 'app/funcionario/funcionariosel.html',
        controller: 'funcionarioController',
        controllerAs: 'funcionarioController'
      });

    $stateProvider
      .state('funcionariomdt', {
        url: '/funcionariomdt/:id',
        templateUrl: 'app/funcionario/funcionariomdt.html',
        controller: 'funcionarioController',
        controllerAs: 'funcionarioController'
      });

    $urlRouterProvider.otherwise('/');
  }

})();


