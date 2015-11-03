angular
    .module('cronos')
    .directive('appGtModelos', function() {
        return {
            restrict: 'AE',
            scope: {
                item: '=item',
                topicos: '=topicos',
                preview: '=',
                readonly: '='
            },
            templateUrl: './views/directives/grupotrabalho/app-gt-modelos.html',
            link: function(scope, element, attrs){

            },
            controller: function($scope){

            }
        };
    });
