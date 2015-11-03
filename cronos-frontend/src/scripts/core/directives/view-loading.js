angular.module('cronos').directive('appViewLoading', function() {
    return {
        restrict: 'AE',
        // templateUrl: 'views/directives/app/view-loading.html',
        template: '<div class="view-loading loading-container" data-ng-if="loading" data-ng-animate-children><div class="loading fa-2x"><i class="fa fa-circle-o-notch fa-spin"></i><span>carregando...</span></div></div>',
        controller: function($rootScope, $scope, $route) {

            $scope.loading = false;

            $rootScope.$on('$routeChangeStart', function() {
                // console.log('routeChangeStart');
                $scope.loading = true;
            });
            $rootScope.$on('$routeChangeSuccess', function() {
                // console.log('routeChangeSuccess');
                $scope.loading = false;
            });
            $rootScope.$on('$routeChangeError', function() {
                // console.log('routeChangeError');
                $scope.loading = false;
            });
        }
    };
});
