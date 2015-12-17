(function() {
  'use strict';

  angular
    .module('rhdemo')
    .config(RouterConfig);

    /** @ngInject */
  function RouterConfig($stateProvider, $urlRouterProvider) {
    
	     $stateProvider
	      .state('login', {
	        url: '/plc-login',
	        templateUrl: 'plc-login.html',
	        controller: 'PlcLoginController',
	        controllerAs: 'loginCt',
	        access: 'public'
	      });


     $stateProvider
      .state('home', {
        url: '/',
        templateUrl: 'app/inicial/inicial.html'
      });

    $urlRouterProvider.otherwise('/');

   
  }

})();


