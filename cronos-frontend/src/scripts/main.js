(function() {
    "use strict";

    // App bootstrap dependencies
    var app = angular.module('cronos', [
        'ui.bootstrap',
        'ui.sortable',
        'ui.bootstrap.tooltip',
        'ui-notification',
        'ui.bootstrap.modal',
        'ui.bootstrap.tabs',
        'colorpicker.module',
        'naif.base64',
        'ngRoute',
        // 'ngTable',
        'ngAnimate',
        'ngCookies',
        'ngMask',
        'ngMockE2E', // for mock rest
        'textAngular',
        'ng-context-menu',
        'ngDraggable'
    ]);

    // App bootstrap constants definitions
    app.constant('$contextUrl', '#{contextUrl}');
    app.constant('$backendUrl', '#{backendUrl}');

    // App bootstrap configuration
    app.config(function($backendUrl, $httpProvider, $routeProvider, $locationProvider, RestServiceProvider) {

        // REST should send cookie credentials
        $httpProvider.defaults.useXDomain = true;
        $httpProvider.defaults.withCredentials = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];

        // Config Access Outside ngView
        $locationProvider.html5Mode(false);

        // Config REST base URL
        RestServiceProvider.setServiceUrl($backendUrl);

        // Config view routing
        $routeProvider
            .when('/', {
                templateUrl: 'views/pages/dashboard-view.html'
            })
            .when('/usuarios', {
                templateUrl: 'views/pages/usuario/usuario-view.html'
            })
            .when('/configuracoes', {
                templateUrl: 'views/pages/configuracoes/configuracoes-view.html'
            })
            .when('/pesquisa-geral', {
                templateUrl: 'views/pages/pesquisa-geral-view.html'
            })
            .when('/sentenca/:id', {
                templateUrl: 'views/pages/grupotrabalho/grupotrabalho-sentenca.html',
                controller : 'SentencaController',
                controllerAs : 'sentencaCt',
                resolve: {
                    loadData: function(sentencaService, $route){
                        return sentencaService.carregarSentenca($route.current.params.id);
                    }
                }
            })
            .when('/grupo-trabalho/:tipo/:id?/:hash?', {
                templateUrl: 'views/pages/grupotrabalho/grupotrabalho-view.html'
            })
            .when('/acesso-negado', {
                templateUrl: 'views/pages/acesso-negado.html'
            })
            .otherwise({
                redirectTo: '/'
            });
    });

    // App bootstrap initialization
    app.run(function(authService, mockService) {
        // Enable/Disable http mock
        mockService.mock({
            auth: false,
            usuario: false,
            grupoTrabalho: false
        });
        // Load authentication info
        authService.load();
    });
})();
