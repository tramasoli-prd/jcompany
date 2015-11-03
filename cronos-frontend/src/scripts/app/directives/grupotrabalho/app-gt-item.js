angular
    .module('cronos')
    .directive('appGtItem', ['authService', function (authService) {
        return {
            restrict: 'AE',
            scope: {
                node: '=item'
            },
            templateUrl: './views/directives/grupotrabalho/app-gt-item.html',
            controller: function($scope){
                $scope.hasWriteAccess = authService.hasWriteAccess();
                $scope.isAdmin = authService.isAdmin();
            }
        };
    }]);
