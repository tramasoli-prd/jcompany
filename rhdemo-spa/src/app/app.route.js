(function() {
  'use strict';

  angular
    .module('rhdemo')
    .config(RouterConfig);

    /** @ngInject */
  function RouterConfig($stateProvider, $urlRouterProvider) {
    
   


     $stateProvider
      .state('home', {
        url: '/',
        templateUrl: 'app/inicial/inicial.html',
        controller: 'AppController',
        controllerAs: 'app'
      });

    $urlRouterProvider.otherwise('/');

   
  }

})();


