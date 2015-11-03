angular
    .module('cronos')
    .directive('appGtTopicos', function() {
        return {
            restrict: 'AE',
            scope: {
                item: '=',
                preview: '=',
                readonly: '='
            },
            templateUrl: './views/directives/grupotrabalho/app-gt-topicos.html',
            link: function(scope, element, attrs){

            },
            controller: function($scope){

            }
        };
    });
