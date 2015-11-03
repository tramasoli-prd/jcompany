angular
    .module('cronos')
    .directive('appFab', ['$window', 'authService', 'sentencaService', function ($window, authService, sentencaService) {
        return {
            restrict: 'AE',
            scope: true,
            templateUrl: './views/directives/actionbutton/app-fab.html',
            link: function(scope, elem, attrs) {
                
                scope.openSentenca = function (attrs) {
                    authService.checarSessao();
                    sentencaService.clear();
                    sentencaService.setOpen(true);
                };

                scope.hasWriteAccess = function() {
                    return authService.hasWriteAccess();
                };
            }
        };
    }]);
